package com.bioimpedance.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Gera tokens CSRF aleatórios e criptograficamente seguros.
 * Extraído como utilitário independente para evitar dependência circular
 * entre AuthService e TwoFactorService.
 */
@Component
public class CsrfTokenUtil {

    private final SecureRandom secureRandom = new SecureRandom();

    public String generate() {
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}