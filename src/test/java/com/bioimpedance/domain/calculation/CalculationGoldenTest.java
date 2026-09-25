package com.bioimpedance.domain.calculation;

import com.bioimpedance.domain.conversion.DensityToFatConverter;
import com.bioimpedance.library.conversions.ConversionDefinitionRegistry;
import com.bioimpedance.library.equations.EquationVariantRegistry;
import com.bioimpedance.library.equations.FormulaDefinition;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculationGoldenTest {

    private static final EquationVariantRegistry EQUATIONS = new EquationVariantRegistry();
    private static final ConversionDefinitionRegistry CONVERSIONS = new ConversionDefinitionRegistry();
    private static final EquationEvaluator EVALUATOR = new EquationEvaluator();
    private static final DensityToFatConverter CONVERTER = new DensityToFatConverter();

    @Test
    void falk4CalculatesPercentageDirectly() {
        FormulaDefinition falk4 = EQUATIONS.resolve("FALK4");
        Map<String, Double> inputs = Map.of(
            "SKINFOLD_TRICEPS", 15.0,
            "SKINFOLD_SUBSCAPULAR", 20.0,
            "SKINFOLD_SUPRAILIAC", 25.0,
            "SKINFOLD_ABDOMEN", 30.0
        ); // Soma = 90

        PredictionResult result = EVALUATOR.evaluate(falk4, inputs);

        // 5.783 + 0.153 * 90 = 5.783 + 13.77 = 19.553
        assertEquals("BODY_FAT_PERCENTAGE", result.outputType());
        assertEquals(19.553, result.value(), 0.001);
    }

    @Test
    void pM16CalculatesDensityAndConvertsWithSiri() {
        FormulaDefinition pm16 = EQUATIONS.resolve("P-M16");
        Map<String, Double> inputs = Map.of(
            "SKINFOLD_TRICEPS", 15.0,
            "SKINFOLD_ABDOMEN", 25.0, // Soma S = 40
            "AGE", 30.0,
            "CIRCUMFERENCE_FOREARM", 28.0,
            "CIRCUMFERENCE_ABDOMEN", 85.0
        );

        PredictionResult densityResult = EVALUATOR.evaluate(pm16, inputs);
        assertEquals("BODY_DENSITY", densityResult.outputType());

        // D = 1.08843264 - 0.00130623*(40) + 0.00000710*(1600) - 0.00021414*(30) + 0.00182587*(28) - 0.00052569*(85)
        // D = 1.08843264 - 0.0522492 + 0.01136 - 0.0064242 + 0.05112436 - 0.04468365 = 1.04755995
        assertEquals(1.04755995, densityResult.value(), 0.0000001);

        // Conversão Siri: (495 / 1.04755995) - 450 = 472.526 - 450 = 22.526%
        PredictionResult fatResult = CONVERTER.convert(CONVERSIONS.resolve("siri"), densityResult.value());
        assertEquals("BODY_FAT_PERCENTAGE", fatResult.outputType());
        assertEquals(22.526, fatResult.value(), 0.001);
    }
}