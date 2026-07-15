package com.spotBus.backend.controller;

import com.spotBus.backend.response.AuthenticationResponseDTO;
import com.spotBus.backend.dto.LoginRequestDTO;
import com.spotBus.backend.dto.RefreshTokenRequestDTO;
import com.spotBus.backend.dto.RegisterRequestDTO;
import com.spotBus.backend.response.ApiResponseDTO;
import com.spotBus.backend.response.RefreshTokenResponseDTO;
import com.spotBus.backend.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<AuthenticationResponseDTO>> register(
            @Valid @RequestBody RegisterRequestDTO dto) {

        AuthenticationResponseDTO authResponse = authenticationService.register(dto);

        ApiResponseDTO<AuthenticationResponseDTO> response =
                new ApiResponseDTO<>(true, "Registration successful", authResponse);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<AuthenticationResponseDTO>> login(
            @Valid @RequestBody LoginRequestDTO dto) {

        AuthenticationResponseDTO authResponse = authenticationService.login(dto);

        ApiResponseDTO<AuthenticationResponseDTO> response =
                new ApiResponseDTO<>(true, "Login successful", authResponse);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponseDTO<RefreshTokenResponseDTO>> refresh(
            @Valid @RequestBody RefreshTokenRequestDTO dto) {

        RefreshTokenResponseDTO refreshResponse = authenticationService.refreshToken(dto);

        ApiResponseDTO<RefreshTokenResponseDTO> response =
                new ApiResponseDTO<>(true, "Token refreshed successfully", refreshResponse);

        return ResponseEntity.ok(response);
    }
}
