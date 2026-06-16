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

    @Transactional
    public void createRefreshToken(String userId, String plainTextJwt, boolean rememberMe) {
        refreshTokenRepository.deleteByUserId(userId);
        String tokenHash = hashToken(plainTextJwt);
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

    @Transactional
    public Optional<RotateResult> rotateRefreshToken(String plainTextJwt) {
        String hash = hashToken(plainTextJwt);
        RefreshToken stored = refreshTokenRepository.findByTokenHash(hash).orElse(null);

        if (stored == null || stored.isUsed() || stored.getExpiresAt().isBefore(Instant.now())) {
            if (stored != null) {
                refreshTokenRepository.deleteByUserId(stored.getUserId());
            }
            return Optional.empty();
        }

        stored.setUsed(true);
        refreshTokenRepository.save(stored);
        return Optional.of(new RotateResult(stored.getUserId()));
    }

    /**
     * Job de limpeza: roda todos os dias às 03:00 da manhã.
     * Remove tokens usados (used=true) com mais de 14 dias.
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

    private String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao hash token", e);
        }
    }

    public record RotateResult(String userId) {}
}