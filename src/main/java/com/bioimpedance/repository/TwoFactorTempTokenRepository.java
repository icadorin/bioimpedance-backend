package com.bioimpedance.repository;

import com.bioimpedance.entity.TwoFactorTempToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface TwoFactorTempTokenRepository extends JpaRepository<TwoFactorTempToken, String> {

    Optional<TwoFactorTempToken> findByTokenHash(String tokenHash);

    @Transactional
    void deleteByUserId(String userId);
}