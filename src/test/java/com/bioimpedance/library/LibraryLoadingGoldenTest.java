package com.bioimpedance.library;

import com.bioimpedance.library.conversions.ConversionDefinition;
import com.bioimpedance.library.conversions.ConversionDefinitionRegistry;
import com.bioimpedance.library.measurements.InputTypeCatalog;
import com.bioimpedance.library.measurements.Unit;
import com.bioimpedance.library.scientificrules.EquationVariantScientificProfile;
import com.bioimpedance.library.scientificrules.LifecycleStatus;
import com.bioimpedance.library.scientificrules.ScientificRuleRegistry;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class LibraryLoadingGoldenTest {

    private static final ScientificRuleRegistry SCIENTIFIC = new ScientificRuleRegistry();
    private static final ConversionDefinitionRegistry CONVERSIONS = new ConversionDefinitionRegistry();
    private static final InputTypeCatalog CATALOG = new InputTypeCatalog();

    @Test
    void carrega43FichasCientificas() {
        assertEquals(43, SCIENTIFIC.size());
    }

    @Test
    void inventarioPorFamilia() {
        Map<String, Long> porFamilia = SCIENTIFIC.all().stream()
            .collect(Collectors.groupingBy(p -> p.identity().familyId(), Collectors.counting()));
        assertEquals(4L, porFamilia.get("jackson-pollock"));
        assertEquals(7L, porFamilia.get("guedes"));       // G-F4..G-F8 fora da V1 (DEC-1)
        assertEquals(31L, porFamilia.get("petroski"));    // P-F15 sem ficha (DAD-1)
        assertEquals(1L, porFamilia.get("faulkner"));
    }

    @Test
    void todaFichaAtivaTemCamposEssenciais() {
        for (EquationVariantScientificProfile p : SCIENTIFIC.all()) {
            String id = p.identity().variantId();
            assertFalse(p.applicability().sex().supportedSexes().isEmpty(), id);
            assertNotNull(p.applicability().population().originalPopulation(), id);
            assertFalse(p.inputs().requiredInputs().isEmpty(), id);
            assertEquals(LifecycleStatus.ACTIVE, p.lifecycle().status(), id);
        }
    }

    @Test
    void todoRequiredInputExisteNoCatalogo() {
        for (EquationVariantScientificProfile p : SCIENTIFIC.all()) {
            for (String inputId : p.inputs().requiredInputs()) {
                assertTrue(CATALOG.isKnown(inputId),
                    "inputId não catalogado: " + inputId + " em " + p.identity().variantId());
            }
        }
    }

    @Test
    void ausenciasConhecidas() {
        assertTrue(SCIENTIFIC.find("P-F15").isEmpty());   // DAD-1
        assertTrue(SCIENTIFIC.find("G-F4").isEmpty());    // DEC-1
    }

    @Test
    void conversoesV1() {
        assertEquals(2, CONVERSIONS.size());
        for (String id : new String[]{"siri", "brozek"}) {
            ConversionDefinition def = CONVERSIONS.resolve(id);
            assertEquals("BODY_DENSITY", def.inputType(), id);
            assertEquals("BODY_FAT_PERCENTAGE", def.outputType(), id);
            assertEquals(LifecycleStatus.ACTIVE, def.status(), id);
            assertFalse(def.expression().isBlank(), id);
        }
    }

    @Test
    void catalogoDeInputs() {
        assertEquals(15, CATALOG.size());
        assertEquals(Unit.YEARS, CATALOG.resolve("AGE").unit());
        assertEquals(Unit.KG, CATALOG.resolve("BODY_MASS").unit());
        assertEquals(Unit.CM, CATALOG.resolve("HEIGHT").unit());
        assertEquals(Unit.MM, CATALOG.resolve("SKINFOLD_TRICEPS").unit());
    }
}