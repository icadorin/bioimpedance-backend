package com.bioimpedance.exception;

import lombok.Getter;

/**
 * Avaliação FINALIZED não aceita edição, novas medidas, novo cálculo
 * nem nova finalização (Fase 13 — DEC-58). Mapeada para 409
 * ASSESSMENT_LOCKED. Corrigir = nova avaliação (reabertura fora da V1).
 */
@Getter
public class AssessmentLockedException extends RuntimeException {

    private final String assessmentId;

    public AssessmentLockedException(String assessmentId) {
        super("Avaliação finalizada está travada: " + assessmentId);
        this.assessmentId = assessmentId;
    }

}