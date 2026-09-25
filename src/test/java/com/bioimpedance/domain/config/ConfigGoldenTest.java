package com.bioimpedance.domain.config;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConfigGoldenTest {

    // ===== SystemConversionPolicy (DEC-3 / §7.3) =====

    @Test
    void platformDefault_usaSiriComoDefaultOperacional() {
        SystemConversionPolicy policy = SystemConversionPolicy.platformDefault();
        assertEquals("siri", policy.defaultConversionId());
    }

    // ===== Habilitação (§7.1 / §7.2) =====

    @Test
    void varianteHabilitada_retornaTrue() {
        ProfessionalConfiguration config = new ProfessionalConfiguration(
            "prof-1", ConfigurationMode.CUSTOM,
            List.of("JP7-M", "G-M3"), List.of("siri", "brozek"), null);
        assertTrue(config.isVariantEnabled("JP7-M"));
        assertFalse(config.isVariantEnabled("P-M16"));
    }

    @Test
    void conversaoHabilitada_retornaTrue() {
        ProfessionalConfiguration config = new ProfessionalConfiguration(
            "prof-1", ConfigurationMode.CUSTOM,
            List.of("JP7-M"), List.of("brozek"), null);
        assertTrue(config.isConversionEnabled("brozek"));
        assertFalse(config.isConversionEnabled("siri"));
    }

    // ===== Preferência de conversão (§6.2) =====

    @Test
    void semPreferencia_preferredConversionIdNull() {
        ProfessionalConfiguration config = new ProfessionalConfiguration(
            "prof-1", ConfigurationMode.DEFAULT,
            List.of("JP7-M"), List.of("siri"), null);
        assertNull(config.preferredConversionId());
    }

    @Test
    void comPreferencia_retornaPreferred() {
        ProfessionalConfiguration config = new ProfessionalConfiguration(
            "prof-1", ConfigurationMode.CUSTOM,
            List.of("JP7-M"), List.of("siri", "brozek"), "brozek");
        assertEquals("brozek", config.preferredConversionId());
    }

    // ===== Restaurar padrão (§7.4) =====

    @Test
    void restaurarPadraoConversao_zeraPreferencia_semAlterarHabilitacoes() {
        ProfessionalConfiguration config = new ProfessionalConfiguration(
            "prof-1", ConfigurationMode.CUSTOM,
            List.of("JP7-M"), List.of("siri", "brozek"), "brozek");
        ProfessionalConfiguration restored = config.withConversionPreferenceCleared();
        assertNull(restored.preferredConversionId());
        assertEquals(List.of("JP7-M"), restored.enabledVariantIds());
        assertEquals(List.of("siri", "brozek"), restored.enabledConversionIds());
        assertEquals(ConfigurationMode.CUSTOM, restored.mode());
    }

    // ===== Imutabilidade =====

    @Test
    void listasSaoImutaveis() {
        ProfessionalConfiguration config = new ProfessionalConfiguration(
            "prof-1", ConfigurationMode.DEFAULT,
            List.of("JP7-M"), List.of("siri"), null);
        assertThrows(UnsupportedOperationException.class,
            () -> config.enabledVariantIds().add("X"));
        assertThrows(UnsupportedOperationException.class,
            () -> config.enabledConversionIds().add("X"));
    }
}