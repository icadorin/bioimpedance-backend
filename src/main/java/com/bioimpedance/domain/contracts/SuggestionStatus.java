package com.bioimpedance.domain.contracts;

/**
 * Estado global do resultado da sugestão.
 * <p>
 * Fonte: especificacao_cientifica.md §12 (Conflitos e Estados Extremos).
 */
public enum SuggestionStatus {
    /** Existe ao menos uma variante READY; indicação produzida. */
    SUGGESTED,
    /** Existem candidatas, mas nenhuma READY (§12.2/§12.3). */
    NO_READY_METHOD,
    /** Nenhuma variante elegível para o cenário (§12.1). */
    NO_ELIGIBLE_METHOD,
    /** Todas as variantes estão desabilitadas (§12.4). */
    NO_ENABLED_METHOD
}