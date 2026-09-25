package com.bioimpedance.domain.conversionsuggestion;

import com.bioimpedance.domain.contracts.ConversionCandidateInput;
import com.bioimpedance.domain.contracts.ConversionSuggestionResult;
import com.bioimpedance.domain.contracts.ConversionSuggestionStatus;
import com.bioimpedance.domain.conversionsuggestion.explanation.ConversionSuggestionExplanationBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConversionSuggestionGoldenTest {

    private static final ConversionSuggestionEngine ENGINE =
        new ConversionSuggestionEngine(new ConversionSuggestionExplanationBuilder());

    private static final String SIRI = "siri";
    private static final String BROZEK = "brozek";

    private static final List<ConversionCandidateInput> BOTH_CONVERSIONS = List.of(
        new ConversionCandidateInput(SIRI, "BODY_DENSITY", "BODY_FAT_PERCENTAGE"),
        new ConversionCandidateInput(BROZEK, "BODY_DENSITY", "BODY_FAT_PERCENTAGE"));

    // CONVERSION-SUGGESTION-001 (doc.md §27): Siri e Brozek elegíveis, sem preferência → Siri.
    @Test
    void ambasElegiveis_semPreferencia_sugereSiri() {
        ConversionSuggestionResult r = ENGINE.suggest("BODY_DENSITY", BOTH_CONVERSIONS,
            null, SIRI, Set.of(SIRI, BROZEK));
        assertEquals(ConversionSuggestionStatus.SUGGESTED, r.status());
        assertEquals(1, r.suggestedConversions().size());
        assertEquals(SIRI, r.suggestedConversions().getFirst().conversionId());
    }

    // CONVERSION-OVERRIDE-001: preferência profissional = Brozek → Brozek.
    @Test
    void preferenciaBrozek_sugereBrozek() {
        ConversionSuggestionResult r = ENGINE.suggest("BODY_DENSITY", BOTH_CONVERSIONS,
            BROZEK, SIRI, Set.of(SIRI, BROZEK));
        assertEquals(ConversionSuggestionStatus.SUGGESTED, r.status());
        assertEquals(BROZEK, r.suggestedConversions().getFirst().conversionId());
    }

    // CONVERSION-FALLBACK-001: Siri desabilitada, Brozek elegível → Brozek.
    @Test
    void siriDesabilitada_brozekElegivel_sugereBrozek() {
        ConversionSuggestionResult r = ENGINE.suggest("BODY_DENSITY", BOTH_CONVERSIONS,
            null, SIRI, Set.of(BROZEK));
        assertEquals(ConversionSuggestionStatus.SUGGESTED, r.status());
        assertEquals(BROZEK, r.suggestedConversions().getFirst().conversionId());
    }

    // Preferência desabilitada cai para o default (preferência só seleciona conversão elegível).
    @Test
    void preferenciaDesabilitada_caiParaDefault() {
        ConversionSuggestionResult r = ENGINE.suggest("BODY_DENSITY", BOTH_CONVERSIONS,
            BROZEK, SIRI, Set.of(SIRI));
        assertEquals(ConversionSuggestionStatus.SUGGESTED, r.status());
        assertEquals(SIRI, r.suggestedConversions().getFirst().conversionId());
    }

    // NO_CONVERSION_NEEDED: resultado já é %G direto (ex.: FALK4).
    @Test
    void resultadoJaPercentualG_noConversionNeeded() {
        ConversionSuggestionResult r = ENGINE.suggest("BODY_FAT_PERCENTAGE", BOTH_CONVERSIONS,
            null, SIRI, Set.of(SIRI, BROZEK));
        assertEquals(ConversionSuggestionStatus.NO_CONVERSION_NEEDED, r.status());
        assertTrue(r.suggestedConversions().isEmpty());
    }

    // Todas desabilitadas → NO_ENABLED_CONVERSION.
    @Test
    void todasDesabilitadas_noEnabledConversion() {
        ConversionSuggestionResult r = ENGINE.suggest("BODY_DENSITY", BOTH_CONVERSIONS,
            null, SIRI, Set.of());
        assertEquals(ConversionSuggestionStatus.NO_ENABLED_CONVERSION, r.status());
        assertTrue(r.suggestedConversions().isEmpty());
    }

    // Determinismo: mesma entrada, mesma saída (architecture.md §20).
    @Test
    void mesmaEntrada_mesmaSaida() {
        ConversionSuggestionResult r1 = ENGINE.suggest("BODY_DENSITY", BOTH_CONVERSIONS,
            null, SIRI, Set.of(SIRI, BROZEK));
        ConversionSuggestionResult r2 = ENGINE.suggest("BODY_DENSITY", BOTH_CONVERSIONS,
            null, SIRI, Set.of(SIRI, BROZEK));
        assertEquals(r1, r2);
    }
}