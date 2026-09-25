package com.bioimpedance.domain.suggestion;

import com.bioimpedance.domain.contracts.ApplicabilityResult;
import com.bioimpedance.domain.contracts.MatchClassification;
import com.bioimpedance.domain.contracts.MatchResult;
import com.bioimpedance.domain.contracts.ReadinessResult;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Seleciona as variantes READY mais compatíveis.
 * <p>
 * Fonte: especificacao_cientifica.md §11.2–11.3 + architecture.md §11.
 * <p>
 * DEC-19: sem pontuação numérica. A ordenação usa a hierarquia das
 * classificações documentadas (idade primeiro, população depois).
 * Empate completo = variantes igualmente compatíveis: todas são
 * sugeridas (§11.3), sem tie-breaker inventado. O desempate por
 * variantId serve somente ao determinismo da lista (§20), nunca como
 * preferência científica.
 */
@Component
public class CompatibilityRanker {

    /**
     * Retorna os variantIds do grupo mais compatível entre as READY,
     * em ordem determinística.
     */
    public List<String> selectSuggestedVariantIds(List<ReadinessResult> readyVariants,
                                                  Map<String, ApplicabilityResult> applicabilityResults) {
        if (readyVariants.isEmpty()) {
            return List.of();
        }
        record Ranked(String variantId, int ageRank, int populationRank) {}

        List<Ranked> ranked = readyVariants.stream()
            .map(r -> {
                ApplicabilityResult a = applicabilityResults.get(r.variantId());
                return new Ranked(
                    r.variantId(),
                    ageRank(a != null ? a.ageMatch() : null),
                    populationRank(a != null ? a.populationMatch() : null));
            })
            .sorted(Comparator.comparingInt(Ranked::ageRank)
                .thenComparingInt(Ranked::populationRank)
                .thenComparing(Ranked::variantId))
            .toList();

        Ranked best = ranked.getFirst();
        return ranked.stream()
            .filter(r -> r.ageRank() == best.ageRank()
                && r.populationRank() == best.populationRank())
            .map(Ranked::variantId)
            .toList();
    }

    /** Menor = mais compatível: EXACT > PARTIAL > OUTSIDE_VALIDATED_RANGE > UNKNOWN. */
    private static int ageRank(MatchResult match) {
        MatchClassification c = match != null ? match.classification() : null;
        if (c == null) return 3;
        return switch (c) {
            case EXACT -> 0;
            case PARTIAL -> 1;
            case OUTSIDE_VALIDATED_RANGE -> 2;
            default -> 3;
        };
    }

    /** Menor = mais compatível: EXACT > HIGH > MODERATE > LOW > UNKNOWN. */
    private static int populationRank(MatchResult match) {
        MatchClassification c = match != null ? match.classification() : null;
        if (c == null) return 4;
        return switch (c) {
            case EXACT -> 0;
            case HIGH -> 1;
            case MODERATE -> 2;
            case LOW -> 3;
            default -> 4;
        };
    }
}