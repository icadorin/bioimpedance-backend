package com.bioimpedance.repository;

import com.bioimpedance.entity.Client;
import com.bioimpedance.constants.ClientStatus;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
