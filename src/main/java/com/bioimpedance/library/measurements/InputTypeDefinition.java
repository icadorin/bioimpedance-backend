package com.bioimpedance.library.measurements;

import com.bioimpedance.library.scientificrules.Range;

/**
 * Fonte: schema_cientifico.md §7.1.
 * <p>
 * {@code plausibleRange} é sanidade do dado (regra global da plataforma),
 * NUNCA aplicabilidade científica da fórmula (schema §7.3/§7.4).
 */
public record InputTypeDefinition(
    String inputId,
    String label,
    ValueType valueType,
    Unit unit,
    Integer precision,
    Range<Double> plausibleRange,
    String measurementProtocolNotes
) {}