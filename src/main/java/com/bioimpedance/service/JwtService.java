package com.bioimpedance.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration:900000}")
    private long expiration;

    @Value("${jwt.refresh-expiration:604800000}")
    private long refreshExpiration;

    @Value("${jwt.two-factor-expiration:300000}")
    private long twoFactorExpiration;

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

    public String generateToken(String email, String tokenFamily) {
        return Jwts.builder()
            .id(tokenFamily)
            .subject(email)
            .claim("type", "access")
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSigningKey(), Jwts.SIG.HS512)
            .compact();
    }

    public String generateRefreshToken(String email, String tokenFamily) {
        return generateRefreshToken(email, tokenFamily, false);
    }

    public String generateRefreshToken(String email, String tokenFamily, boolean rememberMe) {
        return Jwts.builder()
            .id(UUID.randomUUID().toString())
            .subject(email)
            .claim("type", "refresh")
            .claim("family", tokenFamily)
            .claim("rememberMe", rememberMe)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + refreshExpiration))
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
