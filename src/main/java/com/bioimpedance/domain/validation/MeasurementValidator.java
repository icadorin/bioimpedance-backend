package com.bioimpedance.domain.validation;

import com.bioimpedance.domain.contracts.ValidationIssue;
import com.bioimpedance.domain.contracts.ValidationIssueType;
import com.bioimpedance.domain.contracts.ValidationResult;
import com.bioimpedance.library.measurements.InputTypeCatalog;
import com.bioimpedance.library.measurements.InputTypeDefinition;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Fonte: architecture.md §2, §2.1–2.3.
 * Validação de DADOS contra o InputTypeCatalog — não sabe nada de
 * aplicabilidade científica nem de prontidão.
 * Dependência domain → library é permitida (mesma direção do EquationEvaluator).
 */
@Service
public class MeasurementValidator {

    private final InputTypeCatalog catalog;

    public MeasurementValidator(InputTypeCatalog catalog) {
        this.catalog = catalog;
    }

    /** Valida apenas os dados presentes. */
    public ValidationResult validate(Map<String, Double> measurements) {
        return validate(measurements, List.of());
    }

    /** Valida dados presentes + obrigatoriedade dos requiredInputIds. */
    public ValidationResult validate(Map<String, Double> measurements,
                                     Collection<String> requiredInputIds) {
        List<ValidationIssue> issues = new ArrayList<>();

        for (Map.Entry<String, Double> entry : measurements.entrySet()) {
            String inputId = entry.getKey();
            Double value = entry.getValue();

            if (!catalog.isKnown(inputId)) {
                issues.add(issue(ValidationIssueType.INVALID_VALUE, inputId,
                    "Input não catalogado: " + inputId));
                continue;
            }
            InputTypeDefinition def = catalog.resolve(inputId);

            if (value == null || value.isNaN() || value.isInfinite()) {
                issues.add(issue(ValidationIssueType.INVALID_VALUE, inputId,
                    "Valor ausente ou não numérico"));
                continue;
            }
            if (value <= 0) {
                issues.add(issue(ValidationIssueType.IMPOSSIBLE_VALUE, inputId,
                    "Valor deve ser positivo"));
                continue;
            }
            var range = def.plausibleRange();
            if (range != null && (value < range.min() || value > range.max())) {
                issues.add(issue(ValidationIssueType.IMPOSSIBLE_VALUE, inputId,
                    String.format("Fora da faixa plausível [%s–%s %s]",
                        range.min(), range.max(), def.unit())));
            }
            Integer precision = def.precision();
            if (precision != null && decimalPlaces(value) > precision) {
                issues.add(issue(ValidationIssueType.PRECISION_MISMATCH, inputId,
                    "Excede a precisão de " + precision + " casa(s) decimal(is)"));
            }
        }

        for (String requiredId : requiredInputIds) {
            if (!measurements.containsKey(requiredId)) {
                issues.add(issue(ValidationIssueType.MISSING_REQUIRED_INPUT, requiredId,
                    "Input obrigatório ausente"));
            }
        }

        return new ValidationResult(issues.isEmpty(), issues);
    }

    private static ValidationIssue issue(ValidationIssueType type, String inputId, String message) {
        return new ValidationIssue(type, inputId, message);
    }

    private static int decimalPlaces(double value) {
        return BigDecimal.valueOf(value).stripTrailingZeros().scale();
    }
}