package com.bioimpedance.domain.suggestion;

import com.bioimpedance.domain.contracts.ApplicabilityResult;
import com.bioimpedance.domain.contracts.CandidateStatus;
import com.bioimpedance.domain.contracts.CandidateVariantSummary;
import com.bioimpedance.domain.contracts.MatchResult;
import com.bioimpedance.domain.contracts.MeasurementQuality;
import com.bioimpedance.domain.contracts.ReadinessResult;
import com.bioimpedance.domain.contracts.SuggestionCriteriaSummary;
import com.bioimpedance.domain.contracts.SuggestionResult;
import com.bioimpedance.domain.contracts.SuggestionStatus;
import com.bioimpedance.domain.suggestion.explanation.SuggestionExplanationBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Indica as variantes compatíveis com o cenário atual.
 * <p>
 * Fonte: architecture.md §11 + especificacao_cientifica.md §11–12 +
 * doc.md §9.3.
 * <p>
 * Contrato de entrada: para cada {@link ReadinessResult} presente em
 * {@code readinessResults}, deve existir um {@link ApplicabilityResult}
 * correspondente em {@code applicabilityResults} com o mesmo variantId.
 * A orchestration (Fase 11) é responsável por produzir os dois mapas
 * alinhados — se não o fizer, {@link #toSummary} lança
 * {@link IllegalStateException} explicitamente, consistentemente com o
 * padrão fail-fast do resto do domínio (EquationEvaluator.coeff(),
 * requireNamedInput()).
 */
@Service
public class SuggestionEngine {

    public static final String ENGINE_VERSION = "suggestion-engine-v1";

    private final CompatibilityRanker ranker;
    private final SuggestionExplanationBuilder explanationBuilder;

    public SuggestionEngine(CompatibilityRanker ranker,
                            SuggestionExplanationBuilder explanationBuilder) {
        this.ranker = ranker;
        this.explanationBuilder = explanationBuilder;
    }

    public SuggestionResult suggest(List<ReadinessResult> readinessResults,
                                    Map<String, ApplicabilityResult> applicabilityResults) {

        List<ReadinessResult> all = List.copyOf(readinessResults);
        List<ReadinessResult> ready = byStatus(all, CandidateStatus.READY);
        List<ReadinessResult> missing = byStatus(all, CandidateStatus.MISSING_INPUTS);
        List<ReadinessResult> ineligible = byStatus(all, CandidateStatus.INELIGIBLE);
        List<ReadinessResult> disabled = byStatus(all, CandidateStatus.DISABLED);

        SuggestionStatus status = resolveStatus(all, ready, missing, disabled);

        List<CandidateVariantSummary> candidates = new ArrayList<>();
        sortById(ready).forEach(r -> candidates.add(toSummary(r, applicabilityResults)));
        sortById(missing).forEach(r -> candidates.add(toSummary(r, applicabilityResults)));

        List<CandidateVariantSummary> excluded = new ArrayList<>();
        sortById(ineligible).forEach(r -> excluded.add(toSummary(r, applicabilityResults)));
        sortById(disabled).forEach(r -> excluded.add(toSummary(r, applicabilityResults)));

        // HashSet basta — o Set só é usado para .contains(),
        // nunca para iterar em ordem.
        Set<String> suggestedIds = new HashSet<>(
            ranker.selectSuggestedVariantIds(ready, applicabilityResults));
        List<CandidateVariantSummary> suggested = candidates.stream()
            .filter(c -> suggestedIds.contains(c.variantId()))
            .toList();

        List<String> warnings = candidates.stream()
            .flatMap(c -> c.warnings().stream())
            .distinct()
            .sorted()
            .toList();

        return new SuggestionResult(status, suggested, candidates, excluded,
            warnings, globalReasons(status, suggested), ENGINE_VERSION);
    }

    private SuggestionStatus resolveStatus(List<ReadinessResult> all,
                                           List<ReadinessResult> ready,
                                           List<ReadinessResult> missing,
                                           List<ReadinessResult> disabled) {
        if (all.isEmpty()) {
            return SuggestionStatus.NO_ELIGIBLE_METHOD;
        }
        if (disabled.size() == all.size()) {
            return SuggestionStatus.NO_ENABLED_METHOD;
        }
        if (ready.isEmpty() && missing.isEmpty()) {
            return SuggestionStatus.NO_ELIGIBLE_METHOD;
        }
        if (ready.isEmpty()) {
            return SuggestionStatus.NO_READY_METHOD;
        }
        return SuggestionStatus.SUGGESTED;
    }

    private CandidateVariantSummary toSummary(ReadinessResult readiness,
                                              Map<String, ApplicabilityResult> applicabilityResults) {
        // Fail-fast: se a orchestration não produziu o ApplicabilityResult
        // correspondente, .get() retorna null — lançamos aqui mesmo, antes de
        // propagar esse null pra qualquer outro método. Não silenciamos
        // violação de contrato.
        ApplicabilityResult applicability = applicabilityResults.get(readiness.variantId());
        if (applicability == null) {
            throw new IllegalStateException(
                "ApplicabilityResult ausente para variantId=" + readiness.variantId()
                    + ". A orchestration deve produzir mapas alinhados.");
        }

        List<String> missingInputs = readiness.status() == CandidateStatus.MISSING_INPUTS
            ? readiness.missingInputs()
            : List.of();
        return new CandidateVariantSummary(
            readiness.variantId(),
            readiness.status(),
            buildCriteria(applicability, readiness),
            missingInputs,
            explanationBuilder.buildReasons(applicability, readiness),
            readiness.warnings());
    }

    /**
     * Variáveis locais nomeadas 1:1 com a ordem de
     * SuggestionCriteriaSummary evita troca silenciosa de posição
     * entre MatchResults (mesmo risco já resolvido em
     * EvidenceSummaryBuilder).
     */
    private SuggestionCriteriaSummary buildCriteria(ApplicabilityResult applicability,
                                                    ReadinessResult readiness) {
        MatchResult sex = applicability.sexMatch();
        MatchResult age = applicability.ageMatch();
        MatchResult population = applicability.populationMatch();
        MatchResult context = applicability.contextMatch();
        return new SuggestionCriteriaSummary(
            sex, age, population, context,
            applicability.evidenceSummary(),
            readiness.status(),
            MeasurementQuality.UNKNOWN);
    }

    private List<String> globalReasons(SuggestionStatus status,
                                       List<CandidateVariantSummary> suggested) {
        return switch (status) {
            case SUGGESTED -> List.of(suggested.size() == 1
                ? "1 variante compatível indicada."
                : suggested.size() + " variantes igualmente compatíveis indicadas.");
            case NO_READY_METHOD -> List.of(
                "Nenhuma variante pronta para execução; existem candidatas com inputs obrigatórios ausentes.");
            case NO_ELIGIBLE_METHOD -> List.of(
                "Nenhuma variante elegível para este perfil/contexto.");
            case NO_ENABLED_METHOD -> List.of(
                "Todas as variantes estão desabilitadas pela configuração.");
        };
    }

    private static List<ReadinessResult> byStatus(List<ReadinessResult> all, CandidateStatus status) {
        return all.stream().filter(r -> r.status() == status).toList();
    }

    private static List<ReadinessResult> sortById(List<ReadinessResult> list) {
        return list.stream()
            .sorted(Comparator.comparing(ReadinessResult::variantId))
            .toList();
    }
}