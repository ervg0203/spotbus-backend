package com.spotBus.backend.security;

public record AuthenticatedUser(Long userId, String email, UserType userType) {
}
