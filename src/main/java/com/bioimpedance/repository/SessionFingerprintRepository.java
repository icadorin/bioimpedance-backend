package com.bioimpedance.repository;

import com.bioimpedance.entity.SessionFingerprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Repository
public interface SessionFingerprintRepository extends JpaRepository<SessionFingerprint, String> {

    @Query("""
    SELECT s
    FROM SessionFingerprint s
    WHERE s.userId = :userId
      AND s.tokenFamily = :tokenFamily
    ORDER BY s.lastUsedAt DESC
    """)
    List<SessionFingerprint> findByUserIdAndTokenFamily(
        @Param("userId") String userId,
        @Param("tokenFamily") String tokenFamily
    );

    List<SessionFingerprint> findByUserIdOrderByLastUsedAtDesc(String userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM SessionFingerprint s WHERE s.lastUsedAt < :cutoffDate")
    int deleteByLastUsedAtBefore(@Param("cutoffDate") Instant cutoffDate);
}