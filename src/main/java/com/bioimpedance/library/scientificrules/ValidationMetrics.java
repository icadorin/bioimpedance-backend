package com.bioimpedance.library.scientificrules;

/**
 * Fonte: schema_cientifico.md §3.3.
 * <p>
 * Conjunto inicial, poderá evoluir conforme métricas encontradas na
 * literatura (nota explícita do schema). Quando uma métrica não
 * estiver disponível: {@code null}, nunca um valor artificial.
 */
public record ValidationMetrics(
    Double correlation,
    Double standardError,
    Double meanDifference,
    Double rmse,
    String otherMetrics
) {}
