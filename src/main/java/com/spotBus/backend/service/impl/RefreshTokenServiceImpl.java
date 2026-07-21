package com.spotBus.backend.service.impl;

import com.spotBus.backend.entity.RefreshTokenEntity;
import com.spotBus.backend.exception.ExpiredTokenException;
import com.spotBus.backend.exception.InvalidTokenException;
import com.spotBus.backend.repository.RefreshTokenRepository;
import com.spotBus.backend.security.UserType;
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
    public String createAndPersistRefreshToken(Long userId, String email, UserType userType) {
        // remove any existing refresh tokens for this user/type
        refreshTokenRepository.deleteByUserIdAndUserType(userId, userType);

        String token = jwtService.generateRefreshToken(userId, email);

        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setToken(token);
        refreshToken.setUserId(userId);
        refreshToken.setUserType(userType);
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
                .orElseThrow(() -> new InvalidTokenException("Invalid refresh token"));

        if (storedToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ExpiredTokenException("Refresh token has expired");
        }
    }

    @Override
    public String refreshAccessToken(String refreshToken) {
        validateRefreshToken(refreshToken);

        RefreshTokenEntity storedToken = refreshTokenRepository.findByTokenAndRevokedFalse(refreshToken)
                .orElseThrow(() -> new InvalidTokenException("Invalid refresh token"));

        Long userId = jwtService.extractUserId(refreshToken);
        String email = jwtService.extractEmail(refreshToken);

        return jwtService.generateAccessToken(userId, email, storedToken.getUserType());
    }
}
