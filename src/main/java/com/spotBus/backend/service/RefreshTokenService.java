package com.spotBus.backend.service;

import com.spotBus.backend.entity.PassengerEntity;

public interface RefreshTokenService {

    String createAndPersistRefreshToken(PassengerEntity passenger);

    void validateRefreshToken(String token);

    String refreshAccessToken(String refreshToken);
}
