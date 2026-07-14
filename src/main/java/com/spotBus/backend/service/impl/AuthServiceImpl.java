package com.spotBus.backend.service.impl;

import com.spotBus.backend.response.AuthenticationResponseDTO;
import com.spotBus.backend.dto.LoginRequestDTO;
import com.spotBus.backend.dto.RefreshTokenRequestDTO;
import com.spotBus.backend.dto.RegisterRequestDTO;
import com.spotBus.backend.response.RefreshTokenResponseDTO;
import com.spotBus.backend.security.PassengerAuthenticationProvider;
import com.spotBus.backend.service.AuthService;
import com.spotBus.backend.service.RefreshTokenService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final PassengerAuthenticationProvider passengerAuthenticationProvider;
    private final RefreshTokenService refreshTokenService;

    public AuthServiceImpl(PassengerAuthenticationProvider passengerAuthenticationProvider,
                           RefreshTokenService refreshTokenService) {
        this.passengerAuthenticationProvider = passengerAuthenticationProvider;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public AuthenticationResponseDTO register(RegisterRequestDTO dto) {
        return passengerAuthenticationProvider.register(dto);
    }

    @Override
    public AuthenticationResponseDTO login(LoginRequestDTO dto) {
        return passengerAuthenticationProvider.login(dto);
    }

    @Override
    public RefreshTokenResponseDTO refreshToken(RefreshTokenRequestDTO dto) {
        String accessToken = refreshTokenService.refreshAccessToken(dto.getRefreshToken());
        return new RefreshTokenResponseDTO(accessToken);
    }
}
