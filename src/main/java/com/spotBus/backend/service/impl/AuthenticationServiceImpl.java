package com.spotBus.backend.service.impl;

import com.spotBus.backend.dto.LoginRequestDTO;
import com.spotBus.backend.dto.RefreshTokenRequestDTO;
import com.spotBus.backend.dto.RegisterRequestDTO;
import com.spotBus.backend.response.AuthenticationResponseDTO;
import com.spotBus.backend.response.RefreshTokenResponseDTO;
import com.spotBus.backend.security.AuthenticationProviderRegistry;
import com.spotBus.backend.security.UserType;
import com.spotBus.backend.exception.UnsupportedUserTypeException;
import com.spotBus.backend.service.AuthenticationService;
import com.spotBus.backend.service.RefreshTokenService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationProviderRegistry providerRegistry;
    private final RefreshTokenService refreshTokenService;

    public AuthenticationServiceImpl(AuthenticationProviderRegistry providerRegistry,
                                     RefreshTokenService refreshTokenService) {
        this.providerRegistry = providerRegistry;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public AuthenticationResponseDTO register(RegisterRequestDTO dto) {
        UserType userType = resolveUserType(dto.getUserType());
        if (userType != UserType.PASSENGER) {
            throw new UnsupportedUserTypeException("Only passenger self-registration is allowed");
        }
        return providerRegistry.getProvider(userType).register(dto);
    }

    @Override
    public AuthenticationResponseDTO login(LoginRequestDTO dto) {
        UserType userType = resolveUserType(dto.getUserType());
        return providerRegistry.getProvider(userType).login(dto);
    }

    @Override
    public RefreshTokenResponseDTO refreshToken(RefreshTokenRequestDTO dto) {
        String accessToken = refreshTokenService.refreshAccessToken(dto.getRefreshToken());
        return new RefreshTokenResponseDTO(accessToken);
    }

    private UserType resolveUserType(UserType userType) {
        return userType != null ? userType : UserType.PASSENGER;
    }
}
