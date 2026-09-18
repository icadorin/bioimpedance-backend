package com.bioimpedance.backend.library.scientificrules;

/**
 * Faixa {@code [min, max]}. min ou max podem ser {@code null} para
 * representar faixa aberta.
 * <p>
 * Fonte: schema_cientifico.md, "Convenções de tipo" — {@code Range<Number>}.
 */
public record Range<T>(T min, T max) {
}
