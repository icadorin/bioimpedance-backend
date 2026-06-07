package com.bioimpedance.repository;

import com.bioimpedance.entity.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
