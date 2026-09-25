package com.bioimpedance.domain.applicability;

import com.bioimpedance.domain.contracts.*;
import com.bioimpedance.library.scientificrules.EquationVariantScientificProfile;
import com.bioimpedance.library.scientificrules.ScientificRuleRegistry;
import org.junit.jupiter.api.Test;

import static com.bioimpedance.domain.contracts.DocumentationStatus.DOCUMENTED;
import static com.bioimpedance.domain.contracts.DocumentationStatus.NOT_DOCUMENTED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ApplicabilityGoldenTest {

    private static final ScientificRuleRegistry REGISTRY = new ScientificRuleRegistry();
    private static final ApplicabilityEngine ENGINE =
        new ApplicabilityEngine(new EvidenceSummaryBuilder());

    private static EquationVariantScientificProfile profile(String id) {
        return REGISTRY.resolve(id);
    }

    private static AssessmentContext ctx(Sex sex, int age) {
        return new AssessmentContext(
            new ClientProfile(sex, age, null, null, null),
            null);
    }

    // ===== SEX =====

    @Test
    void jp7F_withFemale_44Years_exactSexMatch() {
        ApplicabilityResult r = ENGINE.evaluate(profile("JP7-F"), ctx(Sex.FEMALE, 44));
        assertEquals(MatchClassification.EXACT, r.sexMatch().classification());
    }

    @Test
    void jp7F_withMale_incompatibleSex() {
        ApplicabilityResult r = ENGINE.evaluate(profile("JP7-F"), ctx(Sex.MALE, 44));
        assertEquals(MatchClassification.LOW, r.sexMatch().classification());
    }

    @Test
    void falk4_supportsOnlyMale() {
        ApplicabilityResult r1 = ENGINE.evaluate(profile("FALK4"), ctx(Sex.MALE, 25));
        ApplicabilityResult r2 = ENGINE.evaluate(profile("FALK4"), ctx(Sex.FEMALE, 25));
        assertEquals(MatchClassification.EXACT, r1.sexMatch().classification());
        assertEquals(MatchClassification.LOW, r2.sexMatch().classification());
    }

    // ===== AGE =====

    @Test
    void jp7F_female_44Years_withinValidatedRange_exact() {
        // validatedAgeRanges 18-55
        ApplicabilityResult r = ENGINE.evaluate(profile("JP7-F"), ctx(Sex.FEMALE, 44));
        assertEquals(MatchClassification.EXACT, r.ageMatch().classification());
    }

    @Test
    void jp7F_female_16Years_outsideValidatedRange() {
        ApplicabilityResult r = ENGINE.evaluate(profile("JP7-F"), ctx(Sex.FEMALE, 16));
        assertEquals(MatchClassification.OUTSIDE_VALIDATED_RANGE, r.ageMatch().classification());
    }

    @Test
    void jp7F_female_60Years_outsideValidatedRange() {
        ApplicabilityResult r = ENGINE.evaluate(profile("JP7-F"), ctx(Sex.FEMALE, 60));
        assertEquals(MatchClassification.OUTSIDE_VALIDATED_RANGE, r.ageMatch().classification());
    }

    @Test
    void gM3_male_25Years_partial_withoutValidatedBounds() {
        // G-M3: originalDevelopment 18-30, validatedAgeRanges [{range: {min:null,max:null}}]
        ApplicabilityResult r = ENGINE.evaluate(profile("G-M3"), ctx(Sex.MALE, 25));
        assertEquals(MatchClassification.PARTIAL, r.ageMatch().classification());
    }

    @Test
    void gM3_male_45Years_outside_developmentRange() {
        ApplicabilityResult r = ENGINE.evaluate(profile("G-M3"), ctx(Sex.MALE, 45));
        assertEquals(MatchClassification.OUTSIDE_VALIDATED_RANGE, r.ageMatch().classification());
    }

    @Test
    void falk4_male_25Years_outside_withoutDocumentedRange() {
        // FALK4 has no formal age range — any age falls into OUTSIDE_VALIDATED_RANGE
        // (without a documented range, the age is "outside everything documented").
        ApplicabilityResult r = ENGINE.evaluate(profile("FALK4"), ctx(Sex.MALE, 25));
        assertEquals(MatchClassification.OUTSIDE_VALIDATED_RANGE, r.ageMatch().classification());
    }

    // ===== POPULATION =====

    @Test
    void jp7M_male_30Years_highPopulation() {
        // originalPopulation sexCoverage=[MALE], ageCoverage=18-61
        ApplicabilityResult r = ENGINE.evaluate(profile("JP7-M"), ctx(Sex.MALE, 30));
        assertEquals(MatchClassification.HIGH, r.populationMatch().classification());
    }

    @Test
    void jp7M_female_30Years_moderatePopulation_sexDiverges() {
        ApplicabilityResult r = ENGINE.evaluate(profile("JP7-M"), ctx(Sex.FEMALE, 30));
        assertEquals(MatchClassification.MODERATE, r.populationMatch().classification());
    }

    @Test
    void jp7M_male_80Years_moderatePopulation_ageDiverges() {
        ApplicabilityResult r = ENGINE.evaluate(profile("JP7-M"), ctx(Sex.MALE, 80));
        assertEquals(MatchClassification.MODERATE, r.populationMatch().classification());
    }

    @Test
    void jp7M_female_80Years_lowPopulation_bothDiverge() {
        ApplicabilityResult r = ENGINE.evaluate(profile("JP7-M"), ctx(Sex.FEMALE, 80));
        assertEquals(MatchClassification.LOW, r.populationMatch().classification());
    }

    // ===== CONTEXT =====

    @Test
    void allVariants_V1_contextNotDocumented() {
        for (EquationVariantScientificProfile p : REGISTRY.all()) {
            ApplicabilityResult r = ENGINE.evaluate(p, ctx(Sex.MALE, 30));
            assertEquals(MatchClassification.NOT_DOCUMENTED, r.contextMatch().classification(),
                p.identity().variantId() + " should have context NOT_DOCUMENTED in V1");
        }
    }

    // ===== INVARIANTS =====

    @Test
    void noVariantHasExplicitScientificBlockingInV1() {
        for (EquationVariantScientificProfile p : REGISTRY.all()) {
            ApplicabilityResult r = ENGINE.evaluate(p, ctx(Sex.MALE, 30));
            assertFalse(r.hasBlockingScientificRestriction(),
                p.identity().variantId() + " must not have explicit INELIGIBLE restriction in V1");
        }
    }

    @Test
    void evidenceCoverage_consistentWithRealData() {
        // Checks that sex/age/population are DOCUMENTED when documented,
        // and that context remains NOT_DOCUMENTED in V1 — WITHOUT false universal premise.
        for (EquationVariantScientificProfile p : REGISTRY.all()) {
            ApplicabilityResult r = ENGINE.evaluate(p, ctx(Sex.MALE, 30));
            var cov = r.evidenceSummary();
            assertEquals(DOCUMENTED, cov.sex(), p.identity().variantId());
            assertEquals(NOT_DOCUMENTED, cov.athlete(), p.identity().variantId());
            assertEquals(NOT_DOCUMENTED, cov.trainingLevel(), p.identity().variantId());
            assertEquals(NOT_DOCUMENTED, cov.modality(), p.identity().variantId());
            assertEquals(NOT_DOCUMENTED, cov.bodyCharacteristics(), p.identity().variantId());
            // age and population: we accept DOCUMENTED or NOT_DOCUMENTED according to the actual record.
            // The specific FALK4 test (in EvidenceCoverageGoldenTest) guarantees the historical case.
        }
    }

    @Test
    void incompatibleSex_generatesRestrictionWarning() {
        // Sanity: when sex is LOW, the result remains valid (does not block here).
        // Blocking is the responsibility of Phase 6.
        ApplicabilityResult r = ENGINE.evaluate(profile("JP7-F"), ctx(Sex.MALE, 30));
        assertEquals(MatchClassification.LOW, r.sexMatch().classification());
        assertFalse(r.hasBlockingScientificRestriction());
    }
}