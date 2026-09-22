package com.bioimpedance.library.equations;

import com.bioimpedance.library.scientificrules.EquationVariantScientificProfile;
import com.bioimpedance.library.scientificrules.ScientificRuleRegistry;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Fase 3 — golden test final: prova que os 43 YAMLs matemáticos batem
 * 1-para-1 com os 43 perfis científicos. Regra #1: a ciência manda.
 */
class EquationLibraryGoldenTest {

    private static final EquationVariantRegistry EQUATIONS = new EquationVariantRegistry();
    private static final ScientificRuleRegistry SCIENTIFIC = new ScientificRuleRegistry();

    @Test
    void carregaAs43FormulasMatematicas() {
        assertEquals(43, EQUATIONS.size());
    }

    @Test
    void inventarioMatematicoBateComInventarioCientifico() {
        Set<String> mathIds = EQUATIONS.all().stream()
            .map(FormulaDefinition::variantId)
            .collect(Collectors.toSet());
        Set<String> sciIds = SCIENTIFIC.all().stream()
            .map(p -> p.identity().variantId())
            .collect(Collectors.toSet());
        assertEquals(sciIds, mathIds, "Inventário equations/ ≠ inventário scientific-rules/");
    }

    @Test
    void inputsMatematicosCobremExatamenteOsInputsCientificos() {
        for (FormulaDefinition formula : EQUATIONS.all()) {
            EquationVariantScientificProfile profile = SCIENTIFIC.resolve(formula.variantId());
            Set<String> required = new HashSet<>(profile.inputs().requiredInputs());

            // Inputs realmente consumidos pela fórmula
            Set<String> used = new HashSet<>(formula.sumInputs());
            used.addAll(formula.namedInputs().values());

            boolean needsAge = switch (formula.template()) {
                case QUADRATIC_SUM_WITH_AGE,
                     QUADRATIC_SUM_WITH_AGE_AND_CIRC,
                     QUADRATIC_SUM_WITH_AGE_MASS_HEIGHT,
                     LOG10_SUM_WITH_AGE,
                     LOG10_SUM_WITH_AGE_AND_CIRC -> true;
                default -> false;
            };
            if (needsAge) {
                used.add("AGE");
            }
            if (formula.template() == FormulaTemplate.QUADRATIC_SUM_WITH_AGE_MASS_HEIGHT) {
                used.add("BODY_MASS");
                used.add("HEIGHT");
            }

            assertEquals(required, used,
                "Divergência entre requiredInputs e inputs da fórmula em " + formula.variantId());
        }
    }

    @Test
    void outputTypesCorretos() {
        // Todas as variantes V1 saem em BODY_DENSITY, exceto FALK4 (direto em %G — DEC-2)
        for (FormulaDefinition formula : EQUATIONS.all()) {
            assertTrue("BODY_DENSITY".equals(formula.outputType())
                    || "BODY_FAT_PERCENTAGE".equals(formula.outputType()),
                "outputType inválido em " + formula.variantId());
        }
        assertEquals("BODY_FAT_PERCENTAGE", EQUATIONS.resolve("FALK4").outputType());
        long densityCount = EQUATIONS.all().stream()
            .filter(f -> "BODY_DENSITY".equals(f.outputType()))
            .count();
        assertEquals(42, densityCount);
    }
}