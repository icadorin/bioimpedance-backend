package com.bioimpedance.domain.conversionsuggestion;

import com.bioimpedance.domain.contracts.ConversionCandidateInput;
import com.bioimpedance.domain.contracts.ConversionCandidateSummary;
import com.bioimpedance.domain.contracts.ConversionStatus;
import com.bioimpedance.domain.contracts.ConversionSuggestionResult;
import com.bioimpedance.domain.contracts.ConversionSuggestionStatus;
import com.bioimpedance.domain.conversionsuggestion.explanation.ConversionSuggestionExplanationBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Indica quais conversões são compatíveis com um PredictionResult e qual
 * o sistema sugere.
 * <p>
 * Fonte: architecture.md §12 + doc.md §16.4.
 * <p>
 * O que este engine NÃO faz (architecture.md §12.2): não executa conversão,
 * não altera PredictionResult, não cria ConversionResult, não força conversão
 * inelegível, não interpreta Siri como cientificamente superior.
 * <p>
 * DEC-23: recebe record neutro + outputType como String, sem depender de
 * library/conversions nem de domain/calculation.
 */
@Service
public class ConversionSuggestionEngine {

    public static final String ENGINE_VERSION = "conversion-suggestion-engine-v1";

    private final ConversionSuggestionExplanationBuilder explanationBuilder;

    public ConversionSuggestionEngine(ConversionSuggestionExplanationBuilder explanationBuilder) {
        this.explanationBuilder = explanationBuilder;
    }

    public ConversionSuggestionResult suggest(String predictionOutputType,
                                              Collection<ConversionCandidateInput> conversions,
                                              String professionalPreferenceConversionId,
                                              String defaultConversionId,
                                              Collection<String> enabledConversionIds) {

        // 1. Conversões aplicáveis: inputType bate com o outputType do resultado.
        List<ConversionCandidateInput> applicable = conversions.stream()
            .filter(c -> c.inputType().equals(predictionOutputType))
            .toList();
        if (applicable.isEmpty()) {
            return noConversionNeeded(predictionOutputType);
        }

        // 2. Classificar cada conversão aplicável.
        List<CandidateState> states = new ArrayList<>();
        for (ConversionCandidateInput c : applicable) {
            states.add(new CandidateState(c, resolveStatus(c, enabledConversionIds)));
        }

        // 3. Separar candidatas (READY) e excluídas, em ordem determinística.
        List<CandidateState> ready = states.stream()
            .filter(s -> s.status() == ConversionStatus.READY)
            .sorted(Comparator.comparing(s -> s.input().conversionId()))
            .toList();
        List<CandidateState> excluded = states.stream()
            .filter(s -> s.status() != ConversionStatus.READY)
            .sorted(Comparator.comparing(s -> s.input().conversionId()))
            .toList();

        // 4. Estado global.
        ConversionSuggestionStatus globalStatus = resolveGlobalStatus(ready, excluded);

        // 5. Summaries.
        List<ConversionCandidateSummary> candidateSummaries = ready.stream()
            .map(this::toSummary)
            .toList();
        List<ConversionCandidateSummary> excludedSummaries = excluded.stream()
            .map(this::toSummary)
            .toList();

        // 6. Seleção da(s) sugerida(s): preferência > default > empate.
        List<ConversionCandidateSummary> suggestedSummaries =
            selectSuggested(ready, professionalPreferenceConversionId, defaultConversionId);

        // 7. Warnings e reasons globais (determinísticos).
        List<String> warnings = candidateSummaries.stream()
            .flatMap(c -> c.warnings().stream())
            .distinct()
            .sorted()
            .toList();
        List<String> reasons = globalReasons(globalStatus, suggestedSummaries);

        return new ConversionSuggestionResult(globalStatus, suggestedSummaries,
            candidateSummaries, excludedSummaries, warnings, reasons, ENGINE_VERSION);
    }

    private ConversionStatus resolveStatus(ConversionCandidateInput c,
                                           Collection<String> enabledConversionIds) {
        if (!enabledConversionIds.contains(c.conversionId())) {
            return ConversionStatus.DISABLED;
        }
        return ConversionStatus.READY;
    }

    private ConversionSuggestionStatus resolveGlobalStatus(List<CandidateState> ready,
                                                           List<CandidateState> excluded) {
        if (!ready.isEmpty()) {
            return ConversionSuggestionStatus.SUGGESTED;
        }
        boolean allDisabled = excluded.stream()
            .allMatch(s -> s.status() == ConversionStatus.DISABLED);
        return allDisabled
            ? ConversionSuggestionStatus.NO_ENABLED_CONVERSION
            : ConversionSuggestionStatus.NO_ELIGIBLE_CONVERSION;
    }

    private List<ConversionCandidateSummary> selectSuggested(List<CandidateState> ready,
                                                             String preferenceId,
                                                             String defaultId) {
        if (ready.isEmpty()) {
            return List.of();
        }
        // 1. Preferência profissional válida e READY.
        if (preferenceId != null) {
            CandidateState preferred = findById(ready, preferenceId);
            if (preferred != null) {
                return List.of(toSummary(preferred));
            }
        }
        // 2. Default operacional, se READY.
        if (defaultId != null) {
            CandidateState defaultConv = findById(ready, defaultId);
            if (defaultConv != null) {
                return List.of(toSummary(defaultConv));
            }
        }
        // 3. Empate: sugerir todas as READY.
        return ready.stream().map(this::toSummary).toList();
    }

    private CandidateState findById(List<CandidateState> states, String conversionId) {
        return states.stream()
            .filter(s -> s.input().conversionId().equals(conversionId))
            .findFirst()
            .orElse(null);
    }

    private ConversionCandidateSummary toSummary(CandidateState state) {
        List<String> reasons = explanationBuilder.buildReasons(state.input(), state.status());
        return new ConversionCandidateSummary(
            state.input().conversionId(),
            state.status(),
            reasons,
            List.of());
    }

    private ConversionSuggestionResult noConversionNeeded(String predictionOutputType) {
        List<String> reasons = List.of(
            "Nenhuma conversão aplicável para o tipo de resultado ("
                + predictionOutputType + "); o resultado já está no formato final.");
        return new ConversionSuggestionResult(ConversionSuggestionStatus.NO_CONVERSION_NEEDED,
            List.of(), List.of(), List.of(), List.of(), reasons, ENGINE_VERSION);
    }

    private List<String> globalReasons(ConversionSuggestionStatus status,
                                       List<ConversionCandidateSummary> suggested) {
        return switch (status) {
            case SUGGESTED -> List.of(suggested.size() == 1
                ? "1 conversão compatível sugerida."
                : suggested.size() + " conversões igualmente compatíveis sugeridas.");
            case NO_CONVERSION_NEEDED -> List.of(
                "O resultado já está no formato final; nenhuma conversão é necessária.");
            case NO_ELIGIBLE_CONVERSION -> List.of(
                "Nenhuma conversão elegível para este tipo de resultado.");
            case NO_ENABLED_CONVERSION -> List.of(
                "Existem conversões aplicáveis, mas todas estão desabilitadas.");
        };
    }

    /** Agrupamento interno input + status resolvido. */
    private record CandidateState(ConversionCandidateInput input, ConversionStatus status) {}
}