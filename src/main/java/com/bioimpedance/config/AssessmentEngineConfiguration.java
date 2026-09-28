package com.bioimpedance.config;

import com.bioimpedance.domain.applicability.ApplicabilityEngine;
import com.bioimpedance.domain.applicability.EvidenceSummaryBuilder;
import com.bioimpedance.domain.calculation.EquationEvaluator;
import com.bioimpedance.domain.config.SystemConversionPolicy;
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
import com.bioimpedance.orchestration.assessmentflow.AssessmentFlowOrchestrator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Wiring Spring do motor científico (Fase 12 / Chunk 5 — DEC-43/DEC-46).
 * <p>
 * As classes de domain/, library/ e orchestration/ permanecem framework-agnostic
 * (sem @Component): todas são instanciadas aqui, num único ponto. A ordem de
 * composição espelha o AssessmentFlowOrchestrationGoldenTest.
 * <p>
 * Sem este @Configuration o contexto Spring não sobe: AssessmentFlowService
 * injeta AssessmentFlowOrchestrator por construtor e não existiria bean candidato.
 */
@Configuration
public class AssessmentEngineConfiguration {

    // ── library (carregam os YAMLs do classpath) ──

    @Bean
    public InputTypeCatalog inputTypeCatalog() {
        return new InputTypeCatalog();
    }

    @Bean
    public EquationVariantRegistry equationVariantRegistry() {
        return new EquationVariantRegistry();
    }

    @Bean
    public ScientificRuleRegistry scientificRuleRegistry() {
        return new ScientificRuleRegistry();
    }

    @Bean
    public ConversionDefinitionRegistry conversionDefinitionRegistry() {
        return new ConversionDefinitionRegistry();
    }

    // ── domain: engines puros ──

    @Bean
    public MeasurementValidator measurementValidator(InputTypeCatalog inputTypeCatalog) {
        return new MeasurementValidator(inputTypeCatalog);
    }

    @Bean
    public EvidenceSummaryBuilder evidenceSummaryBuilder() {
        return new EvidenceSummaryBuilder();
    }

    @Bean
    public ApplicabilityEngine applicabilityEngine(EvidenceSummaryBuilder evidenceSummaryBuilder) {
        return new ApplicabilityEngine(evidenceSummaryBuilder);
    }

    @Bean
    public EligibilityResolver eligibilityResolver() {
        return new EligibilityResolver();
    }

    @Bean
    public CompatibilityRanker compatibilityRanker() {
        return new CompatibilityRanker();
    }

    @Bean
    public SuggestionExplanationBuilder suggestionExplanationBuilder() {
        return new SuggestionExplanationBuilder();
    }

    @Bean
    public SuggestionEngine suggestionEngine(CompatibilityRanker compatibilityRanker,
                                             SuggestionExplanationBuilder suggestionExplanationBuilder) {
        return new SuggestionEngine(compatibilityRanker, suggestionExplanationBuilder);
    }

    @Bean
    public ConversionSuggestionExplanationBuilder conversionSuggestionExplanationBuilder() {
        return new ConversionSuggestionExplanationBuilder();
    }

    @Bean
    public ConversionSuggestionEngine conversionSuggestionEngine(
        ConversionSuggestionExplanationBuilder conversionSuggestionExplanationBuilder) {
        return new ConversionSuggestionEngine(conversionSuggestionExplanationBuilder);
    }

    @Bean
    public EquationEvaluator equationEvaluator() {
        return new EquationEvaluator();
    }

    @Bean
    public DensityToFatConverter densityToFatConverter() {
        return new DensityToFatConverter();
    }

    @Bean
    public SystemConversionPolicy systemConversionPolicy() {
        return SystemConversionPolicy.platformDefault();
    }

    // ── orchestration: o maestro ──

    @Bean
    public AssessmentFlowOrchestrator assessmentFlowOrchestrator(
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
        return new AssessmentFlowOrchestrator(
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
            systemConversionPolicy);
    }
}