package com.bioimpedance.orchestration;

import com.bioimpedance.domain.applicability.ApplicabilityEngine;
import com.bioimpedance.domain.applicability.EvidenceSummaryBuilder;
import com.bioimpedance.domain.audit.AuditSnapshot;
import com.bioimpedance.domain.calculation.EquationEvaluator;
import com.bioimpedance.domain.config.ConfigurationMode;
import com.bioimpedance.domain.config.ProfessionalConfiguration;
import com.bioimpedance.domain.config.SystemConversionPolicy;
import com.bioimpedance.domain.contracts.AssessmentContext;
import com.bioimpedance.domain.contracts.AssessmentObjective;
import com.bioimpedance.domain.contracts.ClientProfile;
import com.bioimpedance.domain.contracts.Sex;
import com.bioimpedance.domain.contracts.TrainingLevel;
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
import com.bioimpedance.orchestration.assessmentflow.AssessmentFlowInput;
import com.bioimpedance.orchestration.assessmentflow.AssessmentFlowOrchestrator;
import com.bioimpedance.orchestration.assessmentflow.AssessmentFlowResult;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AssessmentFlowOrchestrationGoldenTest {

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

    @Test
    void fluxoCompleto_JP7M_para_BodyFatPercentage_com_Siri() {
        // Contexto: masculino, 44 anos, hipertrofia
        ClientProfile client = new ClientProfile(
            Sex.MALE,
            44,
            Boolean.FALSE,
            TrainingLevel.TRAINED,
            "musculação"
        );
        AssessmentContext context = new AssessmentContext(client, AssessmentObjective.HYPERTROPHY);

        // Medidas para JP7-M (7 dobras)
        Map<String, Double> measurements = Map.of(
            "AGE", 44.0,
            "SKINFOLD_PECTORAL", 10.0,
            "SKINFOLD_ABDOMEN", 15.0,
            "SKINFOLD_THIGH", 12.0,
            "SKINFOLD_TRICEPS", 8.0,
            "SKINFOLD_SUBSCAPULAR", 14.0,
            "SKINFOLD_SUPRAILIAC", 11.0,
            "SKINFOLD_AXILLARY_MID", 9.0
        );

        // Configuração profissional: tudo habilitado
        ProfessionalConfiguration config = new ProfessionalConfiguration(
            "professional-001",
            ConfigurationMode.DEFAULT,
            List.of("JP7-M", "JP3-M", "G-M7", "P-M7", "FALK4"),
            List.of("siri", "brozek"),
            null
        );

        AssessmentFlowInput input = new AssessmentFlowInput(
            "assessment-001",
            context,
            measurements,
            config,
            "JP7-M",
            false,
            null,
            null,
            false,
            null
        );

        // Executar o fluxo.
        AssessmentFlowResult result = orchestrator.execute(input);

        // Verificar resultado.
        assertEquals("assessment-001", result.assessmentId());
        assertNotNull(result.suggestionResult());
        assertEquals("JP7-M", result.selectedVariantId());
        assertNotNull(result.variantPrediction());
        assertEquals("BODY_DENSITY", result.variantPrediction().outputType());

        // Verificar conversão.
        assertNotNull(result.conversionSuggestionResult());
        assertEquals("siri", result.selectedConversionId());
        assertNotNull(result.conversionPrediction());
        assertEquals("BODY_FAT_PERCENTAGE", result.conversionPrediction().outputType());

        // Verificar resultado final.
        assertNotNull(result.finalResult());
        assertEquals("BODY_FAT_PERCENTAGE", result.finalResult().outputType());
        assertTrue(result.finalResult().value() > 0);

        // Verificar auditoria (campos diretos do AuditSnapshot).
        AuditSnapshot audit = result.auditSnapshot();
        assertNotNull(audit);
        assertEquals("assessment-001", audit.assessmentId());
        assertNotNull(audit.timestamp());
        assertNotNull(audit.context());
        assertNotNull(audit.inputsUsed());

        // Campos de sugestão.
        assertNotNull(audit.candidateVariantIds());
        assertFalse(audit.candidateVariantIds().isEmpty());
        assertNotNull(audit.suggestionStatus());
        assertNotNull(audit.suggestedVariantId());
        assertEquals("JP7-M", audit.selectedVariantId());

        // Campos de cálculo.
        assertEquals("JP7-M", audit.equationVariantId());
        assertEquals("1", audit.equationVersion());
        assertEquals("BODY_DENSITY", audit.predictionOutputType());
        assertTrue(audit.predictionValue() > 0);

        // Campos de conversão.
        assertEquals("siri", audit.conversionId());
        assertEquals("1", audit.conversionVersion());
        assertEquals("BODY_FAT_PERCENTAGE", audit.conversionOutputType());
        assertNotNull(audit.conversionValue());
        assertTrue(audit.conversionValue() > 0);
        assertEquals("siri", audit.suggestedConversionId());
        assertEquals("siri", audit.selectedConversionId());

        // Versões.
        assertNotNull(audit.suggestionEngineVersion());
        assertNotNull(audit.conversionSuggestionEngineVersion());
        assertNotNull(audit.scientificRulesVersion());
        assertNotNull(audit.evidenceVersion());
        assertNotNull(audit.configurationVersion());
    }
}