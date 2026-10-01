package com.bioimpedance.orchestration;

import com.bioimpedance.domain.applicability.ApplicabilityEngine;
import com.bioimpedance.domain.applicability.EvidenceSummaryBuilder;
import com.bioimpedance.domain.calculation.EquationEvaluator;
import com.bioimpedance.domain.config.ConfigurationMode;
import com.bioimpedance.domain.config.ProfessionalConfiguration;
import com.bioimpedance.domain.config.SystemConversionPolicy;
import com.bioimpedance.domain.contracts.AssessmentContext;
import com.bioimpedance.orchestration.assessmentflow.AssessmentFlowInput;
import com.bioimpedance.domain.contracts.AssessmentObjective;
import com.bioimpedance.domain.contracts.CandidateStatus;
import com.bioimpedance.domain.contracts.ClientProfile;
import com.bioimpedance.domain.contracts.SelectionNotAllowedException;
import com.bioimpedance.domain.contracts.Sex;
import com.bioimpedance.domain.contracts.SuggestionResult;
import com.bioimpedance.domain.conversion.DensityToFatConverter;
import com.bioimpedance.domain.conversionsuggestion.ConversionSuggestionEngine;
import com.bioimpedance.domain.conversionsuggestion.explanation.ConversionSuggestionExplanationBuilder;
import com.bioimpedance.domain.eligibility.EligibilityResolver;
import com.bioimpedance.domain.suggestion.CompatibilityRanker;
import com.bioimpedance.domain.suggestion.SuggestionEngine;
import com.bioimpedance.domain.suggestion.explanation.SuggestionExplanationBuilder;
import com.bioimpedance.domain.validation.MeasurementValidator;
import com.bioimpedance.library.conversions.ConversionDefinitionRegistry;
import com.bioimpedance.library.equations.EquationVariantRegistry;
import com.bioimpedance.library.measurements.InputTypeCatalog;
import com.bioimpedance.library.scientificrules.ScientificRuleRegistry;
import com.bioimpedance.orchestration.assessmentflow.AssessmentFlowAssessment;
import com.bioimpedance.orchestration.assessmentflow.AssessmentFlowOrchestrator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Golden tests do guard de seleção (Fase 13 / B1 — DEC-53/DEC-54).
 * <p>
 * Cobrem: INELIGIBLE recusada · DISABLED recusada · MISSING_INPUTS recusada
 * com erro de domínio (não IllegalArgumentException do evaluator) ·
 * READY+WARNING sem motivo recusada na finalização · override sem motivo
 * recusado na finalização · conversão desabilitada recusada.
 */
class AssessmentFlowGuardGoldenTest {

    private AssessmentFlowOrchestrator orchestrator;

    @BeforeEach
    void setUp() {
        EquationVariantRegistry equationVariantRegistry = new EquationVariantRegistry();
        ScientificRuleRegistry scientificRuleRegistry = new ScientificRuleRegistry();
        ConversionDefinitionRegistry conversionDefinitionRegistry = new ConversionDefinitionRegistry();
        InputTypeCatalog inputTypeCatalog = new InputTypeCatalog();
        MeasurementValidator measurementValidator = new MeasurementValidator(inputTypeCatalog);
        EvidenceSummaryBuilder evidenceSummaryBuilder = new EvidenceSummaryBuilder();
        ApplicabilityEngine applicabilityEngine = new ApplicabilityEngine(evidenceSummaryBuilder);
        EligibilityResolver eligibilityResolver = new EligibilityResolver();
        CompatibilityRanker compatibilityRanker = new CompatibilityRanker();
        SuggestionExplanationBuilder suggestionExplanationBuilder = new SuggestionExplanationBuilder();
        SuggestionEngine suggestionEngine = new SuggestionEngine(compatibilityRanker, suggestionExplanationBuilder);
        ConversionSuggestionExplanationBuilder conversionExplanationBuilder = new ConversionSuggestionExplanationBuilder();
        ConversionSuggestionEngine conversionSuggestionEngine = new ConversionSuggestionEngine(conversionExplanationBuilder);
        EquationEvaluator equationEvaluator = new EquationEvaluator();
        DensityToFatConverter densityToFatConverter = new DensityToFatConverter();
        SystemConversionPolicy systemConversionPolicy = SystemConversionPolicy.platformDefault();

        orchestrator = new AssessmentFlowOrchestrator(
            measurementValidator,
            applicabilityEngine,
            eligibilityResolver,
            suggestionEngine,
            conversionSuggestionEngine,
            equationEvaluator,
            densityToFatConverter,
            equationVariantRegistry,
            scientificRuleRegistry,
            conversionDefinitionRegistry,
            systemConversionPolicy
        );
    }

