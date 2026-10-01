package com.bioimpedance.orchestration.assessmentflow;

import com.bioimpedance.domain.applicability.ApplicabilityEngine;
import com.bioimpedance.domain.audit.AuditSnapshot;
import com.bioimpedance.domain.calculation.EquationEvaluator;
import com.bioimpedance.domain.calculation.PredictionResult;
import com.bioimpedance.domain.config.SystemConversionPolicy;
import com.bioimpedance.domain.contracts.*;
import com.bioimpedance.domain.conversion.DensityToFatConverter;
import com.bioimpedance.domain.conversionsuggestion.ConversionSuggestionEngine;
import com.bioimpedance.domain.eligibility.EligibilityResolver;
import com.bioimpedance.domain.suggestion.SuggestionEngine;
import com.bioimpedance.domain.validation.MeasurementValidator;
import com.bioimpedance.library.conversions.ConversionDefinition;
import com.bioimpedance.library.conversions.ConversionDefinitionRegistry;
import com.bioimpedance.library.equations.EquationVariantRegistry;
import com.bioimpedance.library.equations.FormulaDefinition;
import com.bioimpedance.library.scientificrules.EquationVariantScientificProfile;
import com.bioimpedance.library.scientificrules.ScientificRuleRegistry;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Coordena o pipeline completo de avaliação física.
 * <p>
 * Fonte: architecture.md §23.
 * <p>
 * A orchestration COORDENA, mas NÃO IMPLEMENTA regras científicas.
 * Não contém: if idade > X, if sexo == Y, if athlete == true, if variant == JP7.
 * <p>
 * DEC-53: a variante escolhida deve estar READY; a conversão escolhida deve
 * estar habilitada e elegível. Violação lança {@link SelectionNotAllowedException}
 * (domain), sem executar cálculo nem gravar auditoria.
 */
public class AssessmentFlowOrchestrator {

    private final MeasurementValidator measurementValidator;
    private final ApplicabilityEngine applicabilityEngine;
    private final EligibilityResolver eligibilityResolver;
    private final SuggestionEngine suggestionEngine;
    private final ConversionSuggestionEngine conversionSuggestionEngine;
    private final EquationEvaluator equationEvaluator;
    private final DensityToFatConverter densityToFatConverter;
    private final EquationVariantRegistry equationVariantRegistry;
    private final ScientificRuleRegistry scientificRuleRegistry;
    private final ConversionDefinitionRegistry conversionDefinitionRegistry;
    private final SystemConversionPolicy systemConversionPolicy;

    public AssessmentFlowOrchestrator(
        MeasurementValidator measurementValidator,
        ApplicabilityEngine applicabilityEngine,
        EligibilityResolver eligibilityResolver,
        SuggestionEngine suggestionEngine,
        ConversionSuggestionEngine conversionSuggestionEngine,
        EquationEvaluator equationEvaluator,
        DensityToFatConverter densityToFatConverter,
        EquationVariantRegistry equationVariantRegistry,
        ScientificRuleRegistry scientificRuleRegistry,
        ConversionDefinitionRegistry conversionDefinitionRegistry,
        SystemConversionPolicy systemConversionPolicy
    ) {
        this.measurementValidator = measurementValidator;
        this.applicabilityEngine = applicabilityEngine;
        this.eligibilityResolver = eligibilityResolver;
        this.suggestionEngine = suggestionEngine;
        this.conversionSuggestionEngine = conversionSuggestionEngine;
        this.equationEvaluator = equationEvaluator;
        this.densityToFatConverter = densityToFatConverter;
        this.equationVariantRegistry = equationVariantRegistry;
        this.scientificRuleRegistry = scientificRuleRegistry;
        this.conversionDefinitionRegistry = conversionDefinitionRegistry;
        this.systemConversionPolicy = systemConversionPolicy;
    }

