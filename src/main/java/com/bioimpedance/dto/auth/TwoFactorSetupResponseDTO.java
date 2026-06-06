package com.bioimpedance.dto.auth;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TwoFactorSetupResponseDTO {
    private String secret;
    private String qrCodeUrl;
    private List<String> backupCodes;
}