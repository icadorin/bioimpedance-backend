package com.bioimpedance.repository;

import com.bioimpedance.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByTokenHash(String tokenHash);

    @Transactional
    void deleteByUserId(String userId);

    /**
     * Limpeza performática: deleta tokens usados (used=true)
     * criados há mais de X dias, sem carregar as entidades na memória.
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM RefreshToken r WHERE r.used = true AND r.createdAt < :cutoffDate")
    int deleteUsedTokensOlderThan(@Param("cutoffDate") Instant cutoffDate);
}