    // ============ Helpers ============

    private ProfessionalConfiguration config(List<String> variants, List<String> conversions) {
        return new ProfessionalConfiguration(
            "professional-guard", ConfigurationMode.DEFAULT, variants, conversions, null);
    }

    private AssessmentFlowInput input(
        String selectedVariantId,
        Map<String, Double> measurements,
        ProfessionalConfiguration config,
        Sex sex, int age,
        String selectedConversionId
    ) {
        ClientProfile client = new ClientProfile(sex, age, Boolean.FALSE, null, null);
        AssessmentContext context = new AssessmentContext(client, AssessmentObjective.GENERAL_FOLLOW_UP);
        return new AssessmentFlowInput(
            "assessment-guard",
            context,
            measurements,
            config,
            selectedVariantId,
            false,
            null,
            selectedConversionId,
            false,
            null
        );
    }

    /** 7 dobras do JP7 + AGE — cobre JP7-M/JP7-F e G-M3 (subconjunto). */
    private Map<String, Double> jp7Measurements(int age) {
        Map<String, Double> m = new HashMap<>();
        m.put("AGE", (double) age);
        m.put("SKINFOLD_PECTORAL", 10.0);
        m.put("SKINFOLD_AXILLARY_MID", 9.0);
        m.put("SKINFOLD_TRICEPS", 8.0);
        m.put("SKINFOLD_SUBSCAPULAR", 14.0);
        m.put("SKINFOLD_ABDOMEN", 15.0);
        m.put("SKINFOLD_SUPRAILIAC", 11.0);
        m.put("SKINFOLD_THIGH", 12.0);
        return m;
    }

    // ============ Guard de variante (DEC-53) ============

    @Test
    void ineligibleVariant_isRejected() {
        // JP7-F é female-only; cliente MALE → INELIGIBLE por SEX_NOT_SUPPORTED.
        ProfessionalConfiguration config = config(List.of("JP7-F"), List.of("siri", "brozek"));
        AssessmentFlowInput in = input("JP7-F", jp7Measurements(44), config, Sex.MALE, 44, null);

        SelectionNotAllowedException ex = assertThrows(
            SelectionNotAllowedException.class, () -> orchestrator.execute(in));
        assertEquals(SelectionNotAllowedException.CODE_SELECTION_NOT_ALLOWED, ex.getCode());
        assertEquals("JP7-F", ex.getTargetId());
        assertEquals(CandidateStatus.INELIGIBLE.name(), ex.getStatus());
    }

    @Test
    void disabledVariant_isRejected() {
        // Config habilita apenas JP7-M; selecionar G-M3 → DISABLED.
        ProfessionalConfiguration config = config(List.of("JP7-M"), List.of("siri", "brozek"));
        AssessmentFlowInput in = input("G-M3", jp7Measurements(44), config, Sex.MALE, 44, null);

        SelectionNotAllowedException ex = assertThrows(
            SelectionNotAllowedException.class, () -> orchestrator.execute(in));
        assertEquals(SelectionNotAllowedException.CODE_SELECTION_NOT_ALLOWED, ex.getCode());
        assertEquals("G-M3", ex.getTargetId());
        assertEquals(CandidateStatus.DISABLED.name(), ex.getStatus());
    }

    @Test
    void missingInputs_isRejectedWithDomainError_notEvaluatorIllegalArgument() {
        // JP7-M com apenas 3 das 7 dobras → MISSING_INPUTS. O guard deve
        // lançar SelectionNotAllowedException ANTES do EquationEvaluator.
        ProfessionalConfiguration config = config(List.of("JP7-M"), List.of("siri", "brozek"));
        Map<String, Double> partial = new HashMap<>();
        partial.put("AGE", 44.0);
        partial.put("SKINFOLD_TRICEPS", 8.0);
        partial.put("SKINFOLD_SUBSCAPULAR", 14.0);
        partial.put("SKINFOLD_SUPRAILIAC", 11.0);
        AssessmentFlowInput in = input("JP7-M", partial, config, Sex.MALE, 44, null);

        SelectionNotAllowedException ex = assertThrows(
            SelectionNotAllowedException.class, () -> orchestrator.execute(in));
        assertEquals(SelectionNotAllowedException.CODE_SELECTION_NOT_ALLOWED, ex.getCode());
        assertEquals(CandidateStatus.MISSING_INPUTS.name(), ex.getStatus());
    }

