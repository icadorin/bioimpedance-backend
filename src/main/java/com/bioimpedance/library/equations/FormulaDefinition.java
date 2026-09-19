package com.bioimpedance.library.equations;

import java.util.List;
import java.util.Map;

/**
 * Fonte: architecture.md §4.2.
 * Contém APENAS a matemática. Não sabe nada sobre população, sexo ou evidência.
 */
public record FormulaDefinition(
    String variantId,
    String outputType, // "BODY_DENSITY" ou "BODY_FAT_PERCENTAGE"
    FormulaTemplate template,
    List<String> sumInputs, // Inputs que formam o "S" (soma de dobras)
    Map<String, String> namedInputs, // Mapa de papéis (ex: "circ1" -> "CIRCUMFERENCE_FOREARM")
    Map<String, Double> coefficients
) {
    public FormulaDefinition {
        sumInputs = List.copyOf(sumInputs);
        // Proteção contra null caso o YAML não tenha a chave namedInputs
        namedInputs = namedInputs == null ? Map.of() : Map.copyOf(namedInputs);
        coefficients = Map.copyOf(coefficients);
    }
}