package com.bioimpedance.persistence;

import com.bioimpedance.domain.audit.AuditSnapshot;
import com.bioimpedance.domain.contracts.AssessmentContext;
import com.bioimpedance.domain.contracts.AssessmentObjective;
import com.bioimpedance.domain.contracts.ClientProfile;
import com.bioimpedance.domain.contracts.Sex;
import com.bioimpedance.domain.contracts.TrainingLevel;
import com.bioimpedance.persistence.entity.AuditSnapshotEntity;
import com.bioimpedance.persistence.repository.AuditSnapshotRepository;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Teste unitário do AuditSnapshotStore (Chunk 3 — DEC-41).
 * <p>
 * Usa um fake in-memory do AuditSnapshotRepository (viável porque a
 * interface declara só 3 métodos): valida a serialização JSON
 * (record/enum/Instant) e o contrato append/read SEM contexto Spring
 * e SEM H2. O round-trip real de banco (CLOB) fica para o Chunk 5,
 * junto com o teste de contexto.
 */
class AuditSnapshotStoreTest {

    private final AuditSnapshotStore store = new AuditSnapshotStore(
        new InMemoryAuditSnapshotRepository(),
        JsonMapper.builder().addModule(new JavaTimeModule()).build());

    @Test
    void append_e_read_preservam_snapshot_integral() {
        AuditSnapshot snapshot = sample("audit-1", "assessment-1");

        store.append(snapshot);

        List<AuditSnapshot> read = store.readByAssessment("assessment-1");
        assertEquals(1, read.size());
        assertEquals(snapshot, read.getFirst()); // igualdade de record: todos os campos
    }

    @Test
    void append_sucessivos_geram_historico_ordenado() {
        store.append(sample("audit-1", "assessment-2"));
        store.append(sample("audit-2", "assessment-2"));

        List<AuditSnapshot> read = store.readByAssessment("assessment-2");
        assertEquals(2, read.size());
        assertEquals("audit-1", read.get(0).auditId());
        assertEquals("audit-2", read.get(1).auditId());
    }

    private AuditSnapshot sample(String auditId, String assessmentId) {
        return new AuditSnapshot(
            auditId,
            assessmentId,
            Instant.parse("2026-09-26T12:00:00Z"),
            new AssessmentContext(
                new ClientProfile(Sex.MALE, 44, Boolean.FALSE,
                    TrainingLevel.TRAINED, "musculação"),
                AssessmentObjective.HYPERTROPHY),
            Map.of("SKINFOLD_TRICEPS", 12.0, "SKINFOLD_SUBSCAPULAR", 14.0),
            List.of("JP7-M", "P-M7"),
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

    /** Fake in-memory: o AuditSnapshotRepository declara só 3 métodos. */
    private static final class InMemoryAuditSnapshotRepository implements AuditSnapshotRepository {

        private final Map<String, AuditSnapshotEntity> store = new LinkedHashMap<>();

        @Override
        public AuditSnapshotEntity save(AuditSnapshotEntity entity) {
            store.put(entity.getId(), entity);
            return entity;
        }

        @Override
        public Optional<AuditSnapshotEntity> findById(String id) {
            return Optional.ofNullable(store.get(id));
        }

        @Override
        public List<AuditSnapshotEntity> findByAssessmentIdOrderByCreatedAtAsc(String assessmentId) {
            return store.values().stream()
                .filter(e -> e.getAssessmentId().equals(assessmentId))
                .sorted(Comparator.comparing(AuditSnapshotEntity::getCreatedAt))
                .toList();
        }
    }
}