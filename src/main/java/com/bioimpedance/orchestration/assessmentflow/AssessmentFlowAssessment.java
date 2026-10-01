package com.bioimpedance.orchestration.assessmentflow;

import com.bioimpedance.domain.contracts.ApplicabilityResult;
import com.bioimpedance.domain.contracts.ReadinessResult;
import com.bioimpedance.domain.contracts.SuggestionResult;
import com.bioimpedance.domain.contracts.ValidationResult;

import java.util.List;
import java.util.Map;

/**
 * Saída do {@link AssessmentFlowOrchestrator#assess(AssessmentFlowInput)} —
 * etapas 1–9 do pipeline (doc.md §13), ANTES de qualquer execução de cálculo.
 * <p>
 * DEC-53: extrair {@code assess()} de {@code execute()} é mudança aditiva
 * prevista no DEC-40(c). É também a base do painel de estado (doc.md §17)
 * e do guard de seleção: o estado da variante escolhida é consultado aqui,
 * não derivado por efeito colateral do EquationEvaluator.
 * <p>
 * Fonte: architecture.md §23 · especificacao_cientifica.md §14.
 */
public record AssessmentFlowAssessment(
    SuggestionResult suggestionResult,
    Map<String, ApplicabilityResult> applicabilityResults,
    List<ReadinessResult> readinessResults,
    ValidationResult validationResult
) {
    public AssessmentFlowAssessment {
        applicabilityResults = Map.copyOf(applicabilityResults);
        readinessResults = List.copyOf(readinessResults);
    }
}