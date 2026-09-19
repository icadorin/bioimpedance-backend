package com.bioimpedance.library.scientificrules;

import com.bioimpedance.library.measurements.InputTypeCatalog;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScientificRuleRegistryGoldenTest {

    private static final ScientificRuleRegistry REGISTRY = new ScientificRuleRegistry();
    private static final InputTypeCatalog CATALOG = new InputTypeCatalog();

    @Test
    void carregaAs43FichasCientificas() {
        assertEquals(43, REGISTRY.size());
    }

    @Test
    void inventarioPorFamiliaBateComDecisoesV1() {
        Map<String, Long> porFamilia = REGISTRY.all().stream()
            .collect(Collectors.groupingBy(p -> p.identity().familyId(), Collectors.counting()));
        assertEquals(4L, porFamilia.get("jackson-pollock"));
        assertEquals(7L, porFamilia.get("guedes"));       // G-F4..G-F8 fora da V1 (DEC-1)
        assertEquals(31L, porFamilia.get("petroski"));    // P-F15 sem ficha (DAD-1)
        assertEquals(1L, porFamilia.get("faulkner"));
    }

    @Test
    void variantesConhecidasPresentesEAusentesConfirmadas() {
        assertTrue(REGISTRY.find("FALK4").isPresent());
        assertTrue(REGISTRY.find("G-M3").isPresent());
        assertTrue(REGISTRY.find("JP7-F").isPresent());
        assertTrue(REGISTRY.find("P-M16").isPresent());
        assertTrue(REGISTRY.find("P-F16").isPresent());
        assertTrue(REGISTRY.find("P-F15").isEmpty());   // DAD-1
        assertTrue(REGISTRY.find("G-F4").isEmpty());    // DEC-1
    }

    @Test
    void todaFichaAtivaTemCamposEssenciais() {
        for (EquationVariantScientificProfile p : REGISTRY.all()) {
            String id = p.identity().variantId();
            assertNotNull(p.identity(), id);
            assertFalse(p.applicability().sex().supportedSexes().isEmpty(), id);
            assertNotNull(p.applicability().population().originalPopulation(), id);
            assertFalse(p.inputs().requiredInputs().isEmpty(), id);
            Assertions.assertEquals(LifecycleStatus.ACTIVE, p.lifecycle().status(), id);
        }
    }

    @Test
    void todoRequiredInputExisteNoCatalogoGlobal() {
        for (EquationVariantScientificProfile p : REGISTRY.all()) {
            for (String inputId : p.inputs().requiredInputs()) {
                assertTrue(CATALOG.isKnown(inputId),
                    "inputId não catalogado: " + inputId + " na variante " + p.identity().variantId());
            }
        }
    }
}