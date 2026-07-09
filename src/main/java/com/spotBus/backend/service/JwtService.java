package com.spotBus.backend.service;

import io.jsonwebtoken.Claims;

public interface JwtService {

    String generateAccessToken(Long userId, String email);

    String generateRefreshToken(Long userId, String email);

    void validateToken(String token);

    boolean isTokenExpired(String token);

    Claims extractClaims(String token);

    String extractEmail(String token);

    Long extractUserId(String token);
}
