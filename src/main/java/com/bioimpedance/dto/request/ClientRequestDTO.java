package com.bioimpedance.dto.request;

import com.bioimpedance.constants.ClientStatus;
import com.bioimpedance.constants.Gender;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String name;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Telefone deve conter 10 ou 11 dígitos")
    private String phone;

    @NotNull(message = "Gênero é obrigatório")
    private Gender gender;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate birthDate;

    @NotNull(message = "Altura é obrigatória")
    @DecimalMin(value = "100.0", message = "Altura deve ser no mínimo 100 cm")
    @DecimalMax(value = "250.0", message = "Altura deve ser no máximo 250 cm")
    private Double height;

    @Size(max = 500, message = "Objetivo deve ter no máximo 500 caracteres")
    private String goal;

    @Size(max = 1000, message = "Observações devem ter no máximo 1000 caracteres")
    private String notes;

    /**
     * Status opcional — usado apenas em atualizações (PUT /clients/{id}).
     * Na criação é ignorado: novos clientes sempre começam como PENDING.
     * Valores aceitos: ACTIVE, INACTIVE, PENDING.
     */
    private ClientStatus status;
}