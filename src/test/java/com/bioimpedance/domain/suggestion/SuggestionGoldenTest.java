package com.bioimpedance.domain.suggestion;

import com.bioimpedance.domain.contracts.ApplicabilityResult;
import com.bioimpedance.domain.contracts.CandidateStatus;
import com.bioimpedance.domain.contracts.CandidateVariantSummary;
import com.bioimpedance.domain.contracts.DocumentationStatus;
import com.bioimpedance.domain.contracts.EvidenceSummary;
import com.bioimpedance.domain.contracts.MatchClassification;
import com.bioimpedance.domain.contracts.MatchDimension;
import com.bioimpedance.domain.contracts.MatchResult;
import com.bioimpedance.domain.contracts.ReadinessResult;
import com.bioimpedance.domain.contracts.SuggestionResult;
import com.bioimpedance.domain.contracts.SuggestionStatus;
import com.bioimpedance.domain.suggestion.explanation.SuggestionExplanationBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SuggestionGoldenTest {

    private static final SuggestionEngine ENGINE = new SuggestionEngine(
        new CompatibilityRanker(), new SuggestionExplanationBuilder());

    // ===== Helpers =====

    private static ReadinessResult readiness(String variantId, CandidateStatus status) {
        return new ReadinessResult(variantId, status, List.of(), List.of(), List.of(), List.of());
    }

    private static ReadinessResult readiness(String variantId, CandidateStatus status,
                                             List<String> missingInputs, List<String> warnings,
                                             List<String> exclusionReasons, List<String> reasons) {
        return new ReadinessResult(variantId, status, missingInputs, warnings, exclusionReasons, reasons);
    }

    private static ApplicabilityResult applicability(String variantId,
                                                     MatchClassification age,
                                                     MatchClassification population) {
        return new ApplicabilityResult(variantId,
            new MatchResult(MatchDimension.SEX, MatchClassification.EXACT, null),
            new MatchResult(MatchDimension.AGE, age, null),
            new MatchResult(MatchDimension.POPULATION, population, null),
            new MatchResult(MatchDimension.CONTEXT, MatchClassification.NOT_DOCUMENTED, null),
            new EvidenceSummary(
                DocumentationStatus.DOCUMENTED, DocumentationStatus.DOCUMENTED,
                DocumentationStatus.DOCUMENTED, DocumentationStatus.NOT_DOCUMENTED,
                DocumentationStatus.NOT_DOCUMENTED, DocumentationStatus.NOT_DOCUMENTED,
                DocumentationStatus.NOT_DOCUMENTED),
            List.of(), false);
    }

    private static CandidateVariantSummary find(List<CandidateVariantSummary> list, String variantId) {
        return list.stream()
            .filter(c -> c.variantId().equals(variantId))
            .findFirst()
            .orElseThrow(() -> new AssertionError(variantId + " não encontrado na lista"));
    }

    // ===== Indicação básica =====

    @Test
    void singleReadyVariant_suggested() {
        SuggestionResult r = ENGINE.suggest(
            List.of(readiness("JP7-M", CandidateStatus.READY)),
            Map.of("JP7-M", applicability("JP7-M", MatchClassification.EXACT, MatchClassification.HIGH)));
        assertEquals(SuggestionStatus.SUGGESTED, r.status());
        assertEquals(1, r.suggestedVariants().size());
        assertEquals("JP7-M", r.suggestedVariants().getFirst().variantId());
    }

    @Test
    void tieWithoutDifferentiationRule_allSuggested() {
        // §11.3: sem regra válida para diferenciar, indicar as empatadas.
        SuggestionResult r = ENGINE.suggest(
            List.of(readiness("JP7-M", CandidateStatus.READY),
                readiness("P-M3", CandidateStatus.READY)),
            Map.of("JP7-M", applicability("JP7-M", MatchClassification.EXACT, MatchClassification.HIGH),
                "P-M3", applicability("P-M3", MatchClassification.EXACT, MatchClassification.HIGH)));
        assertEquals(SuggestionStatus.SUGGESTED, r.status());
        assertEquals(2, r.suggestedVariants().size());
    }

    @Test
    void exactAgeBeatsPartial() {
        SuggestionResult r = ENGINE.suggest(
            List.of(readiness("JP7-M", CandidateStatus.READY),
                readiness("G-M3", CandidateStatus.READY)),
            Map.of("JP7-M", applicability("JP7-M", MatchClassification.EXACT, MatchClassification.HIGH),
                "G-M3", applicability("G-M3", MatchClassification.PARTIAL, MatchClassification.HIGH)));
        assertEquals(List.of("JP7-M"),
            r.suggestedVariants().stream().map(CandidateVariantSummary::variantId).toList());
    }

    @Test
    void highPopulationBeatsModerate_whenAgeIsEqual() {
        SuggestionResult r = ENGINE.suggest(
            List.of(readiness("JP7-M", CandidateStatus.READY),
                readiness("P-M3", CandidateStatus.READY)),
            Map.of("JP7-M", applicability("JP7-M", MatchClassification.EXACT, MatchClassification.HIGH),
                "P-M3", applicability("P-M3", MatchClassification.EXACT, MatchClassification.MODERATE)));
        assertEquals(List.of("JP7-M"),
            r.suggestedVariants().stream().map(CandidateVariantSummary::variantId).toList());
    }

    @Test
    void readyWithWarning_remainsSuggested() {
        // §9: warning não torna a variante inelegível.
        SuggestionResult r = ENGINE.suggest(
            List.of(readiness("JP7-M", CandidateStatus.READY,
                List.of(), List.of("AGE_OUTSIDE_VALIDATED_RANGE"), List.of(), List.of())),
            Map.of("JP7-M", applicability("JP7-M",
                MatchClassification.OUTSIDE_VALIDATED_RANGE, MatchClassification.HIGH)));
        assertEquals(SuggestionStatus.SUGGESTED, r.status());
        assertEquals(1, r.suggestedVariants().size());
        assertTrue(r.warnings().contains("AGE_OUTSIDE_VALIDATED_RANGE"));
    }

    // ===== Estados extremos (§12) =====

    @Test
    void onlyMissingInputs_noReadyMethod() {
        SuggestionResult r = ENGINE.suggest(
            List.of(readiness("JP7-M", CandidateStatus.MISSING_INPUTS,
                List.of("SKINFOLD_CHEST", "SKINFOLD_THIGH"), List.of(), List.of(),
                List.of("Faltam 2 medida(s) obrigatória(s)."))),
            Map.of("JP7-M", applicability("JP7-M", MatchClassification.EXACT, MatchClassification.HIGH)));
        assertEquals(SuggestionStatus.NO_READY_METHOD, r.status());
        assertTrue(r.suggestedVariants().isEmpty());
        CandidateVariantSummary candidate = find(r.candidateVariants(), "JP7-M");
        assertEquals(List.of("SKINFOLD_CHEST", "SKINFOLD_THIGH"), candidate.missingInputs());
    }

    @Test
    void allIneligible_noEligibleMethod() {
        SuggestionResult r = ENGINE.suggest(
            List.of(readiness("JP7-F", CandidateStatus.INELIGIBLE,
                List.of(), List.of(), List.of("SEX_NOT_SUPPORTED"),
                List.of("Sexo incompatível com a variante."))),
            Map.of("JP7-F", applicability("JP7-F", MatchClassification.EXACT, MatchClassification.HIGH)));
        assertEquals(SuggestionStatus.NO_ELIGIBLE_METHOD, r.status());
        assertTrue(r.suggestedVariants().isEmpty());
        assertEquals(1, r.excludedVariants().size());
        assertTrue(r.excludedVariants().getFirst().reasons().contains("Sexo incompatível com a variante."));
    }

    @Test
    void allDisabled_noEnabledMethod() {
        SuggestionResult r = ENGINE.suggest(
            List.of(readiness("JP7-M", CandidateStatus.DISABLED,
                    List.of(), List.of(), List.of("VARIANT_DISABLED"),
                    List.of("Variante desabilitada pelo profissional.")),
                readiness("G-M3", CandidateStatus.DISABLED,
                    List.of(), List.of(), List.of("VARIANT_DISABLED"),
                    List.of("Variante desabilitada pelo profissional."))),
            Map.of("JP7-M", applicability("JP7-M", MatchClassification.EXACT, MatchClassification.HIGH),
                "G-M3", applicability("G-M3", MatchClassification.EXACT, MatchClassification.HIGH)));
        assertEquals(SuggestionStatus.NO_ENABLED_METHOD, r.status());
        assertEquals(2, r.excludedVariants().size());
    }

    @Test
    void mixedScenario_readySuggested_missingCandidate_ineligibleAndDisabledExcluded() {
        SuggestionResult r = ENGINE.suggest(
            List.of(readiness("JP7-M", CandidateStatus.READY),
                readiness("P-M16", CandidateStatus.MISSING_INPUTS,
                    List.of("SKINFOLD_TRICEPS"), List.of(), List.of(),
                    List.of("Faltam 1 medida(s) obrigatória(s).")),
                readiness("JP7-F", CandidateStatus.INELIGIBLE,
                    List.of(), List.of(), List.of("SEX_NOT_SUPPORTED"),
                    List.of("Sexo incompatível com a variante.")),
                readiness("G-M3", CandidateStatus.DISABLED,
                    List.of(), List.of(), List.of("VARIANT_DISABLED"),
                    List.of("Variante desabilitada pelo profissional."))),
            Map.of("JP7-M", applicability("JP7-M", MatchClassification.EXACT, MatchClassification.HIGH),
                "P-M16", applicability("P-M16", MatchClassification.EXACT, MatchClassification.HIGH),
                "JP7-F", applicability("JP7-F", MatchClassification.EXACT, MatchClassification.HIGH),
                "G-M3", applicability("G-M3", MatchClassification.EXACT, MatchClassification.HIGH)));
        assertEquals(SuggestionStatus.SUGGESTED, r.status());
        assertEquals(List.of("JP7-M"),
            r.suggestedVariants().stream().map(CandidateVariantSummary::variantId).toList());
        assertEquals(2, r.candidateVariants().size());  // READY + MISSING_INPUTS
        assertEquals(2, r.excludedVariants().size());   // INELIGIBLE + DISABLED
    }

    // ===== Invariantes =====

    @Test
    void ineligibleAndDisabled_neverAppearInSuggestedOrCandidates() {
        SuggestionResult r = ENGINE.suggest(
            List.of(readiness("JP7-M", CandidateStatus.READY),
                readiness("JP7-F", CandidateStatus.INELIGIBLE,
                    List.of(), List.of(), List.of("SEX_NOT_SUPPORTED"), List.of("x")),
                readiness("G-M3", CandidateStatus.DISABLED,
                    List.of(), List.of(), List.of("VARIANT_DISABLED"), List.of("x"))),
            Map.of("JP7-M", applicability("JP7-M", MatchClassification.EXACT, MatchClassification.HIGH),
                "JP7-F", applicability("JP7-F", MatchClassification.EXACT, MatchClassification.HIGH),
                "G-M3", applicability("G-M3", MatchClassification.EXACT, MatchClassification.HIGH)));
        for (CandidateVariantSummary c : r.suggestedVariants()) {
            assertEquals(CandidateStatus.READY, c.status());
        }
        for (CandidateVariantSummary c : r.candidateVariants()) {
            assertTrue(c.status() == CandidateStatus.READY
                || c.status() == CandidateStatus.MISSING_INPUTS);
        }
    }

    @Test
    void sameInput_sameOutput_determinism() {
        // architecture.md §20
        List<ReadinessResult> readinessList = List.of(
            readiness("P-M3", CandidateStatus.READY),
            readiness("JP7-M", CandidateStatus.READY));
        Map<String, ApplicabilityResult> applicabilityMap = Map.of(
            "P-M3", applicability("P-M3", MatchClassification.EXACT, MatchClassification.HIGH),
            "JP7-M", applicability("JP7-M", MatchClassification.EXACT, MatchClassification.HIGH));
        SuggestionResult r1 = ENGINE.suggest(readinessList, applicabilityMap);
        SuggestionResult r2 = ENGINE.suggest(readinessList, applicabilityMap);
        assertEquals(r1, r2);
    }

    @Test
    void readinessWithoutCorrespondingApplicability_throwsIllegalStateException() {
        // Violação de contrato da orchestration — fail-fast.
        assertThrows(IllegalStateException.class, () -> ENGINE.suggest(
            List.of(readiness("JP7-M", CandidateStatus.READY)),
            Map.of())); // mapa vazio: falta o ApplicabilityResult
    }
}