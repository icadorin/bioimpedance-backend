package com.bioimpedance.repository;

import com.bioimpedance.entity.BrandingProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandingRepository extends JpaRepository<BrandingProfile, String> {

    Optional<BrandingProfile> findByUserId(String userId);
}