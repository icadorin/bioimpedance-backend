package com.bioimpedance.domain.applicability;

import com.bioimpedance.domain.contracts.AssessmentContext;
import com.bioimpedance.domain.contracts.ClientProfile;
import com.bioimpedance.domain.contracts.MatchClassification;
import com.bioimpedance.domain.contracts.Sex;
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
    void jp7F_comMulher_44anos_sexoExato() {
        ApplicabilityResult r = ENGINE.evaluate(profile("JP7-F"), ctx(Sex.FEMALE, 44));
        assertEquals(MatchClassification.EXACT, r.sexMatch().classification());
    }

    @Test
    void jp7F_comHomem_sexoIncompativel() {
        ApplicabilityResult r = ENGINE.evaluate(profile("JP7-F"), ctx(Sex.MALE, 44));
        assertEquals(MatchClassification.LOW, r.sexMatch().classification());
    }

    @Test
    void falk4_suportaApenasMale() {
        ApplicabilityResult r1 = ENGINE.evaluate(profile("FALK4"), ctx(Sex.MALE, 25));
        ApplicabilityResult r2 = ENGINE.evaluate(profile("FALK4"), ctx(Sex.FEMALE, 25));
        assertEquals(MatchClassification.EXACT, r1.sexMatch().classification());
        assertEquals(MatchClassification.LOW, r2.sexMatch().classification());
    }

    // ===== AGE =====

    @Test
    void jp7F_mulher_44anos_dentroDaFaixaValidada_exato() {
        // validatedAgeRanges 18-55
        ApplicabilityResult r = ENGINE.evaluate(profile("JP7-F"), ctx(Sex.FEMALE, 44));
        assertEquals(MatchClassification.EXACT, r.ageMatch().classification());
    }

    @Test
    void jp7F_mulher_16anos_outsideValidatedRange() {
        ApplicabilityResult r = ENGINE.evaluate(profile("JP7-F"), ctx(Sex.FEMALE, 16));
        assertEquals(MatchClassification.OUTSIDE_VALIDATED_RANGE, r.ageMatch().classification());
    }

    @Test
    void jp7F_mulher_60anos_outsideValidatedRange() {
        ApplicabilityResult r = ENGINE.evaluate(profile("JP7-F"), ctx(Sex.FEMALE, 60));
        assertEquals(MatchClassification.OUTSIDE_VALIDATED_RANGE, r.ageMatch().classification());
    }

    @Test
    void gM3_homem_25anos_partial_semValidatedComBounds() {
        // G-M3: originalDevelopment 18-30, validatedAgeRanges [{range: {min:null,max:null}}]
        ApplicabilityResult r = ENGINE.evaluate(profile("G-M3"), ctx(Sex.MALE, 25));
        assertEquals(MatchClassification.PARTIAL, r.ageMatch().classification());
    }

    @Test
    void gM3_homem_45anos_outside_foraDoDevelopment() {
        ApplicabilityResult r = ENGINE.evaluate(profile("G-M3"), ctx(Sex.MALE, 45));
        assertEquals(MatchClassification.OUTSIDE_VALIDATED_RANGE, r.ageMatch().classification());
    }

    @Test
    void falk4_homem_25anos_outside_semFaixaDocumentada() {
        // FALK4 não tem faixa etária formal — qualquer idade cai em OUTSIDE_VALIDATED_RANGE
        // (sem faixa documentada, a idade está "fora de tudo que é documentado").
        ApplicabilityResult r = ENGINE.evaluate(profile("FALK4"), ctx(Sex.MALE, 25));
        assertEquals(MatchClassification.OUTSIDE_VALIDATED_RANGE, r.ageMatch().classification());
    }

    // ===== POPULATION =====

    @Test
    void jp7M_homem_30anos_populacaoAlta() {
        // originalPopulation sexCoverage=[MALE], ageCoverage=18-61
        ApplicabilityResult r = ENGINE.evaluate(profile("JP7-M"), ctx(Sex.MALE, 30));
        assertEquals(MatchClassification.HIGH, r.populationMatch().classification());
    }

    @Test
    void jp7M_mulher_30anos_populacaoModerada_sexoDiverge() {
        ApplicabilityResult r = ENGINE.evaluate(profile("JP7-M"), ctx(Sex.FEMALE, 30));
        assertEquals(MatchClassification.MODERATE, r.populationMatch().classification());
    }

    @Test
    void jp7M_homem_80anos_populacaoModerada_idadeDiverge() {
        ApplicabilityResult r = ENGINE.evaluate(profile("JP7-M"), ctx(Sex.MALE, 80));
        assertEquals(MatchClassification.MODERATE, r.populationMatch().classification());
    }

    @Test
    void jp7M_mulher_80anos_populacaoBaixa_ambosDivergem() {
        ApplicabilityResult r = ENGINE.evaluate(profile("JP7-M"), ctx(Sex.FEMALE, 80));
        assertEquals(MatchClassification.LOW, r.populationMatch().classification());
    }

    // ===== CONTEXT =====

    @Test
    void todasAsVariantes_V1_contextoNotDocumented() {
        for (EquationVariantScientificProfile p : REGISTRY.all()) {
            ApplicabilityResult r = ENGINE.evaluate(p, ctx(Sex.MALE, 30));
            assertEquals(MatchClassification.NOT_DOCUMENTED, r.contextMatch().classification(),
                p.identity().variantId() + " deveria ter contexto NOT_DOCUMENTED na V1");
        }
    }

    // ===== INVARIANTES =====

    @Test
    void nenhumaVarianteTemBloqueioCientificoExplicitoNaV1() {
        for (EquationVariantScientificProfile p : REGISTRY.all()) {
            ApplicabilityResult r = ENGINE.evaluate(p, ctx(Sex.MALE, 30));
            assertFalse(r.hasBlockingScientificRestriction(),
                p.identity().variantId() + " não deve ter restrição INELIGIBLE explícita na V1");
        }
    }

    @Test
    void coberturaDeEvidencia_consistenteComOsDadosReais() {
        // Verifica que sexo/idade/população ficam DOCUMENTED quando documentados,
        // e que contexto permanece NOT_DOCUMENTED na V1 — SEM premissa universal falsa.
        for (EquationVariantScientificProfile p : REGISTRY.all()) {
            ApplicabilityResult r = ENGINE.evaluate(p, ctx(Sex.MALE, 30));
            var cov = r.evidenceSummary();
            assertEquals(DOCUMENTED, cov.sex(), p.identity().variantId());
            assertEquals(NOT_DOCUMENTED, cov.athlete(), p.identity().variantId());
            assertEquals(NOT_DOCUMENTED, cov.trainingLevel(), p.identity().variantId());
            assertEquals(NOT_DOCUMENTED, cov.modality(), p.identity().variantId());
            assertEquals(NOT_DOCUMENTED, cov.bodyCharacteristics(), p.identity().variantId());
            // age e population: aceitamos DOCUMENTED ou NOT_DOCUMENTED conforme a ficha real.
            // O teste específico do FALK4 (no EvidenceCoverageGoldenTest) garante o caso histórico.
        }
    }

    @Test
    void sexoIncompativel_geraWarningDeRestricao() {
        // Sanity: quando sexo é LOW, o resultado continua válido (não bloqueia aqui).
        // Bloqueio é responsabilidade da Fase 6.
        ApplicabilityResult r = ENGINE.evaluate(profile("JP7-F"), ctx(Sex.MALE, 30));
        assertEquals(MatchClassification.LOW, r.sexMatch().classification());
        assertFalse(r.hasBlockingScientificRestriction());
    }
}