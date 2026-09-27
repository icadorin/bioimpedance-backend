package com.bioimpedance.persistence.repository;

import com.bioimpedance.persistence.entity.AuditSnapshotEntity;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Ponto de acesso append-only da auditoria (architecture.md §6).
 * <p>
 * Estende {@link Repository} de propósito (não JpaRepository):
 * expõe SOMENTE create (save) e read. Não existe update/delete
 * no contrato — a única forma de "mudar" auditoria é inserir
 * uma nova execução (doc.md §3).
 */
public interface AuditSnapshotRepository extends Repository<AuditSnapshotEntity, String> {

    AuditSnapshotEntity save(AuditSnapshotEntity entity);

    Optional<AuditSnapshotEntity> findById(String id);

    List<AuditSnapshotEntity> findByAssessmentIdOrderByCreatedAtAsc(String assessmentId);
}