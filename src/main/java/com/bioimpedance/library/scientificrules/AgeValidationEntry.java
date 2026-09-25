package com.bioimpedance.library.scientificrules;

/**
 * Fonte: schema_cientifico.md §2.2 — AgeValidationEntry.
 * Cada entrada registra faixa, população correspondente e fonte. Uma
 * variante pode possuir múltiplas entradas — nunca colapsar.
 */
public record AgeValidationEntry(
    Range<Integer> range,
    String population,
    Reference source
) {}
