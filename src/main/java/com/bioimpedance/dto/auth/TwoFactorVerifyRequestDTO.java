package com.bioimpedance.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TwoFactorVerifyRequestDTO {

    @NotBlank(message = "O token temporário é obrigatório")
    private String tempToken;

    @NotBlank(message = "O código TOTP é obrigatório")
    private String code;
}