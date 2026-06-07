package com.bioimpedance.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateProfileRequestDTO {
    @NotBlank
    @Size(min = 3, max = 100)
    private String name;

    @NotBlank
    @Email
    private String email;
}
