package com.bioimpedance.domain.audit;

import com.bioimpedance.domain.contracts.AssessmentContext;
import com.bioimpedance.domain.contracts.AssessmentObjective;
import com.bioimpedance.domain.contracts.ClientProfile;
import com.bioimpedance.domain.contracts.Sex;
import com.bioimpedance.domain.contracts.TrainingLevel;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuditGoldenTest {

    private static AssessmentContext sampleContext() {
        ClientProfile client = new ClientProfile(
            Sex.MALE,
            44,
            Boolean.FALSE,           // athlete (Boolean wrapper)
            TrainingLevel.TRAINED,   // trainingLevel
            "musculação"             // modality
        );

        return new AssessmentContext(client, AssessmentObjective.HYPERTROPHY);
    }

    // Cenário com conversão: JP7-M → BODY_DENSITY → Siri → BODY_FAT_PERCENTAGE
    private static AuditSnapshot snapshotWithConversion() {
        return new AuditSnapshot(
            "audit-001",
            "assessment-001",
            Instant.parse("2026-09-25T00:00:00Z"),
            sampleContext(),
            Map.of("SKINFOLD_CHEST", 10.0, "SKINFOLD_ABDOMEN", 15.0,
                "SKINFOLD_TRICEPS", 12.0, "SKINFOLD_SUBSCAPULAR", 14.0,
                "SKINFOLD_SUPRAILIAC", 16.0, "SKINFOLD_AXILLARY_MID", 11.0,
                "SKINFOLD_THIGH", 13.0),
            List.of("JP7-M", "P-M7", "G-M7"),
            "SUGGESTED",
            "JP7-M",
            "JP7-M",
            false,
            null,
            "JP7-M",
            "1",
            "BODY_DENSITY",
            1.06518908,
            "siri",
            "1",
            "BODY_FAT_PERCENTAGE",
            14.7062,
            "siri",
            "siri",
            "suggestion-engine-v1",
            "conversion-suggestion-engine-v1",
            "scientific-rules-v1",
            "evidence-v1",
            "config-v1"
        );
    }

    // Cenário sem conversão: FALK4 produz BODY_FAT_PERCENTAGE direto
    private static AuditSnapshot snapshotWithoutConversion() {
        return new AuditSnapshot(
            "audit-002",
            "assessment-002",
            Instant.parse("2026-09-25T00:00:00Z"),
            sampleContext(),
            Map.of("SKINFOLD_TRICEPS", 12.0, "SKINFOLD_SUBSCAPULAR", 15.0,
                "SKINFOLD_SUPRAILIAC", 18.0, "SKINFOLD_ABDOMEN", 20.0),
            List.of("FALK4"),
            "SUGGESTED",
            "FALK4",
            "FALK4",
            false,
            null,
            "FALK4",
            "1",
            "BODY_FAT_PERCENTAGE",
            8.384,
            null, null, null, null, null, null,
            "suggestion-engine-v1",
            null,
            "scientific-rules-v1",
            "evidence-v1",
            "config-v1"
        );
    }

    @Test
    void snapshotWithConversion_preservesAllFields() {
        AuditSnapshot snapshot = snapshotWithConversion();
        assertEquals("audit-001", snapshot.auditId());
        assertEquals("assessment-001", snapshot.assessmentId());
        assertNotNull(snapshot.timestamp());
        assertNotNull(snapshot.context());
        assertEquals(Sex.MALE, snapshot.context().client().sex());
        assertEquals("JP7-M", snapshot.suggestedVariantId());
        assertEquals("JP7-M", snapshot.equationVariantId());
        assertEquals(1.06518908, snapshot.predictionValue(), 1e-9);
        assertEquals("siri", snapshot.conversionId());
        assertEquals(14.7062, snapshot.conversionValue(), 1e-9);
    }

    @Test
    void snapshotWithoutConversion_conversionFieldsNull() {
        AuditSnapshot snapshot = snapshotWithoutConversion();
        assertNull(snapshot.conversionId());
        assertNull(snapshot.conversionVersion());
        assertNull(snapshot.conversionValue());
        assertEquals("FALK4", snapshot.equationVariantId());
        assertEquals("BODY_FAT_PERCENTAGE", snapshot.predictionOutputType());
        assertEquals(8.384, snapshot.predictionValue(), 1e-9);
    }

    @Test
    void inputsUsed_immutable() {
        AuditSnapshot snapshot = snapshotWithConversion();
        assertThrows(UnsupportedOperationException.class,
            () -> snapshot.inputsUsed().put("NOVO", 1.0));
    }

    @Test
    void candidateVariantIds_immutable() {
        AuditSnapshot snapshot = snapshotWithConversion();
        assertThrows(UnsupportedOperationException.class,
            () -> snapshot.candidateVariantIds().add("NOVO"));
    }

    @Test
    void override_recordsDifferentProfessionalChoice() {
        AuditSnapshot original = snapshotWithConversion();
        AuditSnapshot overridden = new AuditSnapshot(
            original.auditId(),
            original.assessmentId(),
            original.timestamp(),
            original.context(),
            original.inputsUsed(),
            original.candidateVariantIds(),
            original.suggestionStatus(),
            "JP7-M",       // sugerido pelo sistema
            "P-M7",        // escolhido pelo profissional
            true,
            "Preferência profissional",
            "P-M7",
            "1",
            original.predictionOutputType(),
            original.predictionValue(),
            original.conversionId(),
            original.conversionVersion(),
            original.conversionOutputType(),
            original.conversionValue(),
            original.suggestedConversionId(),
            original.selectedConversionId(),
            original.suggestionEngineVersion(),
            original.conversionSuggestionEngineVersion(),
            original.scientificRulesVersion(),
            original.evidenceVersion(),
            original.configurationVersion()
        );
        assertTrue(overridden.override());
        assertEquals("JP7-M", overridden.suggestedVariantId());
        assertEquals("P-M7", overridden.selectedVariantId());
        assertEquals("Preferência profissional", overridden.overrideReason());
    }

    @Test
    void withoutOverride_flagFalseReasonNull() {
        AuditSnapshot snapshot = snapshotWithConversion();
        assertFalse(snapshot.override());
        assertNull(snapshot.overrideReason());
    }

    @Test
    void versions_preservedForReproduction() {
        AuditSnapshot snapshot = snapshotWithConversion();
        assertEquals("suggestion-engine-v1", snapshot.suggestionEngineVersion());
        assertEquals("conversion-suggestion-engine-v1", snapshot.conversionSuggestionEngineVersion());
        assertEquals("scientific-rules-v1", snapshot.scientificRulesVersion());
        assertEquals("evidence-v1", snapshot.evidenceVersion());
        assertEquals("config-v1", snapshot.configurationVersion());
    }

    @Test
    void context_fullyPreserved() {
        AuditSnapshot snapshot = snapshotWithConversion();
        AssessmentContext ctx = snapshot.context();

        // Navegando pelo client()
        assertEquals(Sex.MALE, ctx.client().sex());
        assertEquals(44, ctx.client().age());
        assertEquals(TrainingLevel.TRAINED, ctx.client().trainingLevel());
        assertFalse(ctx.client().athlete());
        assertEquals("musculação", ctx.client().modality());

        // O objetivo fica direto no AssessmentContext
        assertEquals(AssessmentObjective.HYPERTROPHY, ctx.objective());
    }
}