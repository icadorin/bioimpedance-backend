package com.bioimpedance.security;

import com.bioimpedance.entity.SessionFingerprint;
import com.bioimpedance.repository.SessionFingerprintRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FingerprintService {
    private static final Logger log = LoggerFactory.getLogger(FingerprintService.class);
    private final SessionFingerprintRepository fingerprintRepository;

    public FingerprintResult validateFingerprint(String userId, String tokenFamily,
                                                 HttpServletRequest request) {
        String currentIpHash = hash(getClientIp(request));
        String currentUaHash = hash(request.getHeader("User-Agent"));

        try {
            Optional<SessionFingerprint> existing = fingerprintRepository
                .findByUserIdAndTokenFamily(userId, tokenFamily)
                .stream().findFirst();

            if (existing.isEmpty()) {
                fingerprintRepository.save(SessionFingerprint.builder()
                    .id(UUID.randomUUID().toString())
                    .userId(userId)
                    .ipHash(currentIpHash)
                    .userAgentHash(currentUaHash)
                    .tokenFamily(tokenFamily)
                    .createdAt(Instant.now())
                    .lastUsedAt(Instant.now())
                    .blocked(false)
                    .build());
                return FingerprintResult.valid();
            }

            SessionFingerprint stored = existing.get();

            if (stored.isBlocked()) {
                return FingerprintResult.blocked("Sessão bloqueada");
            }

            boolean ipMatch = stored.getIpHash().equals(currentIpHash);
            boolean uaMatch = stored.getUserAgentHash().equals(currentUaHash);

            // IP e User-Agent completamente diferentes: alto risco de roubo de token.
            if (!ipMatch && !uaMatch) {
                blockAllSessions(userId);
                log.warn("Sessão suspeita detectada para userId={} — IP e UA divergentes. " +
                    "Todas as sessões bloqueadas.", userId);
                return FingerprintResult.blocked("Sessão suspeita detectada");
            }

            // IP pode mudar legitimamente (celular trocando de rede, VPN).
            stored.setIpHash(currentIpHash);
            stored.setLastUsedAt(Instant.now());
            fingerprintRepository.save(stored);

            return FingerprintResult.valid();

        } catch (Exception e) {
            log.error("Falha ao validar fingerprint para userId={} — acesso permitido " +
                    "por fallback. Verifique a disponibilidade do banco. Erro: {}",
                userId, e.getMessage());
            return FingerprintResult.valid();
        }
    }

    /**
     * Job de limpeza: roda todos os dias às 03:30 da manhã.
     * Remove fingerprints não utilizados há mais de 30 dias.
     * Evita que a tabela session_fingerprints cresça indefinidamente.
     */
    @Scheduled(cron = "0 30 3 * * *")
    @Transactional
    public void cleanupOldFingerprints() {
        Instant cutoffDate = Instant.now().minus(90, ChronoUnit.DAYS);
        try {
            int deleted = fingerprintRepository.deleteByLastUsedAtBefore(cutoffDate);
            if (deleted > 0) {
                log.info("Limpeza de SessionFingerprints: {} registros antigos removidos.", deleted);
            }
        } catch (Exception e) {
            log.error("Erro ao limpar SessionFingerprints antigos: {}", e.getMessage(), e);
        }
    }

    private void blockAllSessions(String userId) {
        try {
            List<SessionFingerprint> sessions =
                fingerprintRepository.findByUserIdOrderByLastUsedAtDesc(userId);
            sessions.forEach(s -> s.setBlocked(true));
            fingerprintRepository.saveAll(sessions);
        } catch (Exception e) {
            log.error("Falha ao bloquear sessões do userId={}: {}", userId, e.getMessage());
        }
    }

    private String getClientIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) return xff.split(",")[0].trim();
        return request.getRemoteAddr();
    }

    private String hash(String value) {
        if (value == null) return "unknown";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            return "error";
        }
    }

    @Getter
    public static class FingerprintResult {
        private final boolean valid;
        private final boolean blocked;
        private final String reason;

        private FingerprintResult(boolean valid, boolean blocked, String reason) {
            this.valid = valid;
            this.blocked = blocked;
            this.reason = reason;
        }

        public static FingerprintResult valid() {
            return new FingerprintResult(true, false, null);
        }

        public static FingerprintResult blocked(String reason) {
            return new FingerprintResult(false, true, reason);
        }
    }
}