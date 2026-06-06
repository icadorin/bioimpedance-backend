package com.bioimpedance.exception;

import lombok.Getter;

@Getter
public class TwoFactorRequiredException extends RuntimeException {
    private final String tempToken;
    private final String email;

    public TwoFactorRequiredException(String tempToken, String email) {
        super("Two-factor authentication required");
        this.tempToken = tempToken;
        this.email = email;
    }
}