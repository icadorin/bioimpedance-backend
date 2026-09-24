package com.bioimpedance.domain.eligibility;

import com.bioimpedance.domain.contracts.ApplicabilityResult;
import com.bioimpedance.domain.contracts.CandidateStatus;
import com.bioimpedance.domain.contracts.DocumentationStatus;
import com.bioimpedance.domain.contracts.EvidenceSummary;
import com.bioimpedance.domain.contracts.MatchClassification;
import com.bioimpedance.domain.contracts.MatchDimension;
import com.bioimpedance.domain.contracts.MatchResult;
import com.bioimpedance.domain.contracts.ReadinessResult;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EligibilityGoldenTest {

    private static final EligibilityResolver RESOLVER = new EligibilityResolver();

    // ===== Helpers =====

    private static MatchResult match(MatchDimension dim, MatchClassification cls) {
        return new MatchResult(dim, cls, null);
    }

    private static EvidenceSummary evidence() {
        return new EvidenceSummary(
            DocumentationStatus.DOCUMENTED, DocumentationStatus.DOCUMENTED,
            DocumentationStatus.DOCUMENTED, DocumentationStatus.NOT_DOCUMENTED,
            DocumentationStatus.NOT_DOCUMENTED, DocumentationStatus.NOT_DOCUMENTED,
            DocumentationStatus.NOT_DOCUMENTED);
    }

    private static ApplicabilityResult applicable(String variantId) {
        return new ApplicabilityResult(variantId,
            match(MatchDimension.SEX, MatchClassification.EXACT),
            match(MatchDimension.AGE, MatchClassification.EXACT),
            match(MatchDimension.POPULATION, MatchClassification.HIGH),
            match(MatchDimension.CONTEXT, MatchClassification.NOT_DOCUMENTED),
            evidence(), List.of(), false);
    }

    private static ApplicabilityResult sexIncompatible(String variantId) {
        return new ApplicabilityResult(variantId,
            match(MatchDimension.SEX, MatchClassification.LOW),
            match(MatchDimension.AGE, MatchClassification.EXACT),
            match(MatchDimension.POPULATION, MatchClassification.HIGH),
            match(MatchDimension.CONTEXT, MatchClassification.NOT_DOCUMENTED),
            evidence(), List.of(), false);
    }

    // ===== Estados =====

    @Test
    void aplicavel_comTodosInputs_ready() {
        ReadinessResult r = RESOLVER.resolve(applicable("JP7-M"),
            Set.of("SKINFOLD_CHEST"), Set.of("SKINFOLD_CHEST"), true);
        assertEquals(CandidateStatus.READY, r.status());
        assertTrue(r.missingInputs().isEmpty());
    }

    @Test
    void idadeForaDaFaixa_readyComWarning_naoIneligible() {
        ApplicabilityResult a = new ApplicabilityResult("JP7-M",
            match(MatchDimension.SEX, MatchClassification.EXACT),
            match(MatchDimension.AGE, MatchClassification.OUTSIDE_VALIDATED_RANGE),
            match(MatchDimension.POPULATION, MatchClassification.HIGH),
            match(MatchDimension.CONTEXT, MatchClassification.NOT_DOCUMENTED),
            evidence(), List.of(), false);
        ReadinessResult r = RESOLVER.resolve(a, Set.of(), Set.of(), true);
        assertEquals(CandidateStatus.READY, r.status());
        assertTrue(r.warnings().contains("AGE_OUTSIDE_VALIDATED_RANGE"));
    }

    @Test
    void inputObrigatorioAusente_missingInputs() {
        ReadinessResult r = RESOLVER.resolve(applicable("JP7-M"),
            Set.of("SKINFOLD_CHEST", "SKINFOLD_THIGH"),
            Set.of("SKINFOLD_CHEST"), true);
        assertEquals(CandidateStatus.MISSING_INPUTS, r.status());
        assertEquals(List.of("SKINFOLD_THIGH"), r.missingInputs());
    }

    @Test
    void sexoIncompativel_ineligible() {
        ReadinessResult r = RESOLVER.resolve(sexIncompatible("JP7-F"),
            Set.of(), Set.of(), true);
        assertEquals(CandidateStatus.INELIGIBLE, r.status());
        assertTrue(r.exclusionReasons().contains("SEX_NOT_SUPPORTED"));
    }

    @Test
    void restricaoCientificaExplicita_ineligible() {
        ApplicabilityResult a = new ApplicabilityResult("VARIANT-X",
            match(MatchDimension.SEX, MatchClassification.EXACT),
            match(MatchDimension.AGE, MatchClassification.EXACT),
            match(MatchDimension.POPULATION, MatchClassification.HIGH),
            match(MatchDimension.CONTEXT, MatchClassification.NOT_DOCUMENTED),
            evidence(), List.of(), true); // hasBlockingScientificRestriction
        ReadinessResult r = RESOLVER.resolve(a, Set.of(), Set.of(), true);
        assertEquals(CandidateStatus.INELIGIBLE, r.status());
        assertTrue(r.exclusionReasons().contains("SCIENTIFIC_RESTRICTION"));
    }

    @Test
    void varianteDesabilitada_disabled() {
        ReadinessResult r = RESOLVER.resolve(applicable("JP7-M"),
            Set.of(), Set.of(), false);
        assertEquals(CandidateStatus.DISABLED, r.status());
    }

    // ===== Precedência e invariantes =====

    @Test
    void disabledTemPrecedenciaSobreIneligible() {
        ReadinessResult r = RESOLVER.resolve(sexIncompatible("JP7-F"),
            Set.of(), Set.of(), false); // disabled E sexo incompatível
        assertEquals(CandidateStatus.DISABLED, r.status());
    }

    @Test
    void ineligivelNuncaViraReady_mesmoComTodosInputs() {
        ReadinessResult r = RESOLVER.resolve(sexIncompatible("JP7-F"),
            Set.of("SKINFOLD_CHEST"), Set.of("SKINFOLD_CHEST"), true);
        assertEquals(CandidateStatus.INELIGIBLE, r.status());
        assertNotEquals(CandidateStatus.READY, r.status());
    }

    @Test
    void missingInputsNuncaViraReady() {
        ReadinessResult r = RESOLVER.resolve(applicable("JP7-M"),
            Set.of("SKINFOLD_CHEST", "SKINFOLD_THIGH"),
            Set.of("SKINFOLD_CHEST"), true);
        assertEquals(CandidateStatus.MISSING_INPUTS, r.status());
        assertNotEquals(CandidateStatus.READY, r.status());
    }

    @Test
    void idadeParcial_readyComWarning() {
        ApplicabilityResult a = new ApplicabilityResult("JP7-M",
            match(MatchDimension.SEX, MatchClassification.EXACT),
            match(MatchDimension.AGE, MatchClassification.PARTIAL),
            match(MatchDimension.POPULATION, MatchClassification.HIGH),
            match(MatchDimension.CONTEXT, MatchClassification.NOT_DOCUMENTED),
            evidence(), List.of(), false);
        ReadinessResult r = RESOLVER.resolve(a, Set.of(), Set.of(), true);
        assertEquals(CandidateStatus.READY, r.status());
        assertTrue(r.warnings().contains("AGE_PARTIAL_MATCH"));
    }

    @Test
    void multiplosInputsAusentes_ordemEstavel() {
        ReadinessResult r = RESOLVER.resolve(applicable("JP7-M"),
            Set.of("SKINFOLD_THIGH", "SKINFOLD_CHEST", "SKINFOLD_ABDOMEN"),
            Set.of(), true);
        assertEquals(CandidateStatus.MISSING_INPUTS, r.status());
        assertEquals(
            List.of("SKINFOLD_ABDOMEN", "SKINFOLD_CHEST", "SKINFOLD_THIGH"),
            r.missingInputs());
    }
}