package com.bioimpedance.repository;

import com.bioimpedance.constants.AssessmentMethod;
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

    List<Assessment> findByUserIdAndClientIdOrderByDateDescCreatedAtDesc(String userId, String clientId);

    long countByUserIdAndDateBetween(String userId, LocalDate start, LocalDate end);

    List<Assessment> findTop5ByUserIdOrderByDateDescCreatedAtDesc(String userId);

    Optional<Assessment> findByIdAndUserId(String id, String userId);

    boolean existsByIdAndUserId(String id, String userId);

    @Query("""
            SELECT a FROM Assessment a
            WHERE a.userId = :userId
              AND (:clientId   IS NULL OR a.clientId = :clientId)
              AND (:method     IS NULL OR a.method   = :method)
              AND (:from       IS NULL OR a.date    >= :from)
              AND (:to         IS NULL OR a.date    <= :to)
            ORDER BY a.date DESC, a.createdAt DESC
            """)
    Page<Assessment> findPaged(
        @Param("userId")   String userId,
        @Param("clientId") String clientId,
        @Param("method")   AssessmentMethod method,
        @Param("from")     LocalDate from,
        @Param("to")       LocalDate to,
        Pageable pageable
    );
}