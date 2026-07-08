package com.spotBus.backend.service;

import com.spotBus.backend.response.AuthenticationResponseDTO;
import com.spotBus.backend.dto.LoginRequestDTO;
import com.spotBus.backend.dto.RegisterRequestDTO;

public interface AuthService {

    AuthenticationResponseDTO register(RegisterRequestDTO dto);

    AuthenticationResponseDTO login(LoginRequestDTO dto);
}
