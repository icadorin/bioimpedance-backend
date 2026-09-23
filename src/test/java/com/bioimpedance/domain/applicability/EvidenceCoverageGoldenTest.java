package com.bioimpedance.domain.applicability;

import com.bioimpedance.domain.contracts.EvidenceSummary;
import com.bioimpedance.library.scientificrules.EquationVariantScientificProfile;
import com.bioimpedance.library.scientificrules.ScientificRuleRegistry;
import org.junit.jupiter.api.Test;

import static com.bioimpedance.domain.contracts.DocumentationStatus.DOCUMENTED;
import static com.bioimpedance.domain.contracts.DocumentationStatus.NOT_DOCUMENTED;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EvidenceCoverageGoldenTest {

    private static final ScientificRuleRegistry REGISTRY = new ScientificRuleRegistry();
    private static final EvidenceSummaryBuilder BUILDER = new EvidenceSummaryBuilder();

    // ===== Invariantes universais REAIS =====

    @Test
    void sexoDocumentadoEmTodasAs43Variantes() {
        for (EquationVariantScientificProfile p : REGISTRY.all()) {
            assertEquals(DOCUMENTED, BUILDER.build(p).sex(),
                p.identity().variantId() + " deve ter sexo documentado");
        }
    }

    @Test
    void contextoNaoDocumentadoEmNenhumaVarianteDaV1() {
        for (EquationVariantScientificProfile p : REGISTRY.all()) {
            EvidenceSummary s = BUILDER.build(p);
            String id = p.identity().variantId();
            assertEquals(NOT_DOCUMENTED, s.athlete(), id);
            assertEquals(NOT_DOCUMENTED, s.trainingLevel(), id);
            assertEquals(NOT_DOCUMENTED, s.modality(), id);
            assertEquals(NOT_DOCUMENTED, s.bodyCharacteristics(), id);
        }
    }

    // ===== Cobertura realista de variantes específicas =====

    @Test
    void jp7F_temCoberturaCompleta_sexoIdadePopulacao() {
        EvidenceSummary s = BUILDER.build(REGISTRY.resolve("JP7-F"));
        assertEquals(DOCUMENTED, s.sex());
        assertEquals(DOCUMENTED, s.age());       // originalDevelopmentAgeRange 18-55
        assertEquals(DOCUMENTED, s.population());
    }

    @Test
    void falk4_temCoberturaRealista_semIdadeDocumentada() {
        // FALK4 é histórico (1968): não tem faixa etária formal de desenvolvimento.
        // A ficha registra explicitamente originalDevelopmentAgeRange: null.
        EvidenceSummary s = BUILDER.build(REGISTRY.resolve("FALK4"));
        assertEquals(DOCUMENTED, s.sex());
        assertEquals(NOT_DOCUMENTED, s.age(), "FALK4 não tem faixa etária formal documentada");
        assertEquals(DOCUMENTED, s.population());
    }

    @Test
    void gM3_temCoberturaCompleta() {
        EvidenceSummary s = BUILDER.build(REGISTRY.resolve("G-M3"));
        assertEquals(DOCUMENTED, s.sex());
        assertEquals(DOCUMENTED, s.age());
        assertEquals(DOCUMENTED, s.population());
    }
}