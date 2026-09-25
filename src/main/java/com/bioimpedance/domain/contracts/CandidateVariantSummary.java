package com.bioimpedance.domain.contracts;

import java.util.List;

/**
 * Resumo por variante dentro do SuggestionResult.
 * <p>
 * Fonte: especificacao_cientifica.md §15 (Resultado Estruturado).
 * Usado nas três listas: suggestedVariants, candidateVariants e
 * excludedVariants.
 * <p>
 * {@code criteria} pode ser null quando a aplicabilidade não foi
 * avaliada (ex.: variante DISABLED). {@code missingInputs} é populado
 * somente quando status = MISSING_INPUTS.
 */
public record CandidateVariantSummary(
    String variantId,
    CandidateStatus status,
    SuggestionCriteriaSummary criteria,
    List<String> missingInputs,
    List<String> reasons,
    List<String> warnings
) {
    public CandidateVariantSummary {
        missingInputs = List.copyOf(missingInputs);
        reasons = List.copyOf(reasons);
        warnings = List.copyOf(warnings);
    }
}