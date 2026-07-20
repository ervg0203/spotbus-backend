package com.spotBus.backend.repository;

import com.spotBus.backend.entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<DriverEntity, Long> {

    Optional<DriverEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}

