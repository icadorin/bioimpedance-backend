package com.bioimpedance.library.conversions;

import com.bioimpedance.library.scientificrules.LifecycleStatus;
import com.bioimpedance.library.scientificrules.Reference;
import com.bioimpedance.library.scientificrules.SourceConflictRecord;

import java.util.List;

/**
 * Fonte: doc.md §22.4.
 * <p>
 * Conversão NÃO é EquationVariant: trabalha sobre um PredictionResult
 * (inputType) e produz outro tipo de resultado (outputType).
 * inputType/outputType são String nesta fase; o tipo forte de saída
 * entra em domain/calculation (Fase 3).
 */
public record ConversionDefinition(
    String id,
    String name,
    String inputType,
    String outputType,
    String expression,
    String computationalForm,
    List<ConversionApplicabilityRule> applicabilityRules,
    String version,
    LifecycleStatus status,
    Reference reference,
    SourceConflictRecord sourceConflict,
    String notes
) {
    public ConversionDefinition {
        applicabilityRules = List.copyOf(applicabilityRules);
    }
}