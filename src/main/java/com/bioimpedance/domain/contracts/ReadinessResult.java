package com.bioimpedance.backend.domain.contracts;

import java.util.List;

/**
 * Resultado da avaliação de prontidão de uma EquationVariant específica
 * para o Assessment atual.
 * <p>
 * Fonte: especificacao_cientifica.md §9 (Prontidão) e §11.4 (Explicação):
 * "A indicação deve possuir justificativas estruturadas."
 * <p>
 * Este é o objeto por-variante que compõe as listas
 * {@code candidateVariants} e {@code excludedVariants} do
 * SuggestionResult (especificacao_cientifica.md §15).
 * <p>
 * Notas sobre {@code warnings} e {@code exclusionReasons}: os
 * documentos dão exemplos de código (AGE_NEAR_VALIDATED_LIMIT,
 * SEX_NOT_SUPPORTED) mas não uma lista fechada. Mantidos como
 * {@code String} deliberadamente — o catálogo de códigos conhecidos
 * deve viver em {@code domain.eligibility} ou {@code domain.suggestion}
 * (quem os produz), não neste contrato.
 * <p>
 * Um warning NUNCA torna a variante automaticamente INELIGIBLE
 * (architecture.md §10; especificacao_cientifica.md §12.6).
 *
 * @param variantId        identificador da variante avaliada
 * @param status           estado operacional resultante
 * @param missingInputs    inputId ausentes; populado só quando status = MISSING_INPUTS
 * @param warnings         warnings que coexistem com READY
 * @param exclusionReasons motivos de exclusão; populado quando status = INELIGIBLE ou DISABLED
 * @param reasons          justificativas legíveis para exibição ao profissional (§11.4)
 */
public record ReadinessResult(
        String variantId,
        CandidateStatus status,
        List<String> missingInputs,
        List<String> warnings,
        List<String> exclusionReasons,
        List<String> reasons
) {
    public ReadinessResult {
        missingInputs = List.copyOf(missingInputs);
        warnings = List.copyOf(warnings);
        exclusionReasons = List.copyOf(exclusionReasons);
        reasons = List.copyOf(reasons);
    }
}
