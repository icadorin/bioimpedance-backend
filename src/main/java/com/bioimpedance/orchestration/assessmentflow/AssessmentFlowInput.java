package com.bioimpedance.orchestration.assessmentflow;

import com.bioimpedance.domain.contracts.AssessmentContext;
import com.bioimpedance.domain.config.ProfessionalConfiguration;

import java.util.Map;

/**
 * Input completo para o fluxo de avaliação coordenado pela orchestration.
 * <p>
 * Fonte: architecture.md §23.
 * <p>
 * A orchestration recebe todos os dados já resolvidos (não consulta banco).
 */
public record AssessmentFlowInput(
    String assessmentId,
    AssessmentContext context,
    Map<String, Double> measurements,
    ProfessionalConfiguration professionalConfiguration,
    String selectedVariantId,
    boolean variantOverride,
    String variantOverrideReason,
    String selectedConversionId,
    boolean conversionOverride,
    String conversionOverrideReason
) {
    public AssessmentFlowInput {
        measurements = Map.copyOf(measurements);
    }
}