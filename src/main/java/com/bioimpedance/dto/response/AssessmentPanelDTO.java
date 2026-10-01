package com.bioimpedance.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

/**
 * Painel de estado da avaliação em rascunho (Fase 13 / B3 — DEC-52/55,
 * doc.md §17.8): painel (AssessmentFlowResponseDTO) + perfil travado
 * (sexo/idade/estatura, DEC-31) + contexto + medidas com origem.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentPanelDTO {

    private String assessmentId;
    private String clientId;
    private String clientName;
    private LocalDate date;
    /** DRAFT | FINALIZED */
    private String status;

    // ── Perfil travado (DEC-31): vem do Client, não é editável ──
    /** MALE | FEMALE */
    private String sex;
    private Integer age;
    private Double height;

    // ── Contexto editável ──
    private String objective;
    private String trainingLevel;
    private Boolean athlete;
    private String modality;

    // ── Medidas já gravadas, com origem (DEC-57) ──
    private Map<String, MeasurementValueDTO> measurements;

    // ── Estado recalculado pelo back ──
    private AssessmentFlowResponseDTO flow;
}