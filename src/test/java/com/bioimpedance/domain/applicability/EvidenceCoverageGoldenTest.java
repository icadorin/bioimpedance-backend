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

    // ===== REAL universal invariants =====

    @Test
    void sexDocumentedInAll43Variants() {
        for (EquationVariantScientificProfile p : REGISTRY.all()) {
            assertEquals(DOCUMENTED, BUILDER.build(p).sex(),
                p.identity().variantId() + " must have documented sex");
        }
    }

    @Test
    void contextNotDocumentedInAnyV1Variant() {
        for (EquationVariantScientificProfile p : REGISTRY.all()) {
            EvidenceSummary s = BUILDER.build(p);
            String id = p.identity().variantId();
            assertEquals(NOT_DOCUMENTED, s.athlete(), id);
            assertEquals(NOT_DOCUMENTED, s.trainingLevel(), id);
            assertEquals(NOT_DOCUMENTED, s.modality(), id);
            assertEquals(NOT_DOCUMENTED, s.bodyCharacteristics(), id);
        }
    }

    // ===== Realistic coverage of specific variants =====

    @Test
    void jp7F_hasCompleteCoverage_sexAgePopulation() {
        EvidenceSummary s = BUILDER.build(REGISTRY.resolve("JP7-F"));
        assertEquals(DOCUMENTED, s.sex());
        assertEquals(DOCUMENTED, s.age());       // originalDevelopmentAgeRange 18-55
        assertEquals(DOCUMENTED, s.population());
    }

    @Test
    void falk4_hasRealisticCoverage_withoutDocumentedAge() {
        // FALK4 is historical (1968): it has no formal development age range.
        // The record explicitly registers originalDevelopmentAgeRange: null.
        EvidenceSummary s = BUILDER.build(REGISTRY.resolve("FALK4"));
        assertEquals(DOCUMENTED, s.sex());
        assertEquals(NOT_DOCUMENTED, s.age(), "FALK4 has no formal documented age range");
        assertEquals(DOCUMENTED, s.population());
    }

    @Test
    void gM3_hasCompleteCoverage() {
        EvidenceSummary s = BUILDER.build(REGISTRY.resolve("G-M3"));
        assertEquals(DOCUMENTED, s.sex());
        assertEquals(DOCUMENTED, s.age());
        assertEquals(DOCUMENTED, s.population());
    }
}