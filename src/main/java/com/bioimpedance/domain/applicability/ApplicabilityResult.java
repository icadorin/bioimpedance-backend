package com.bioimpedance.domain.applicability;

import com.bioimpedance.domain.contracts.EvidenceSummary;
import com.bioimpedance.domain.contracts.MatchResult;

import java.util.List;

/**
 * Fonte: architecture.md §9 + especificacao_cientifica.md §4.
 *
 * Avaliação derivada de UMA variante contra um perfil+contexto.
 * NÃO decide READY/INELIGIBLE (isso é da Fase 6, domain.eligibility).
 */
public record ApplicabilityResult(
    String variantId,
    MatchResult sexMatch,
    MatchResult ageMatch,
    MatchResult populationMatch,
    MatchResult contextMatch,
    EvidenceSummary evidenceSummary,
    List<String> warnings,
    boolean hasBlockingScientificRestriction
) {
    public ApplicabilityResult {
        warnings = List.copyOf(warnings);
    }
}