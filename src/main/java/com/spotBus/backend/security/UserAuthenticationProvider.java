package com.spotBus.backend.security;

import com.spotBus.backend.dto.LoginRequestDTO;
import com.spotBus.backend.dto.RegisterRequestDTO;
import com.spotBus.backend.response.AuthenticationResponseDTO;

public interface UserAuthenticationProvider {

    UserType getUserType();

    AuthenticationResponseDTO register(RegisterRequestDTO dto);

    AuthenticationResponseDTO login(LoginRequestDTO dto);
}
