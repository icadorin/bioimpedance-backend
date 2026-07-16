package com.bioimpedance.repository;

import com.bioimpedance.entity.Client;
import com.bioimpedance.constants.ClientStatus;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    List<Client> findByUserIdOrderByCreatedAtDesc(String userId);

    Optional<Client> findByIdAndUserId(String id, String userId);

    long countByUserId(String userId);

    long countByUserIdAndStatus(String userId, ClientStatus status);

    boolean existsByEmailAndUserId(String email, String userId);

    boolean existsByEmailAndUserIdAndIdNot(String email, String userId, String id);

    boolean existsByIdAndUserId(String id, String userId);

    @Override
    List<Client> findAllById(@NonNull Iterable<String> ids);

    List<Client> findTop10ByUserIdAndNameContainingIgnoreCaseOrderByNameAsc(
        String userId,
        String name
    );

    @Query("""
        SELECT c FROM Client c
        WHERE c.userId = :userId
          AND (:search IS NULL OR
               LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%')) OR
               LOWER(c.email) LIKE LOWER(CONCAT('%', :search, '%')))
          AND (:status IS NULL OR c.status = :status)
        """)
    Page<Client> findPaged(
        @Param("userId") String userId,
        @Param("search") String search,
        @Param("status") ClientStatus status,
        Pageable pageable
    );
}