    /**
     * Etapas 1–9 do pipeline (doc.md §13): validação, aplicabilidade,
     * eligibility/readiness e sugestão — ANTES de qualquer execução.
     * <p>
     * DEC-53: extração aditiva prevista no DEC-40(c). Base do painel de
     * estado (doc.md §17) e do guard de seleção.
     */
    public AssessmentFlowAssessment assess(AssessmentFlowInput input) {
        Collection<EquationVariantScientificProfile> profiles = scientificRuleRegistry.all();

        // 6. Executar validação.
        ValidationResult validationResult = measurementValidator.validate(input.measurements());

        // 7. Executar aplicabilidade para cada variante.
        Map<String, ApplicabilityResult> applicabilityResults = new HashMap<>();
        for (EquationVariantScientificProfile profile : profiles) {
            String variantId = profile.identity().variantId();
            ApplicabilityResult applicabilityResult =
                applicabilityEngine.evaluate(profile, input.context());
            applicabilityResults.put(variantId, applicabilityResult);
        }

        // 8. Resolver eligibility/readiness.
        List<ReadinessResult> readinessResults = new ArrayList<>();
        for (EquationVariantScientificProfile profile : profiles) {
            String variantId = profile.identity().variantId();
            ApplicabilityResult applicability = applicabilityResults.get(variantId);
            Collection<String> requiredInputIds = profile.inputs().requiredInputs();
            Collection<String> availableInputIds = input.measurements().keySet();
            boolean enabled = input.professionalConfiguration()
                .enabledVariantIds().contains(variantId);
            ReadinessResult readiness = eligibilityResolver.resolve(
                applicability, requiredInputIds, availableInputIds, enabled);
            readinessResults.add(readiness);
        }

        // 9. Executar SuggestionEngine.
        SuggestionResult suggestionResult =
            suggestionEngine.suggest(readinessResults, applicabilityResults);

        return new AssessmentFlowAssessment(
            suggestionResult, applicabilityResults, readinessResults, validationResult);
    }

    /**
     * Executa o fluxo completo de avaliação.
     * <p>
     * Fonte: architecture.md §23.
     * <p>
     * DEC-53: impõe o guard de seleção antes de executar a variante e a
     * conversão. A recusa lança {@link SelectionNotAllowedException} sem
     * executar cálculo nem construir auditoria.
     */
    public AssessmentFlowResult execute(AssessmentFlowInput input) {
        // 1–9. Assessment, Context, Config + validação + aplicabilidade +
        //      eligibility + sugestão.
        AssessmentFlowAssessment assessment = assess(input);
        SuggestionResult suggestionResult = assessment.suggestionResult();

        // 10. Receber ProfessionalSelection (já no input).
        String selectedVariantId = input.selectedVariantId();

        // Guard (DEC-53): a variante escolhida deve estar READY.
        guardVariantSelection(suggestionResult, selectedVariantId);

        // 11. Executar cálculo da variante selecionada.
        FormulaDefinition formula = equationVariantRegistry.resolve(selectedVariantId);
        PredictionResult variantPrediction =
            equationEvaluator.evaluate(formula, input.measurements());

        // 12–14. Conversão (quando aplicável).
        ConversionSuggestionResult conversionSuggestionResult = null;
        String selectedConversionId = null;
        PredictionResult conversionPrediction = null;
        PredictionResult finalResult = variantPrediction;

        if (needsConversion(variantPrediction.outputType())) {
            List<ConversionCandidateInput> conversionCandidates = buildConversionCandidates();
            String defaultConversionId = systemConversionPolicy.defaultConversionId();
            String preferenceConversionId =
                input.professionalConfiguration().preferredConversionId();
            Collection<String> enabledConversionIds =
                input.professionalConfiguration().enabledConversionIds();

            conversionSuggestionResult = conversionSuggestionEngine.suggest(
                variantPrediction.outputType(),
                conversionCandidates,
                preferenceConversionId,
                defaultConversionId,
                enabledConversionIds
            );

            // 13. Receber ConversionSelection (já no input) ou usar default.
            selectedConversionId = input.selectedConversionId();
            if (selectedConversionId == null) {
                selectedConversionId = defaultConversionId;
            }

            // Guard (DEC-53): a conversão escolhida deve estar habilitada e elegível.
            guardConversionSelection(conversionSuggestionResult, selectedConversionId);

            // 14. Executar conversão.
            ConversionDefinition conversionDefinition =
                conversionDefinitionRegistry.resolve(selectedConversionId);
            conversionPrediction = densityToFatConverter.convert(
                conversionDefinition, variantPrediction.value());
            finalResult = conversionPrediction;
        }

        // 16. Registrar auditoria.
        AuditSnapshot auditSnapshot = buildAuditSnapshot(
            input, suggestionResult, selectedVariantId, variantPrediction,
            conversionSuggestionResult, selectedConversionId,
            conversionPrediction, finalResult);

        return new AssessmentFlowResult(
            input.assessmentId(),
            suggestionResult,
            selectedVariantId,
            variantPrediction,
            conversionSuggestionResult,
            selectedConversionId,
            conversionPrediction,
            finalResult,
            auditSnapshot
        );
    }

