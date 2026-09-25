package com.bioimpedance.domain.contracts;

/**
 * Estados operacionais de uma ConversionDefinition ao longo do fluxo.
 * <p>
 * Fonte: doc.md §30.1. Diferente de {@link CandidateStatus}, NÃO inclui
 * MISSING_INPUTS — uma conversão opera sobre um PredictionResult já
 * produzido, não coleta novas medidas.
 * <p>
 * A Fase 8 (conversion-suggestion) produz apenas DISABLED / INELIGIBLE /
 * READY. SELECTED e CALCULATED são estados posteriores, resolvidos pela
 * orchestration / conversion — o enum já nasce completo pra suportar o
 * ciclo de vida inteiro.
 */
public enum ConversionStatus {
    DISABLED,
    INELIGIBLE,
    READY,
    SELECTED,
    CALCULATED
}