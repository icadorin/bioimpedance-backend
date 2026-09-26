package com.bioimpedance.domain.audit;

import com.bioimpedance.domain.contracts.AssessmentContext;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Snapshot append-only de uma execução do motor, para auditoria e
 * reprodução histórica.
 * <p>
 * Fonte: architecture.md §6 (append-only) + §22 (reprodução) +
 * doc.md §3 (regra de auditoria) + especificacao_cientifica.md §18 + DEC-29.
 * <p>
 * Imutável: apenas criação e leitura. Nunca update/delete.
 * Atualizar variante/regra/conversão NUNCA reescreve um snapshot —
 * produz nova execução (doc.md §3).
 * <p>
 * Resultados de cálculo/conversão são capturados como primitivos
 * (não referencia domain/calculation), mantendo a dependência
 * domain/audit → domain/contracts apenas (architecture.md §1.1).
 */
public record AuditSnapshot(
    // Identificação
    String auditId,
    String assessmentId,
    Instant timestamp,

    // Contexto e dados utilizados
    AssessmentContext context,
    Map<String, Double> inputsUsed,

    // Sugestão de método
    List<String> candidateVariantIds,
    String suggestionStatus,
    String suggestedVariantId,

    // Escolha profissional
    String selectedVariantId,
    boolean override,
    String overrideReason,

    // Cálculo
    String equationVariantId,
    String equationVersion,
    String predictionOutputType,
    double predictionValue,

    // Conversão (opcional — null se a variante produz resultado final direto)
    String conversionId,
    String conversionVersion,
    String conversionOutputType,
    Double conversionValue,
    String suggestedConversionId,
    String selectedConversionId,

    // Versões para reprodução (§22)
    String suggestionEngineVersion,
    String conversionSuggestionEngineVersion,
    String scientificRulesVersion,
    String evidenceVersion,
    String configurationVersion
) {
    // Compact constructor garante imutabilidade profunda das coleções
    public AuditSnapshot {
        inputsUsed = Map.copyOf(inputsUsed);
        candidateVariantIds = List.copyOf(candidateVariantIds);
    }
}