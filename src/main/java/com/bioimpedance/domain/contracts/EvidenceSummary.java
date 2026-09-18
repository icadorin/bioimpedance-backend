package com.bioimpedance.backend.domain.contracts;

/**
 * Resumo de cobertura de evidência de uma EquationVariant para o
 * cenário atual (cliente + contexto).
 * <p>
 * Fonte: especificacao_cientifica.md §6.5 (Evidence Coverage):
 * <pre>
 *   Sexo       ✓
 *   Idade      ✓
 *   População  ✓
 *   Atleta     ?
 *   Modalidade ?
 * </pre>
 * IMPORTANTE: ausência de cobertura NÃO implica automaticamente
 * inelegibilidade (§6.5). Este tipo só descreve o que está ou não
 * documentado — a decisão de READY/INELIGIBLE pertence a
 * {@code domain.eligibility}.
 */
public record EvidenceSummary(
        DocumentationStatus sex,
        DocumentationStatus age,
        DocumentationStatus population,
        DocumentationStatus athlete,
        DocumentationStatus trainingLevel,
        DocumentationStatus modality,
        DocumentationStatus bodyCharacteristics
) {
}
