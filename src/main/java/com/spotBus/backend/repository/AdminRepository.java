package com.spotBus.backend.repository;

import com.spotBus.backend.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {

    Optional<AdminEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
