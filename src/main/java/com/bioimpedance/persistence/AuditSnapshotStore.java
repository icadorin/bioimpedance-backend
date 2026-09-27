package com.bioimpedance.persistence;

import com.bioimpedance.domain.audit.AuditSnapshot;
import com.bioimpedance.persistence.entity.AuditSnapshotEntity;
import com.bioimpedance.persistence.repository.AuditSnapshotRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Fachada de persistência da auditoria: append + read (DEC-29/DEC-34).
 * <p>
 * A serialização domain ↔ JSON ↔ entity vive aqui para que o
 * AuditSnapshot (domain/audit) nunca conheça JPA
 * (architecture.md §1.1: domain/* → NÃO → persistence/*).
 * <p>
 * DEC-37: entity e repository da auditoria vivem em
 * persistence/entity + persistence/repository (pacotes novos da Fase 12),
 * separados dos pacotes legacy entity/ + repository/ do protótipo.
 */
@Component
public class AuditSnapshotStore {

    private final AuditSnapshotRepository repository;
    private final ObjectMapper objectMapper;

    public AuditSnapshotStore(AuditSnapshotRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    /** create() — única operação de escrita permitida (architecture.md §6). */
    @Transactional
    public AuditSnapshotEntity append(AuditSnapshot snapshot) {
        AuditSnapshotEntity entity = new AuditSnapshotEntity(
            snapshot.auditId(),
            snapshot.assessmentId(),
            snapshot.timestamp(),
            snapshot.equationVariantId(),
            snapshot.conversionId(),
            write(snapshot)
        );
        return repository.save(entity);
    }

    /** read() — reconstrói os snapshots históricos na ordem de criação. */
    @Transactional(readOnly = true)
    public List<AuditSnapshot> readByAssessment(String assessmentId) {
        return repository.findByAssessmentIdOrderByCreatedAtAsc(assessmentId)
            .stream()
            .map(this::read)
            .toList();
    }

    private String write(AuditSnapshot snapshot) {
        try {
            return objectMapper.writeValueAsString(snapshot);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(
                "Falha ao serializar AuditSnapshot: " + snapshot.auditId(), e);
        }
    }

    private AuditSnapshot read(AuditSnapshotEntity entity) {
        try {
            return objectMapper.readValue(entity.getPayload(), AuditSnapshot.class);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(
                "Falha ao desserializar AuditSnapshot: " + entity.getId(), e);
        }
    }
}