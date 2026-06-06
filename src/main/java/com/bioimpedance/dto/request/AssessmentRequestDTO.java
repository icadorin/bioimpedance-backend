package com.bioimpedance.dto.request;

import com.bioimpedance.constants.AssessmentMethod;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentRequestDTO {

    @NotBlank
    private String clientId;

    @NotNull
    private LocalDate date;

    @NotNull
    private AssessmentMethod method;

    /**
     * Peso em kg — varia a cada avaliação, sempre informado pelo personal.
     */
    @NotNull
    @Positive
    private Double weight;

    /**
     * height, gender e age NÃO são mais informados aqui.
     * - height e gender vêm do cadastro do cliente (estáveis).
     * - age é calculado automaticamente a partir do birthDate do cliente
     *   no momento da avaliação.
     */

    private String activityLevel;
    private String objective;

    // Navy
    private Double waist;
    private Double neck;
    private Double hip;

    // Bioimpedância
    private Double resistance;
    private Double reactance;

    // Dobras cutâneas
    private String protocol;
    private Double biceps;
    private Double chest;
    private Double midaxillary;
    private Double triceps;
    private Double subscapular;
    private Double abdominal;
    private Double suprailiac;
    private Double thigh;

    @Size(max = 500)
    private String observations;
}