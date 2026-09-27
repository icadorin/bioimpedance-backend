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

/**
 * Coordena o pipeline completo de avaliação física.
 * <p>
 * Fonte: architecture.md §23.
 * <p>
 * A orchestration COORDENA, mas NÃO IMPLEMENTA regras científicas.
 * Não contém: if idade > X, if sexo == Y, if athlete == true, if variant == JP7.
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
     * Executa o fluxo completo de avaliação.
     * <p>
     * Fonte: architecture.md §23.
     */
    public AssessmentFlowResult execute(AssessmentFlowInput input) {
        // 1-3. Assessment, Context e Config já estão no input.

        // 4-5. Carregar variantes e perfis científicos.
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

        // 10. Receber ProfessionalSelection (já no input).
        String selectedVariantId = input.selectedVariantId();

        // 11. Executar cálculo da variante selecionada.
        FormulaDefinition formula = equationVariantRegistry.resolve(selectedVariantId);
        PredictionResult variantPrediction =
            equationEvaluator.evaluate(formula, input.measurements());

        // 12-14. Conversão (quando aplicável).
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