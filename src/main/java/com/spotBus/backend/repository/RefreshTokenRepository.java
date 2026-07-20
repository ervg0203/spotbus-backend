package com.spotBus.backend.repository;

import com.spotBus.backend.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByTokenAndRevokedFalse(String token);

    // Delete refresh tokens for a specific user id + user type
    void deleteByUserIdAndUserType(Long userId, com.spotBus.backend.security.UserType userType);
}
