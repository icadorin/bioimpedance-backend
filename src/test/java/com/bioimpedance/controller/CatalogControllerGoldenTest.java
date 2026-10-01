package com.bioimpedance.controller;

import com.bioimpedance.dto.response.ConversionCatalogDTO;
import com.bioimpedance.dto.response.InputTypeCatalogDTO;
import com.bioimpedance.dto.response.VariantCatalogDTO;
import com.bioimpedance.library.conversions.ConversionDefinitionRegistry;
import com.bioimpedance.library.equations.EquationVariantRegistry;
import com.bioimpedance.library.measurements.InputTypeCatalog;
import com.bioimpedance.library.scientificrules.ScientificRuleRegistry;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Golden test da projeção dos catálogos (Fase 13 / B2 — DEC-59).
 * Instancia o controller direto (framework-agnostic), espelhando o
 * padrão dos golden tests de library/orchestration.
 *
 * Nota: os DTOs são classes Lombok (@Getter), não records — accessors
 * seguem o padrão getX().
 */
class CatalogControllerGoldenTest {

    private static final CatalogController CONTROLLER = new CatalogController(
        new InputTypeCatalog(),
        new ScientificRuleRegistry(),
        new EquationVariantRegistry(),
        new ConversionDefinitionRegistry());

    @Test
    void inputsExpoem15EntradasComGrupoEOrdemDoCatalogo() {
        List<InputTypeCatalogDTO> inputs = CONTROLLER.inputs().getBody();
        assertNotNull(inputs);
        assertEquals(15, inputs.size());
        // Ordem do input-types.yaml: AGE primeiro (ordem canônica de exibição)
        assertEquals("AGE", inputs.getFirst().getInputId());
        for (InputTypeCatalogDTO input : inputs) {
            assertNotNull(input.getGroup(), input.getInputId());
            assertNotNull(input.getUnit(), input.getInputId());
        }
    }

    @Test
    void variantesExpoem43EntradasComOutputTypeSexosEInputs() {
        List<VariantCatalogDTO> variants = CONTROLLER.variants().getBody();
        assertNotNull(variants);
        assertEquals(43, variants.size());
        for (VariantCatalogDTO variant : variants) {
            assertNotNull(variant.getOutputType(), variant.getVariantId());
            assertFalse(variant.getSupportedSexes().isEmpty(), variant.getVariantId());
            assertFalse(variant.getRequiredInputs().isEmpty(), variant.getVariantId());
        }
        // FALK4: sai direto em %G e é somente MALE (DEC-2)
        VariantCatalogDTO falk4 = variants.stream()
            .filter(v -> v.getVariantId().equals("FALK4"))
            .findFirst()
            .orElseThrow();
        assertEquals("BODY_FAT_PERCENTAGE", falk4.getOutputType());
        assertEquals(List.of("MALE"), falk4.getSupportedSexes());
    }

    @Test
    void conversoesExpoemSiriEBrozekEmOrdemDeterministica() {
        List<ConversionCatalogDTO> conversions = CONTROLLER.conversions().getBody();
        assertNotNull(conversions);
        assertEquals(2, conversions.size());
        assertEquals("brozek", conversions.get(0).getConversionId());
        assertEquals("siri", conversions.get(1).getConversionId());
        assertEquals("BODY_DENSITY", conversions.get(1).getInputType());
        assertEquals("BODY_FAT_PERCENTAGE", conversions.get(1).getOutputType());
    }
}