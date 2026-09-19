package com.bioimpedance.library.conversions;

import com.bioimpedance.library.scientificrules.LifecycleStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ConversionDefinitionRegistryGoldenTest {

    private static final ConversionDefinitionRegistry REGISTRY = new ConversionDefinitionRegistry();

    @Test
    void carregaAsDuasConversoesDaV1() {
        assertEquals(2, REGISTRY.size());
    }

    @Test
    void siriEBrozekTemTiposDeEntradaESaidaCorretos() {
        for (String id : new String[]{"siri", "brozek"}) {
            ConversionDefinition def = REGISTRY.resolve(id);
            assertEquals("BODY_DENSITY", def.inputType(), id);
            assertEquals("BODY_FAT_PERCENTAGE", def.outputType(), id);
            assertEquals(LifecycleStatus.ACTIVE, def.status(), id);
            assertFalse(def.expression().isBlank(), id);
            assertFalse(def.computationalForm().isBlank(), id);
        }
    }
}