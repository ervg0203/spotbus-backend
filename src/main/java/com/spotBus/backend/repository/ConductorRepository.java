package com.spotBus.backend.repository;

import com.spotBus.backend.entity.ConductorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConductorRepository extends JpaRepository<ConductorEntity, Long> {

    Optional<ConductorEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}

