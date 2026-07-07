package com.spotBus.backend.repository;

import com.spotBus.backend.entity.PassengerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassengerRepository extends JpaRepository<PassengerEntity, Long> {

    Optional<PassengerEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}