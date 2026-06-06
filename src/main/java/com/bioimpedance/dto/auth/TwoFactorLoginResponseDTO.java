package com.bioimpedance.dto.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TwoFactorLoginResponseDTO {
    private String name;
    private String email;
    private String plan;
}