    /**
     * Regra de motivo obrigatório (DEC-54) — verificação SEPARADA do guard
     * (DEC-53) e chamada SOMENTE na finalização (DEC-58). Calcular é
     * exploração e não exige motivo.
     * <p>
     * Obrigatório quando:
     * <ul>
     *   <li>{@code override = true} (escolhida fora do conjunto sugerido,
     *       DEC-38) E existe ao menos uma variante sugerida — sem sugestão
     *       não há do que divergir; OU</li>
     *   <li>a variante escolhida está READY com warnings.</li>
     * </ul>
     * Para conversão o motivo é sempre opcional (doc.md §24.1).
     */
    public void validateSelectionReason(SuggestionResult suggestionResult,
                                        String selectedVariantId,
                                        String overrideReason) {
        boolean hasReason = overrideReason != null && !overrideReason.isBlank();
        if (hasReason) {
            return;
        }

        boolean override = suggestionResult.suggestedVariants().stream()
            .noneMatch(v -> v.variantId().equals(selectedVariantId));
        boolean hasSuggested = !suggestionResult.suggestedVariants().isEmpty();

        CandidateVariantSummary selected = findVariantSummary(suggestionResult, selectedVariantId);
        boolean readyWithWarnings = selected != null
            && selected.status() == CandidateStatus.READY
            && !selected.warnings().isEmpty();

        boolean reasonRequired = (override && hasSuggested) || readyWithWarnings;
        if (!reasonRequired) {
            return;
        }

        List<String> reasons = new ArrayList<>();
        if (override && hasSuggested) {
            reasons.add("A variante escolhida é diferente da sugerida; informe o motivo.");
        }
        if (readyWithWarnings) {
            reasons.add("A variante escolhida está pronta, mas possui alertas; informe o motivo.");
        }
        throw new SelectionNotAllowedException(
            SelectionNotAllowedException.CODE_REASON_REQUIRED,
            selectedVariantId,
            CandidateStatus.READY.name(),
            reasons);
    }

    // ============ Guard de seleção (DEC-53) ============

    private void guardVariantSelection(SuggestionResult suggestionResult, String selectedVariantId) {
        CandidateStatus status = findVariantStatus(suggestionResult, selectedVariantId);
        if (status == CandidateStatus.READY) {
            return;
        }
        String statusName = status != null ? status.name() : "UNKNOWN";
        List<String> reasons = findVariantReasons(suggestionResult, selectedVariantId);
        if (reasons.isEmpty()) {
            reasons = List.of("Variante não está pronta para execução.");
        }
        throw new SelectionNotAllowedException(
            SelectionNotAllowedException.CODE_SELECTION_NOT_ALLOWED,
            selectedVariantId,
            statusName,
            reasons);
    }

    private void guardConversionSelection(ConversionSuggestionResult conversionSuggestionResult,
                                          String selectedConversionId) {
        if (conversionSuggestionResult == null || selectedConversionId == null) {
            return;
        }
        ConversionStatus status = findConversionStatus(conversionSuggestionResult, selectedConversionId);
        if (status == ConversionStatus.READY) {
            return;
        }
        String statusName = status != null ? status.name() : "UNKNOWN";
        List<String> reasons = findConversionReasons(conversionSuggestionResult, selectedConversionId);
        if (reasons.isEmpty()) {
            reasons = List.of("Conversão não está em condição de execução.");
        }
        throw new SelectionNotAllowedException(
            SelectionNotAllowedException.CODE_SELECTION_NOT_ALLOWED,
            selectedConversionId,
            statusName,
            reasons);
    }

    // ============ Helpers de busca no SuggestionResult ============

    private CandidateVariantSummary findVariantSummary(SuggestionResult suggestionResult, String variantId) {
        return Stream.concat(
                suggestionResult.candidateVariants().stream(),
                suggestionResult.excludedVariants().stream())
            .filter(v -> v.variantId().equals(variantId))
            .findFirst()
            .orElse(null);
    }

