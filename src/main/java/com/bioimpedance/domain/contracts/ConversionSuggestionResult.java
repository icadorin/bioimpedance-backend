package com.bioimpedance.domain.contracts;

import java.util.List;

/**
 * Resultado do ConversionSuggestionEngine.
 * <p>
 * Espelha {@link SuggestionResult} (Fase 7). candidateConversions contém as
 * conversões READY; excludedConversions contém INELIGIBLE e DISABLED (com
 * motivos); suggestedConversions é o subconjunto sugerido (preferência
 * profissional > default > empate).
 * <p>
 * Esta estrutura NÃO contém pontuação científica (§2).
 */
public record ConversionSuggestionResult(
    ConversionSuggestionStatus status,
    List<ConversionCandidateSummary> suggestedConversions,
    List<ConversionCandidateSummary> candidateConversions,
    List<ConversionCandidateSummary> excludedConversions,
    List<String> warnings,
    List<String> reasons,
    String engineVersion
) {
    public ConversionSuggestionResult {
        suggestedConversions = List.copyOf(suggestedConversions);
        candidateConversions = List.copyOf(candidateConversions);
        excludedConversions = List.copyOf(excludedConversions);
        warnings = List.copyOf(warnings);
        reasons = List.copyOf(reasons);
    }
}