package com.bioimpedance.service;

import com.bioimpedance.constants.ActivityLevel;
import com.bioimpedance.constants.Gender;
import com.bioimpedance.dto.response.CalculationFlowResponseDTO;
import com.bioimpedance.dto.response.PredictionDTO;
import com.bioimpedance.dto.response.RecommendationDTO;
import com.bioimpedance.entity.AssessmentResult;
import com.bioimpedance.entity.Client;
import com.bioimpedance.util.MetabolicCalculator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Aplica métricas derivadas DEPOIS do resultado final do orchestrator (DEC-35).
 * <p>
 * Responsabilidades:
 * - IMC, BMR, TDEE, FFMI (via MetabolicCalculator legado — sobrevivem)
 * - Classificação de %G (tabela declarativa de thresholds, DEC-44)
 * - Recomendação de dieta/treino (via RecommendationService — sobrevive)
 * <p>
 * Não implementa regra científica das 43 variantes — só métricas de produto.
 */
@Service
public class MetabolicService {

    // ── Tabela declarativa de classificação de %G (DEC-44) ──────────────
    // Substitui o if/else legado (BodyFatInterpreter). Limites baseados em
    // ACE (American Council on Exercise); ajustar aqui se a referência mudar.
    private record BodyFatThreshold(double max, String label) {}

    private static final List<BodyFatThreshold> MALE_THRESHOLDS = List.of(
        new BodyFatThreshold(6,  "Gordura essencial"),
        new BodyFatThreshold(14, "Atleta"),
        new BodyFatThreshold(18, "Saudável"),
        new BodyFatThreshold(25, "Acima da média"),
        new BodyFatThreshold(Double.MAX_VALUE, "Obesidade")
    );

    private static final List<BodyFatThreshold> FEMALE_THRESHOLDS = List.of(
        new BodyFatThreshold(14, "Gordura essencial"),
        new BodyFatThreshold(21, "Atleta"),
        new BodyFatThreshold(25, "Saudável"),
        new BodyFatThreshold(32, "Acima da média"),
        new BodyFatThreshold(Double.MAX_VALUE, "Obesidade")
    );

    private final MetabolicCalculator metabolicCalculator;
    private final RecommendationService recommendationService;

    public MetabolicService(MetabolicCalculator metabolicCalculator,
                            RecommendationService recommendationService) {
        this.metabolicCalculator = metabolicCalculator;
        this.recommendationService = recommendationService;
    }

    /**
     * Enriquece o AssessmentResult com métricas derivadas.
     * <p>
     * @param client        cliente (para height, gender)
     * @param age           idade na data da avaliação
     * @param weight        peso em kg (medida BODY_MASS)
     * @param activityLevel nível de atividade (para TDEE)
     * @param objective     objetivo nutricional ("cutting", "bulking", "maintenance")
     * @param flowResponse  resultado do orchestrator (para extrair finalValue/outputType)
     * @param auditId       id do AuditSnapshot
     */
    public AssessmentResult buildResult(
        Client client,
        int age,
        Double weight,
        String activityLevel,
        String objective,
        CalculationFlowResponseDTO flowResponse,
        String auditId
    ) {
        String gender = client.getGender() == Gender.FEMALE ? "FEMALE" : "MALE";
        Double height = client.getHeight();

        Double imc      = (weight != null && height != null && height > 0)
            ? metabolicCalculator.calculateIMC(weight, height) : null;
        Double bmr      = (weight != null && height != null && age > 0)
            ? metabolicCalculator.calculateBMR(weight, height, age, gender) : null;
        ActivityLevel level = parseActivityLevel(activityLevel);
        Double tdee     = (bmr != null) ? metabolicCalculator.calculateTDEE(bmr, level) : null;

        Double bodyFat  = extractBodyFat(flowResponse);
        Double leanMass = (weight != null && bodyFat != null)
            ? metabolicCalculator.calculateLeanMass(weight, bodyFat) : null;
        Double fatMass  = (weight != null && bodyFat != null)
            ? metabolicCalculator.calculateFatMass(weight, bodyFat) : null;
        Double ffmi     = (leanMass != null && height != null && height > 0)
            ? metabolicCalculator.calculateFFMI(leanMass, height) : null;
        String bodyFatLevel = interpretBodyFat(gender, bodyFat);
        Double targetCalories = resolveTargetCalories(tdee, bodyFat, weight, objective, gender);

        return AssessmentResult.builder()
            // Científico
            .variantId(flowResponse.getSelectedVariantId())
            .conversionId(flowResponse.getSelectedConversionId())
            .outputType(extractOutputType(flowResponse))
            .finalValue(extractFinalValue(flowResponse))
            .auditId(auditId)
            // Derivado
            .imc(round2(imc))
            .bodyFat(round2(bodyFat))
            .leanMass(round2(leanMass))
            .fatMass(round2(fatMass))
            .ffmi(round2(ffmi))
            .bmr(round2(bmr))
            .tdee(round2(tdee))
            .targetCalories(targetCalories)
            .bodyFatLevel(bodyFatLevel)
            .build();
    }

    // ==================== HELPERS ====================

    /**
     * Ponto único de extração do resultado final do orchestrator.
     * Todos os extract* derivam dele — evita repetir o null check.
     * O orchestrator já garante outputType = BODY_FAT_PERCENTAGE quando aplicável
     * (aplicando Siri/Brozek internamente), então o valor aqui é sempre o final.
     */
    private PredictionDTO finalResultOf(CalculationFlowResponseDTO response) {
        return response != null ? response.getFinalResult() : null;
    }

    private Double extractBodyFat(CalculationFlowResponseDTO response) {
        PredictionDTO result = finalResultOf(response);
        return result != null ? result.getValue() : null;
    }

    private String extractOutputType(CalculationFlowResponseDTO response) {
        PredictionDTO result = finalResultOf(response);
        return result != null ? result.getOutputType() : null;
    }

    private Double extractFinalValue(CalculationFlowResponseDTO response) {
        PredictionDTO result = finalResultOf(response);
        return result != null ? result.getValue() : null;
    }

    private ActivityLevel parseActivityLevel(String value) {
        return Optional.ofNullable(value)
            .filter(v -> !v.isBlank())
            .map(v -> {
                try {
                    return ActivityLevel.valueOf(v.toUpperCase());
                } catch (IllegalArgumentException e) {
                    return ActivityLevel.MODERATE;
                }
            })
            .orElse(ActivityLevel.MODERATE);
    }

    /** Classificação por tabela declarativa (DEC-44): novo limite = 1 linha. */
    private String interpretBodyFat(String gender, Double bodyFat) {
        if (bodyFat == null) return null;
        List<BodyFatThreshold> thresholds = "MALE".equals(gender)
            ? MALE_THRESHOLDS
            : FEMALE_THRESHOLDS;
        return thresholds.stream()
            .filter(t -> bodyFat < t.max())
            .findFirst()
            .map(BodyFatThreshold::label)
            .orElse("Obesidade");
    }

    private Double resolveTargetCalories(Double tdee, Double bodyFat, Double weight,
                                         String objective, String gender) {
        if (tdee == null || bodyFat == null || weight == null) return null;
        String obj = (objective != null && !objective.isBlank()) ? objective : "maintenance";
        RecommendationDTO rec = recommendationService.generateRecommendation(
            tdee, obj, bodyFat, weight, gender);
        return rec != null ? (double) rec.getTargetCalories() : null;
    }

    private Double round2(Double value) {
        return value == null ? null : Math.round(value * 100.0) / 100.0;
    }
}