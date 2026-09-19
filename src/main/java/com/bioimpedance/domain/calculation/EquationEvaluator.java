package com.bioimpedance.domain.calculation;

import com.bioimpedance.library.equations.FormulaDefinition;
import com.bioimpedance.library.equations.FormulaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Fonte: architecture.md §13.
 * O motor matemático puro. Não sabe quem é o cliente, só resolve a fórmula.
 */
@Service
public class EquationEvaluator {

    public PredictionResult evaluate(FormulaDefinition formula, Map<String, Double> inputs) {
        // 1. Somatório (S) — loop direto, sem alocação de lambda/Stream por chamada
        double S = 0.0;
        for (String id : formula.sumInputs()) {
            S += getRequired(inputs, id, "Input faltando para soma: ");
        }

        Map<String, Double> c = formula.coefficients();

        // 2. Switch Expression exaustivo — sem default: o compilador obriga a cobrir
        //    todo template e quebra o build se um novo for adicionado sem tratamento.
        double result = switch (formula.template()) {
            case LINEAR_SUM ->
                coeff(c, "c0") + coeff(c, "c1") * S;

            case LOG10_SUM ->
                coeff(c, "c0") + coeff(c, "c1") * Math.log10(S);

            case QUADRATIC_SUM_WITH_AGE ->
                coeff(c, "c0") + coeff(c, "c1") * S
                    + coeff(c, "c2") * (S * S)
                    + coeff(c, "c3") * getRequired(inputs, "AGE", "Idade ausente: ");

            case QUADRATIC_SUM_WITH_AGE_AND_CIRC -> {
                String circ1 = requireNamedInput(formula, "circ1");
                String circ2 = requireNamedInput(formula, "circ2");

                yield coeff(c, "c0") + coeff(c, "c1") * S
                    + coeff(c, "c2") * (S * S)
                    + coeff(c, "c3") * getRequired(inputs, "AGE", "Idade ausente: ")
                    + coeff(c, "c4") * getRequired(inputs, circ1, "Circunferência ausente: ")
                    + coeff(c, "c5") * getRequired(inputs, circ2, "Circunferência ausente: ");
            }

            case QUADRATIC_SUM_WITH_AGE_MASS_HEIGHT ->
                coeff(c, "c0") + coeff(c, "c1") * S
                    + coeff(c, "c2") * (S * S)
                    + coeff(c, "c3") * getRequired(inputs, "AGE", "Idade ausente: ")
                    + coeff(c, "c4") * getRequired(inputs, "BODY_MASS", "Massa ausente: ")
                    + coeff(c, "c5") * getRequired(inputs, "HEIGHT", "Estatura ausente: ");

            case LOG10_SUM_WITH_AGE ->
                coeff(c, "c0") + coeff(c, "c1") * Math.log10(S)
                    + coeff(c, "c2") * getRequired(inputs, "AGE", "Idade ausente: ");

            case LOG10_SUM_WITH_AGE_AND_CIRC -> {
                String circ = requireNamedInput(formula, "circ1");

                yield coeff(c, "c0") + coeff(c, "c1") * Math.log10(S)
                    + coeff(c, "c2") * getRequired(inputs, "AGE", "Idade ausente: ")
                    + coeff(c, "c3") * getRequired(inputs, circ, "Circunferência ausente: ");
            }
        };

        return new PredictionResult(formula.variantId(), formula.outputType(), result);
    }

    // --- Helpers de extração segura e fail-fast ---

    private double coeff(Map<String, Double> coefficients, String key) {
        Double val = coefficients.get(key);
        if (val == null) {
            throw new IllegalArgumentException("Coeficiente ausente na fórmula: " + key);
        }
        return val;
    }

    private double getRequired(Map<String, Double> inputs, String key, String errorPrefix) {
        Double val = inputs.get(key);
        if (val == null) {
            throw new IllegalArgumentException(errorPrefix + key);
        }
        return val;
    }

    private String requireNamedInput(FormulaDefinition formula, String role) {
        String inputId = formula.namedInputs().get(role);
        if (inputId == null) {
            throw new IllegalStateException(
                "Fórmula " + formula.variantId() + " não define input nomeado para papel: " + role);
        }
        return inputId;
    }
}