    @Test
    void readyVariant_executesSuccessfully() {
        // Controle positivo: variante READY não é bloqueada pelo guard.
        ProfessionalConfiguration config = config(List.of("JP7-M"), List.of("siri", "brozek"));
        AssessmentFlowInput in = input("JP7-M", jp7Measurements(44), config, Sex.MALE, 44, null);

        var result = orchestrator.execute(in);
        assertEquals("JP7-M", result.selectedVariantId());
        assertEquals("BODY_FAT_PERCENTAGE", result.finalResult().outputType());
    }

    // ============ Guard de conversão (DEC-53) ============

    @Test
    void disabledConversion_isRejected() {
        // JP7-M produz BODY_DENSITY; config habilita só Brozek, mas a seleção
        // explícita é Siri → Siri DISABLED → recusa.
        ProfessionalConfiguration config = config(List.of("JP7-M"), List.of("brozek"));
        AssessmentFlowInput in = input("JP7-M", jp7Measurements(44), config, Sex.MALE, 44, "siri");

        SelectionNotAllowedException ex = assertThrows(
            SelectionNotAllowedException.class, () -> orchestrator.execute(in));
        assertEquals(SelectionNotAllowedException.CODE_SELECTION_NOT_ALLOWED, ex.getCode());
        assertEquals("siri", ex.getTargetId());
    }

    // ============ Regra de motivo (DEC-54) — só na finalização ============

    @Test
    void readyVariantWithWarnings_requiresReasonAtFinalize() {
        // Idade 70 está fora da faixa validada do JP7-M (18–59) → READY +
        // WARNING AGE_OUTSIDE_VALIDATED_RANGE. Sem motivo → REASON_REQUIRED.
        ProfessionalConfiguration config = config(List.of("JP7-M"), List.of("siri", "brozek"));
        AssessmentFlowInput in = input("JP7-M", jp7Measurements(70), config, Sex.MALE, 70, null);

        AssessmentFlowAssessment assessment = orchestrator.assess(in);
        SuggestionResult suggestion = assessment.suggestionResult();

        SelectionNotAllowedException ex = assertThrows(
            SelectionNotAllowedException.class,
            () -> orchestrator.validateSelectionReason(suggestion, "JP7-M", null));
        assertEquals(SelectionNotAllowedException.CODE_REASON_REQUIRED, ex.getCode());

        // Com motivo presente → não lança.
        orchestrator.validateSelectionReason(suggestion, "JP7-M", "Preferência profissional");
    }

    @Test
    void overrideWithoutReason_isRejectedAtFinalize() {
        // Idade 25: JP7-M tem age EXACT (18–59) e G-M3 tem PARTIAL (18–30,
        // sem bounds validados). JP7-M é sugerido; G-M3 fica READY mas fora
        // do conjunto sugerido → override. Sem motivo → REASON_REQUIRED.
        ProfessionalConfiguration config = config(List.of("JP7-M", "G-M3"), List.of("siri", "brozek"));
        AssessmentFlowInput in = input("G-M3", jp7Measurements(25), config, Sex.MALE, 25, null);

        AssessmentFlowAssessment assessment = orchestrator.assess(in);
        SuggestionResult suggestion = assessment.suggestionResult();

        // Confirma o cenário: JP7-M sugerido, G-M3 pronto mas não sugerido.
        assertEquals(List.of("JP7-M"),
            suggestion.suggestedVariants().stream()
                .map(com.bioimpedance.domain.contracts.CandidateVariantSummary::variantId)
                .toList());

        SelectionNotAllowedException ex = assertThrows(
            SelectionNotAllowedException.class,
            () -> orchestrator.validateSelectionReason(suggestion, "G-M3", null));
        assertEquals(SelectionNotAllowedException.CODE_REASON_REQUIRED, ex.getCode());
    }
}