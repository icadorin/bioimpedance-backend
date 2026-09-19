package com.bioimpedance.domain.calculation;

/**
 * Fonte: architecture.md §13.
 * Resultado imutável de um cálculo ou conversão.
 */
public record PredictionResult(
    String sourceId, // variantId ou conversionId
    String outputType, // "BODY_DENSITY" ou "BODY_FAT_PERCENTAGE"
    double value
) {}