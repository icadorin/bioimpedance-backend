package com.bioimpedance.repository;

import com.bioimpedance.constants.AssessmentStatus;
import com.bioimpedance.entity.Assessment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, String> {

    /** DEC-61: leitores legados enxergam apenas avaliações FINALIZED. */
    List<Assessment> findByUserIdAndClientIdAndStatusOrderByDateDescCreatedAtDesc(
        String userId, String clientId, AssessmentStatus status);

    long countByUserIdAndStatusAndDateBetween(
        String userId, AssessmentStatus status, LocalDate start, LocalDate end);

    List<Assessment> findTop10ByUserIdAndStatusOrderByDateDescCreatedAtDesc(
        String userId, AssessmentStatus status);

    Optional<Assessment> findByIdAndUserId(String id, String userId);

    boolean existsByIdAndUserId(String id, String userId);

    @Query("""
            SELECT a FROM Assessment a
            WHERE a.userId = :userId
            AND a.status = :status
            AND (:clientId IS NULL OR a.clientId = :clientId)
            AND (:from     IS NULL OR a.date    >= :from)
            AND (:to       IS NULL OR a.date    <= :to)
            """)
    Page<Assessment> findPaged(
        @Param("userId")   String userId,
        @Param("status")   AssessmentStatus status,
        @Param("clientId") String clientId,
        @Param("from")     LocalDate from,
        @Param("to")       LocalDate to,
        Pageable pageable
    );
}