package com.bioimpedance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "two_factor_temp_tokens")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TwoFactorTempToken {
    @Id
    private String id;
    private String userId;
    private String tokenHash;
    private Instant expiresAt;
    private Instant createdAt;
    private boolean used;
    private int attempts;
    private boolean blocked;
    private boolean rememberMe;
}
