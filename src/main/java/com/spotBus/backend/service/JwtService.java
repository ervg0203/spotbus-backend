package com.spotBus.backend.service;

import com.spotBus.backend.security.UserType;
import io.jsonwebtoken.Claims;

public interface JwtService {

    String generateAccessToken(Long userId, String email, UserType userType);

    String generateRefreshToken(Long userId, String email);

    void validateToken(String token);

    boolean isTokenExpired(String token);

    Claims extractClaims(String token);

    String extractEmail(String token);

    Long extractUserId(String token);

    UserType extractUserType(String token);
}
