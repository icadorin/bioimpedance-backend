package com.bioimpedance.library.measurements;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InputTypeCatalogGoldenTest {

    private static final InputTypeCatalog CATALOG = new InputTypeCatalog();

    @Test
    void carregaOs15TiposDeInput() {
        assertEquals(15, CATALOG.size());
    }

    @Test
    void unidadesETiposCorretos() {
        assertEquals(Unit.YEARS, CATALOG.resolve("AGE").unit());
        assertEquals(ValueType.INTEGER, CATALOG.resolve("AGE").valueType());
        assertEquals(Unit.KG, CATALOG.resolve("BODY_MASS").unit());
        assertEquals(Unit.CM, CATALOG.resolve("HEIGHT").unit());
        assertEquals(Unit.MM, CATALOG.resolve("SKINFOLD_TRICEPS").unit());
        assertEquals(Unit.CM, CATALOG.resolve("CIRCUMFERENCE_ABDOMEN").unit());
    }

    @Test
    void gruposCorretosPorInput() {
        // DEC-59: o front agrupa por group, nunca deduz pelo prefixo do inputId
        assertEquals(InputGroup.BASIC, CATALOG.resolve("AGE").group());
        assertEquals(InputGroup.BASIC, CATALOG.resolve("BODY_MASS").group());
        assertEquals(InputGroup.BASIC, CATALOG.resolve("HEIGHT").group());
        assertEquals(InputGroup.SKINFOLD, CATALOG.resolve("SKINFOLD_TRICEPS").group());
        assertEquals(InputGroup.SKINFOLD, CATALOG.resolve("SKINFOLD_MEDIAL_CALF").group());
        assertEquals(InputGroup.CIRCUMFERENCE, CATALOG.resolve("CIRCUMFERENCE_ABDOMEN").group());
        assertEquals(InputGroup.CIRCUMFERENCE, CATALOG.resolve("CIRCUMFERENCE_THIGH").group());
    }

    @Test
    void todosOsInputsPossuemGrupo() {
        for (InputTypeDefinition def : CATALOG.all()) {
            assertNotNull(def.group(), def.inputId() + " deve ter group (DEC-59)");
        }
    }

    @Test
    void inputIdDesconhecidoFalha() {
        assertThrows(IllegalArgumentException.class, () -> CATALOG.resolve("SKINFOLD_CHEST"));
    }
}