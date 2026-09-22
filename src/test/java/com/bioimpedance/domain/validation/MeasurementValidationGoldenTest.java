package com.bioimpedance.domain.validation;

import com.bioimpedance.domain.contracts.ValidationIssueType;
import com.bioimpedance.domain.contracts.ValidationResult;
import com.bioimpedance.library.measurements.InputTypeCatalog;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MeasurementValidationGoldenTest {

    private static final MeasurementValidator VALIDATOR =
        new MeasurementValidator(new InputTypeCatalog());

    @Test
    void mapaValidoPassaSemIssues() {
        ValidationResult r = VALIDATOR.validate(Map.of(
            "AGE", 30.0,
            "BODY_MASS", 80.5,
            "HEIGHT", 178.0,
            "SKINFOLD_TRICEPS", 12.5,
            "SKINFOLD_SUBSCAPULAR", 15.0,
            "SKINFOLD_SUPRAILIAC", 10.0,
            "SKINFOLD_ABDOMEN", 18.5));
        assertTrue(r.valid());
        assertTrue(r.issues().isEmpty());
    }

    @Test
    void valorForaDaFaixaEhImpossivel() {
        ValidationResult r = VALIDATOR.validate(Map.of("SKINFOLD_TRICEPS", 250.0));
        assertFalse(r.valid());
        assertEquals(ValidationIssueType.IMPOSSIBLE_VALUE, r.issues().getFirst().type());
        assertEquals("SKINFOLD_TRICEPS", r.issues().getFirst().inputId());
    }

    @Test
    void valorNegativoEhImpossivel() {
        ValidationResult r = VALIDATOR.validate(Map.of("AGE", -5.0));
        assertFalse(r.valid());
        assertEquals(ValidationIssueType.IMPOSSIBLE_VALUE, r.issues().getFirst().type());
    }

    @Test
    void idadeComDecimalViolaPrecisao() {
        ValidationResult r = VALIDATOR.validate(Map.of("AGE", 30.5));
        assertFalse(r.valid());
        assertEquals(ValidationIssueType.PRECISION_MISMATCH, r.issues().getFirst().type());
    }

    @Test
    void inputNaoCatalogadoEhInvalido() {
        ValidationResult r = VALIDATOR.validate(Map.of("SKINFOLD_CHEST", 10.0));
        assertFalse(r.valid());
        assertEquals(ValidationIssueType.INVALID_VALUE, r.issues().getFirst().type());
    }

    @Test
    void inputObrigatorioAusenteGeraMissing() {
        ValidationResult r = VALIDATOR.validate(
            Map.of("SKINFOLD_TRICEPS", 12.0),
            List.of("SKINFOLD_TRICEPS", "SKINFOLD_SUBSCAPULAR"));
        assertFalse(r.valid());
        assertEquals(1, r.issues().size());
        assertEquals(ValidationIssueType.MISSING_REQUIRED_INPUT, r.issues().getFirst().type());
        assertEquals("SKINFOLD_SUBSCAPULAR", r.issues().getFirst().inputId());
    }

    @Test
    void todosOs15InputsDoCatalogoAceitamValoresDeExemplo() {
        ValidationResult r = VALIDATOR.validate(Map.ofEntries(
            Map.entry("AGE", 25.0),
            Map.entry("BODY_MASS", 75.0),
            Map.entry("HEIGHT", 175.0),
            Map.entry("SKINFOLD_SUBSCAPULAR", 14.0),
            Map.entry("SKINFOLD_TRICEPS", 11.0),
            Map.entry("SKINFOLD_BICEPS", 7.0),
            Map.entry("SKINFOLD_PECTORAL", 9.0),
            Map.entry("SKINFOLD_AXILLARY_MID", 8.0),
            Map.entry("SKINFOLD_SUPRAILIAC", 10.0),
            Map.entry("SKINFOLD_ABDOMEN", 16.0),
            Map.entry("SKINFOLD_THIGH", 20.0),
            Map.entry("SKINFOLD_MEDIAL_CALF", 13.0),
            Map.entry("CIRCUMFERENCE_FOREARM", 27.0),
            Map.entry("CIRCUMFERENCE_ABDOMEN", 82.0),
            Map.entry("CIRCUMFERENCE_THIGH", 55.0)));
        assertTrue(r.valid(), () -> r.issues().toString());
    }
}