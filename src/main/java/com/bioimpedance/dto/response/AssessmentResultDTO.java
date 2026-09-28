package com.bioimpedance.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentResultDTO {
    private Double imc;
    private Double bodyFat;
    private Double leanMass;
    private Double fatMass;
    private Double ffmi;
    private Double bmr;
    private Double tdee;
    private Double targetCalories;
    private String bodyFatLevel;
    // methodDetails removido (DEC-45): ninguém preenchia; as 43 variantes
    // não têm conceito de "detalhes do método" como o legado.
}