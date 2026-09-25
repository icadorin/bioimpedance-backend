package com.bioimpedance.domain.contracts;

/**
 * Estado global do resultado da sugestão de conversão.
 * <p>
 * Fonte: doc.md §16.4 + §22.
 */
public enum ConversionSuggestionStatus {
    /** Existe ao menos uma conversão READY; indicação produzida. */
    SUGGESTED,
    /** Nenhuma conversão consome o outputType do resultado — ele já está
     *  no formato final (ex.: FALK4 sai direto em BODY_FAT_PERCENTAGE). */
    NO_CONVERSION_NEEDED,
    /** Existem conversões aplicáveis, mas nenhuma está READY (ex.: todas INELIGIBLE). */
    NO_ELIGIBLE_CONVERSION,
    /** Existem conversões aplicáveis, mas todas estão desabilitadas. */
    NO_ENABLED_CONVERSION
}