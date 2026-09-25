package com.bioimpedance.domain.contracts;

/**
 * Visão neutra de uma conversão para o ConversionSuggestionEngine.
 * <p>
 * DEC-23: mantém o engine sem depender de library/conversions. A
 * orchestration converte ConversionDefinition → este record antes de
 * chamar o engine. Contém somente os campos necessários à sugestão.
 */
public record ConversionCandidateInput(
    String conversionId,
    String inputType,
    String outputType
) {}