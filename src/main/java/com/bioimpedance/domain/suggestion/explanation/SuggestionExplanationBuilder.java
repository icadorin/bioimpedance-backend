package com.bioimpedance.domain.suggestion.explanation;

import com.bioimpedance.domain.contracts.ApplicabilityResult;
import com.bioimpedance.domain.contracts.CandidateStatus;
import com.bioimpedance.domain.contracts.MatchClassification;
import com.bioimpedance.domain.contracts.MatchResult;
import com.bioimpedance.domain.contracts.ReadinessResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Fonte: especificacao_cientifica.md §11.4 (Explicação) +
 * architecture.md §11.3.
 * <p>
 * Produz justificativas legíveis SOMENTE a partir dos resultados dos
 * avaliadores (§11.4: "O SuggestionEngine não deve inventar
 * justificativas"). Para variantes não-READY, repassa as justificativas
 * já produzidas por eligibility.
 */
@Component
public class SuggestionExplanationBuilder {

    public List<String> buildReasons(ApplicabilityResult applicability, ReadinessResult readiness) {
        if (readiness.status() != CandidateStatus.READY) {
            return new ArrayList<>(readiness.reasons());
        }

        List<String> reasons = new ArrayList<>();
        if (applicability != null) {
            if (isClassification(applicability.sexMatch(), MatchClassification.EXACT)) {
                reasons.add("Sexo compatível com a variante.");
            }
            if (isClassification(applicability.ageMatch(), MatchClassification.EXACT)) {
                reasons.add("Idade dentro da população validada.");
            }
            MatchResult population = applicability.populationMatch();
            if (isClassification(population, MatchClassification.EXACT)
                || isClassification(population, MatchClassification.HIGH)) {
                reasons.add("População com correspondência documentada.");
            }
        }
        reasons.add("Todos os inputs obrigatórios estão disponíveis.");
        return reasons;
    }

    private static boolean isClassification(MatchResult match, MatchClassification classification) {
        return match != null && match.classification() == classification;
    }
}