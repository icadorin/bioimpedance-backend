package com.bioimpedance.library.measurements;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void inputIdDesconhecidoFalha() {
        assertThrows(IllegalArgumentException.class, () -> CATALOG.resolve("SKINFOLD_CHEST"));
    }
}