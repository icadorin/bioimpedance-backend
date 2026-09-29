package com.bioimpedance.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Persistência append-only do AuditSnapshot (DEC-29/DEC-30/DEC-34).
 * <p>
 * Fonte: architecture.md §6 (somente create/read) + §22 (reprodução) +
 * doc.md §3 (regra de auditoria).
 * <p>
 * O conteúdo viaja como JSON (payload): o schema pode evoluir sem
 * migration e o histórico nunca é reescrito. As colunas indexadas
 * permitem consulta sem parsear o JSON.
 * <p>
 * Entidade imutável: sem setters — insert only.
 */
@Entity
@Table(
    name = "audit_snapshots",
    indexes = {
        @Index(name = "idx_audit_snapshots_assessment", columnList = "assessment_id"),
        @Index(name = "idx_audit_snapshots_variant", columnList = "equation_variant_id")
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuditSnapshotEntity {

    @Id
    @Column(length = 64)
    private String id;

    @Column(name = "assessment_id", nullable = false, length = 64)
    private String assessmentId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "equation_variant_id", length = 64)
    private String equationVariantId;

    @Column(name = "conversion_id", length = 64)
    private String conversionId;

    @Column(name = "payload", nullable = false, columnDefinition = "TEXT")
    private String payload;

    public AuditSnapshotEntity(String id,
        String assessmentId,
        Instant createdAt,
        String equationVariantId,
        String conversionId,
        String payload) {
        this.id = id;
        this.assessmentId = assessmentId;
        this.createdAt = createdAt;
        this.equationVariantId = equationVariantId;
        this.conversionId = conversionId;
        this.payload = payload;
    }
}