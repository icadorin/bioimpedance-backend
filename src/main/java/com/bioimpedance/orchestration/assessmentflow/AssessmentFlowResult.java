package com.bioimpedance.orchestration.assessmentflow;

import com.bioimpedance.domain.contracts.SuggestionResult;
import com.bioimpedance.domain.contracts.ConversionSuggestionResult;
import com.bioimpedance.domain.calculation.PredictionResult;
import com.bioimpedance.domain.audit.AuditSnapshot;

/**
 * Resultado completo do fluxo de avaliação coordenado pela orchestration.
 * <p>
 * Fonte: architecture.md §23, doc.md §13–15.
 */
public record AssessmentFlowResult(
    String assessmentId,
    SuggestionResult suggestionResult,
    String selectedVariantId,
    PredictionResult variantPrediction,
    ConversionSuggestionResult conversionSuggestionResult,
    String selectedConversionId,
    PredictionResult conversionPrediction,
    PredictionResult finalResult,
    AuditSnapshot auditSnapshot
) {}