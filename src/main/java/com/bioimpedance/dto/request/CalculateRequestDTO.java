package com.bioimpedance.dto.request;

import com.bioimpedance.constants.AssessmentMethod;
import com.bioimpedance.constants.Gender;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalculateRequestDTO {

    /**
     * Quando informado, height/gender/age são resolvidos no backend a partir
     * do cadastro do cliente, mantendo o frontend apenas como coletor de dados.
     */
    private String clientId;

    private LocalDateTime date;

    @NotNull
    private AssessmentMethod method;

    @NotNull
    @Positive
    private Double weight;

    @Positive
    private Double height;

    @Min(10)
    @Max(100)
    private Integer age;

    private Gender gender;

    private String activityLevel;
    private String objective;

    // Navy
    private Double waist;
    private Double neck;
    private Double hip;

    // Bioimpedance
    private Double resistance;
    private Double reactance;

    // Skinfold
    @Size(max = 20)
    private String protocol;
    private Double biceps;
    private Double chest;
    private Double midaxillary;
    private Double triceps;
    private Double subscapular;
    private Double abdominal;
    private Double suprailiac;
    private Double thigh;
}
