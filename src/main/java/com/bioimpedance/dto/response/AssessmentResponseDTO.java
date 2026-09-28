package com.bioimpedance.dto.response;

import com.bioimpedance.constants.Gender;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentResponseDTO {
    private String id;
    private String clientId;
    private String clientName;
    private LocalDate date;
    private Double weight;
    private Double height;
    private Integer age;
    private Gender gender;

    /**
     * Medidas no formato inputId → valor (DEC-45). Substitui os antigos
     * blocos navy/bioimpedance/skinfold. Ex.: {"SKINFOLD_TRICEPS": 12.0, "BODY_MASS": 75.0}
     */
    private Map<String, Double> measurements;

    private AssessmentResultDTO result;
    private String observations;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}