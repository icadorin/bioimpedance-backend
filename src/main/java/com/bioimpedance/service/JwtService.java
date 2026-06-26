package com.bioimpedance.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration:900000}") // 15 minutos (padrão)
    private long expiration;

    @Value("${jwt.refresh-expiration:604800000}") // 7 dias (padrão)
    private long refreshExpiration;

    @Value("${jwt.refresh-remember-me-expiration:2592000000}") // 30 dias (padrão)
    private long refreshRememberMeExpiration;

    @Value("${jwt.two-factor-expiration:300000}") // 5 minutos
    private long twoFactorExpiration;

    /**
     * Valida as configurações de expiração na inicialização da aplicação.
     * Previne erros de configuração (ex: segundos ao invés de milissegundos)
     * e garante que a hierarquia de validade dos tokens faça sentido.
     */
    @PostConstruct
    public void validateAndLogExpirationConfig() {
        long oneDayInMs = TimeUnit.DAYS.toMillis(1);
        long maxRememberMeDays = 90L; // Limite máximo de segurança: 90 dias
        long maxRememberMeInMs = maxRememberMeDays * oneDayInMs;

        log.info("=== VALIDAÇÃO DE CONFIGURAÇÃO JWT ===");
        log.info("Access Token:           {} ms ({} min)", expiration, expiration / 60000);
        log.info("Refresh Token (Normal): {} ms ({} dias)", refreshExpiration, refreshExpiration / oneDayInMs);
        log.info("Refresh Token (30 dias):{} ms ({} dias)", refreshRememberMeExpiration, refreshRememberMeExpiration / oneDayInMs);
        log.info("2FA Temp Token:         {} ms ({} min)", twoFactorExpiration, twoFactorExpiration / 60000);
        log.info("========================");

        if (expiration >= refreshExpiration) {
            throw new IllegalStateException(
                "ERRO CRÍTICO: jwt.expiration deve ser menor que jwt.refresh-expiration."
            );
        }
        if (expiration >= refreshRememberMeExpiration) {
            throw new IllegalStateException(
                "ERRO CRÍTICO: jwt.expiration deve ser menor que jwt.refresh-remember-me-expiration."
            );
        }
        if (refreshRememberMeExpiration > maxRememberMeInMs) {
            throw new IllegalStateException(
                String.format(
                    "ERRO CRÍTICO: jwt.refresh-remember-me-expiration excede %d dias.",
                    maxRememberMeDays
                )
            );
        }
    }

    private SecretKey getSigningKey() {
        try {
            if (secret == null || secret.length() < 32) {
                throw new IllegalStateException("JWT_SECRET deve ter pelo menos 32 caracteres");
            }
            return Keys.hmacShaKeyFor(secret.getBytes());
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao gerar chave JWT", e);
        }
    }

    public String generateToken(String email, String tokenFamily, boolean twoFactorVerified) {
        return Jwts.builder()
            .id(tokenFamily)
            .subject(email)
            .claim("type", "access")
            .claim("2fa_verified", twoFactorVerified)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSigningKey(), Jwts.SIG.HS512)
            .compact();
    }

    public String generateRefreshToken(String email, String tokenFamily, boolean rememberMe, boolean twoFactorVerified) {
        long actualExpiration = rememberMe ? refreshRememberMeExpiration : refreshExpiration;

        return Jwts.builder()
            .id(UUID.randomUUID().toString())
            .subject(email)
            .claim("type", "refresh")
            .claim("family", tokenFamily)
            .claim("rememberMe", rememberMe)
            .claim("2fa_verified", twoFactorVerified)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + actualExpiration))
            .signWith(getSigningKey(), Jwts.SIG.HS512)
            .compact();
    }

    public String generateTwoFactorTempToken(String userId, String tokenId) {
        return Jwts.builder()
            .id(tokenId)
            .subject(userId)
            .claim("type", "2fa_temp")
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + twoFactorExpiration))
            .signWith(getSigningKey(), Jwts.SIG.HS512)
            .compact();
    }

    public boolean isTwoFactorTempTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return "2fa_temp".equals(claims.get("type", String.class));
        } catch (Exception e) {
            return false;
        }
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractTokenFamily(String token) {
        Claims claims = extractAllClaims(token);
        String family = claims.get("family", String.class);
        return family != null ? family : claims.getId();
    }

    public boolean extractRememberMe(String token) {
        try {
            Boolean rememberMe = extractAllClaims(token).get("rememberMe", Boolean.class);
            return Boolean.TRUE.equals(rememberMe);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return "access".equals(claims.get("type", String.class));
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRefreshTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return "refresh".equals(claims.get("type", String.class));
        } catch (ExpiredJwtException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean extractTwoFactorVerified(String token) {
        try {
            Boolean verified = extractAllClaims(token).get("2fa_verified", Boolean.class);
            return Boolean.TRUE.equals(verified);
        } catch (Exception e) {
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("Token expirado", e);
        } catch (Exception e) {
            throw new IllegalArgumentException("Token JWT inválido", e);
        }
    }
}