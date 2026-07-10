package com.spotBus.backend.service.impl;

import com.spotBus.backend.entity.PassengerEntity;
import com.spotBus.backend.entity.RefreshTokenEntity;
import com.spotBus.backend.exception.ExpiredTokenException;
import com.spotBus.backend.exception.InvalidTokenException;
import com.spotBus.backend.repository.RefreshTokenRepository;
import com.spotBus.backend.service.JwtService;
import com.spotBus.backend.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final long refreshTokenExpiration;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository,
                                   JwtService jwtService,
                                   @Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    @Override
    public String createAndPersistRefreshToken(PassengerEntity passenger) {
        refreshTokenRepository.deleteByPassengerId(passenger.getId());

        String token = jwtService.generateRefreshToken(passenger.getId(), passenger.getEmail());

        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setToken(token);
        refreshToken.setPassenger(passenger);
        refreshToken.setExpiresAt(LocalDateTime.now().plus(Duration.ofMillis(refreshTokenExpiration)));
        refreshToken.setRevoked(false);
        refreshToken.setCreatedAt(LocalDateTime.now());
        refreshToken.setUpdatedAt(LocalDateTime.now());

        refreshTokenRepository.save(refreshToken);

        return token;
    }

    @Override
    public void validateRefreshToken(String token) {
        jwtService.validateToken(token);

        RefreshTokenEntity storedToken = refreshTokenRepository.findByTokenAndRevokedFalse(token)
                .orElseThrow(() -> new InvalidTokenException("Refresh token not found"));

        if (storedToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ExpiredTokenException("Refresh token has expired");
        }
    }

    @Override
    public String refreshAccessToken(String refreshToken) {
        validateRefreshToken(refreshToken);

        Long userId = jwtService.extractUserId(refreshToken);
        String email = jwtService.extractEmail(refreshToken);

        return jwtService.generateAccessToken(userId, email);
    }
}
