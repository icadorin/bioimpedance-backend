package com.bioimpedance.entity;

import com.bioimpedance.constants.Plan;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Plan plan = Plan.BASIC;

    private String stripeCustomerId;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;

    // ==================== 2FA FIELDS ====================

    @Column(nullable = false)
    @Builder.Default
    private boolean twoFactorEnabled = false;

    @Column(name = "two_factor_secret", length = 500)
    private String twoFactorSecret;

    @Column(name = "two_factor_temp_secret", length = 500)
    private String twoFactorTempSecret;

    @Column(name = "two_factor_backup_codes", length = 2000)
    private String twoFactorBackupCodes;

    @Column(name = "two_factor_setup_at")
    private LocalDateTime twoFactorSetupAt;

    // ==================== TIMESTAMPS ====================

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}