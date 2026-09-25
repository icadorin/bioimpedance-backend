package com.bioimpedance.domain.contracts;

import java.util.List;

/**
 * Resumo por conversão dentro do ConversionSuggestionResult.
 * <p>
 * Espelha {@link CandidateVariantSummary} (Fase 7). Usado nas três listas:
 * suggestedConversions, candidateConversions, excludedConversions.
 */
public record ConversionCandidateSummary(
    String conversionId,
    ConversionStatus status,
    List<String> reasons,
    List<String> warnings
) {
    public ConversionCandidateSummary {
        reasons = List.copyOf(reasons);
        warnings = List.copyOf(warnings);
    }
}