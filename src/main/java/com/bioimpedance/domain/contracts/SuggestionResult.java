package com.bioimpedance.domain.contracts;

import java.util.List;

/**
 * Resultado do SuggestionEngine.
 * <p>
 * Fonte: especificacao_cientifica.md §15 (Resultado Estruturado) +
 * §12 (Conflitos e Estados Extremos).
 * <p>
 * candidateVariants contém as variantes não excluídas (READY e
 * MISSING_INPUTS — §11.1). suggestedVariants contém o subconjunto
 * READY mais compatível; em empate sem regra de diferenciação, todas
 * as empatadas são sugeridas (§11.3). excludedVariants contém
 * INELIGIBLE e DISABLED, com motivos.
 * <p>
 * Esta estrutura NÃO contém pontuação científica (§2).
 */
public record SuggestionResult(
    SuggestionStatus status,
    List<CandidateVariantSummary> suggestedVariants,
    List<CandidateVariantSummary> candidateVariants,
    List<CandidateVariantSummary> excludedVariants,
    List<String> warnings,
    List<String> reasons,
    String engineVersion
) {
    public SuggestionResult {
        suggestedVariants = List.copyOf(suggestedVariants);
        candidateVariants = List.copyOf(candidateVariants);
        excludedVariants = List.copyOf(excludedVariants);
        warnings = List.copyOf(warnings);
        reasons = List.copyOf(reasons);
    }
}