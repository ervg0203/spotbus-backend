package com.spotBus.backend.service;

import com.spotBus.backend.dto.LoginRequestDTO;
import com.spotBus.backend.dto.RefreshTokenRequestDTO;
import com.spotBus.backend.dto.RegisterRequestDTO;
import com.spotBus.backend.response.AuthenticationResponseDTO;
import com.spotBus.backend.response.RefreshTokenResponseDTO;

public interface AuthenticationService {

    AuthenticationResponseDTO register(RegisterRequestDTO dto);

    AuthenticationResponseDTO login(LoginRequestDTO dto);

    RefreshTokenResponseDTO refreshToken(RefreshTokenRequestDTO dto);
}
