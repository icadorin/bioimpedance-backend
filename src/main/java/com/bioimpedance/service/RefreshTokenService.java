package com.bioimpedance.service;

import com.bioimpedance.entity.RefreshToken;
import com.bioimpedance.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * Persiste o hash do refresh token JWT no banco para single-use tracking.
     * Respeita o flag rememberMe para definir 30 dias ou 7 dias de validade.
     */
    @Transactional
    public void createRefreshToken(String userId, String plainTextJwt, boolean rememberMe) {
        // Remove tokens antigos do usuário antes de criar
        refreshTokenRepository.deleteByUserId(userId);

        String tokenHash = hashToken(plainTextJwt);

        // 30 dias se rememberMe for true, senão 7 dias
        long days = rememberMe ? 30 : 7;

        RefreshToken token = RefreshToken.builder()
            .id(UUID.randomUUID().toString())
            .tokenHash(tokenHash)
            .userId(userId)
            .expiresAt(Instant.now().plus(days, ChronoUnit.DAYS))
            .createdAt(Instant.now())
            .used(false)
            .build();

        refreshTokenRepository.save(token);
    }

    /**
     * Valida e invalida o token atual (single-use).
     * Retorna RotateResult com o userId se OK, ou empty se roubo/expiração detectada.
     */
    @Transactional
    public Optional<RotateResult> rotateRefreshToken(String plainTextJwt) {
        String hash = hashToken(plainTextJwt);
        RefreshToken stored = refreshTokenRepository.findByTokenHash(hash).orElse(null);

        // Token não encontrado, já usado, ou expirado → possível roubo ou replay attack
        if (stored == null || stored.isUsed() || stored.getExpiresAt().isBefore(Instant.now())) {
            if (stored != null) {
                // Se o token existe mas foi reutilizado, bloqueia todas as sessões do usuário
                refreshTokenRepository.deleteByUserId(stored.getUserId());
            }
            return Optional.empty();
        }

        // Marca como usado (single-use)
        stored.setUsed(true);
        refreshTokenRepository.save(stored);

        return Optional.of(new RotateResult(stored.getUserId()));
    }

    /**
     * Job de limpeza: roda todos os dias às 03:00 da manhã.
     * Remove tokens que já foram usados (used=true) e têm mais de 14 dias.
     * Mantém o histórico recente para auditoria, mas evita que a tabela cresça infinitamente.
     */
    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void cleanupUsedTokens() {
        Instant cutoffDate = Instant.now().minus(14, ChronoUnit.DAYS);

        try {
            int deleted = refreshTokenRepository.deleteUsedTokensOlderThan(cutoffDate);
            if (deleted > 0) {
                log.info("Limpeza de RefreshTokens: {} tokens usados antigos removidos.", deleted);
            }
        } catch (Exception e) {
            log.error("Erro ao limpar RefreshTokens usados: {}", e.getMessage(), e);
        }
    }

    /**
     * Gera um hash SHA-256 do token para armazenar no banco de forma segura.
     */
    private String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao hash token", e);
        }
    }

    /**
     * Record simples para retornar o resultado da rotação.
     */
    public record RotateResult(String userId) {}
}