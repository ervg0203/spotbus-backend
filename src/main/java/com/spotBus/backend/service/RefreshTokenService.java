package com.spotBus.backend.service;

import com.spotBus.backend.security.UserType;

public interface RefreshTokenService {

    /**
     * Create and persist a refresh token for an arbitrary user.
     * @param userId id of the user
     * @param email user's email (used in JWT payload)
     * @param userType type of the user (PASSENGER, DRIVER, CONDUCTOR, ...)
     * @return the generated refresh token string
     */
    String createAndPersistRefreshToken(Long userId, String email, UserType userType);

    void validateRefreshToken(String token);

    String refreshAccessToken(String refreshToken);
}