    private CandidateStatus findVariantStatus(SuggestionResult suggestionResult, String variantId) {
        CandidateVariantSummary summary = findVariantSummary(suggestionResult, variantId);
        return summary != null ? summary.status() : null;
    }

    private List<String> findVariantReasons(SuggestionResult suggestionResult, String variantId) {
        CandidateVariantSummary summary = findVariantSummary(suggestionResult, variantId);
        if (summary == null) {
            return List.of();
        }
        List<String> reasons = new ArrayList<>();
        reasons.addAll(summary.reasons());
        reasons.addAll(summary.warnings());
        return reasons;
    }

    private ConversionCandidateSummary findConversionSummary(ConversionSuggestionResult result, String conversionId) {
        return Stream.concat(
                result.candidateConversions().stream(),
                result.excludedConversions().stream())
            .filter(c -> c.conversionId().equals(conversionId))
            .findFirst()
            .orElse(null);
    }

    private ConversionStatus findConversionStatus(ConversionSuggestionResult result, String conversionId) {
        ConversionCandidateSummary summary = findConversionSummary(result, conversionId);
        return summary != null ? summary.status() : null;
    }

    private List<String> findConversionReasons(ConversionSuggestionResult result, String conversionId) {
        ConversionCandidateSummary summary = findConversionSummary(result, conversionId);
        if (summary == null) {
            return List.of();
        }
        List<String> reasons = new ArrayList<>();
        reasons.addAll(summary.reasons());
        reasons.addAll(summary.warnings());
        return reasons;
    }

    // ============ (inalterados) ============

    /** doc.md §24: override = escolha profissional fora do conjunto sugerido. */
    private boolean variantOverride(SuggestionResult suggestionResult, String selectedVariantId) {
        return suggestionResult.suggestedVariants().stream()
            .noneMatch(v -> v.variantId().equals(selectedVariantId));
    }

    private boolean needsConversion(String outputType) {
        return "BODY_DENSITY".equals(outputType);
    }

    private List<ConversionCandidateInput> buildConversionCandidates() {
        Collection<ConversionDefinition> conversions =
            conversionDefinitionRegistry.getAll().values();
        List<ConversionCandidateInput> candidates = new ArrayList<>();
        for (ConversionDefinition conversion : conversions) {
            candidates.add(new ConversionCandidateInput(
                conversion.id(),
                conversion.inputType(),
                conversion.outputType()
            ));
        }
        return candidates;
    }

    private AuditSnapshot buildAuditSnapshot(
        AssessmentFlowInput input,
        SuggestionResult suggestionResult,
        String selectedVariantId,
        PredictionResult variantPrediction,
        ConversionSuggestionResult conversionSuggestionResult,
        String selectedConversionId,
        PredictionResult conversionPrediction,
        PredictionResult finalResult
    ) {
        String suggestedVariantId = suggestionResult.suggestedVariants().isEmpty()
            ? null
            : suggestionResult.suggestedVariants().getFirst().variantId();
        String suggestedConversionId = (conversionSuggestionResult == null
            || conversionSuggestionResult.suggestedConversions().isEmpty())
            ? null
            : conversionSuggestionResult.suggestedConversions().getFirst().conversionId();

        return new AuditSnapshot(
            UUID.randomUUID().toString(),
            input.assessmentId(),
            Instant.now(),
            input.context(),
            input.measurements(),
            suggestionResult.candidateVariants().stream()
                .map(CandidateVariantSummary::variantId)
                .toList(),
            suggestionResult.status().name(),
            suggestedVariantId,
            selectedVariantId,
            variantOverride(suggestionResult, selectedVariantId),
            input.variantOverrideReason(),
            selectedVariantId,
            "1",
            variantPrediction.outputType(),
            variantPrediction.value(),
            selectedConversionId,
            "1",
            conversionPrediction != null ? conversionPrediction.outputType() : null,
            conversionPrediction != null ? conversionPrediction.value() : null,
            suggestedConversionId,
            selectedConversionId,
            "suggestion-engine-v1",
            "conversion-suggestion-engine-v1",
            "scientific-rules-v1",
            "evidence-v1",
            "config-v1"
        );
    }
}