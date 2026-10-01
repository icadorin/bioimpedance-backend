package com.bioimpedance.exception;

import com.bioimpedance.domain.contracts.ValidationIssue;
import lombok.Getter;

import java.util.List;

/**
 * Medida recusada pelo MeasurementValidator no autosave (Fase 13 — DEC-56).
 * Somente INVALID_VALUE e IMPOSSIBLE_VALUE bloqueiam; os demais tipos
 * (ex.: PRECISION_MISMATCH) gravam com aviso. Mapeada para 422
 * MEASUREMENT_INVALID com a lista de issues estruturada.
 */
@Getter
public class MeasurementInvalidException extends RuntimeException {

    private final String inputId;
    private final List<ValidationIssue> issues;

    public MeasurementInvalidException(String inputId, List<ValidationIssue> issues) {
        super("Medida inválida para " + inputId);
        this.inputId = inputId;
        this.issues = List.copyOf(issues);
    }

}