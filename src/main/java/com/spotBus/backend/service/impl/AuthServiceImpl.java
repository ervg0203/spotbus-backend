package com.spotBus.backend.service.impl;

import com.spotBus.backend.response.AuthenticationResponseDTO;
import com.spotBus.backend.dto.LoginRequestDTO;
import com.spotBus.backend.dto.RefreshTokenRequestDTO;
import com.spotBus.backend.dto.RegisterRequestDTO;
import com.spotBus.backend.entity.PassengerEntity;
import com.spotBus.backend.exception.EmailAlreadyExistsException;
import com.spotBus.backend.exception.InvalidCredentialsException;
import com.spotBus.backend.repository.PassengerRepository;
import com.spotBus.backend.response.RefreshTokenResponseDTO;
import com.spotBus.backend.service.AuthService;
import com.spotBus.backend.service.JwtService;
import com.spotBus.backend.service.RefreshTokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    private final PassengerRepository passengerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthServiceImpl(PassengerRepository passengerRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService,
                           RefreshTokenService refreshTokenService) {
        this.passengerRepository = passengerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public AuthenticationResponseDTO register(RegisterRequestDTO dto) {
        if (passengerRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException("Email is already registered");
        }

        PassengerEntity passenger = new PassengerEntity();
        passenger.setName(dto.getName());
        passenger.setEmail(dto.getEmail());
        passenger.setPhone(dto.getPhone());
        passenger.setPassword(passwordEncoder.encode(dto.getPassword()));
        passenger.setCreatedAt(LocalDateTime.now());
        passenger.setUpdatedAt(LocalDateTime.now());

        PassengerEntity savedPassenger = passengerRepository.save(passenger);

        return toAuthenticationResponse(savedPassenger);
    }

    @Override
    public AuthenticationResponseDTO login(LoginRequestDTO dto) {
        PassengerEntity passenger = passengerRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(dto.getPassword(), passenger.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return toAuthenticationResponse(passenger);
    }

    @Override
    public RefreshTokenResponseDTO refreshToken(RefreshTokenRequestDTO dto) {
        String accessToken = refreshTokenService.refreshAccessToken(dto.getRefreshToken());
        return new RefreshTokenResponseDTO(accessToken);
    }

    private AuthenticationResponseDTO toAuthenticationResponse(PassengerEntity passenger) {
        return new AuthenticationResponseDTO(
                jwtService.generateAccessToken(passenger.getId(), passenger.getEmail()),
                refreshTokenService.createAndPersistRefreshToken(passenger),
                passenger.getId(),
                passenger.getEmail(),
                passenger.getName()
        );
    }
}
