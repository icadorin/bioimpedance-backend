package com.bioimpedance.domain.contracts;

/**
 * Estado operacional de uma EquationVariant candidata, após avaliação
 * de aplicabilidade e verificação de dados disponíveis.
 * <p>
 * Fonte: especificacao_cientifica.md §9.
 */
public enum CandidateStatus {
    READY,
    MISSING_INPUTS,
    INELIGIBLE,
    DISABLED
}
