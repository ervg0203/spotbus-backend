package com.spotBus.backend.security;

import com.spotBus.backend.dto.LoginRequestDTO;
import com.spotBus.backend.dto.RegisterRequestDTO;
import com.spotBus.backend.entity.ConductorEntity;
import com.spotBus.backend.exception.EmailAlreadyExistsException;
import com.spotBus.backend.exception.InvalidCredentialsException;
import com.spotBus.backend.repository.ConductorRepository;
import com.spotBus.backend.response.AuthenticationResponseDTO;
import com.spotBus.backend.service.JwtService;
import com.spotBus.backend.service.RefreshTokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ConductorAuthenticationProvider implements UserAuthenticationProvider {

    private final ConductorRepository conductorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public ConductorAuthenticationProvider(ConductorRepository conductorRepository,
                                           PasswordEncoder passwordEncoder,
                                           JwtService jwtService,
                                           RefreshTokenService refreshTokenService) {
        this.conductorRepository = conductorRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public UserType getUserType() {
        return UserType.CONDUCTOR;
    }

    @Override
    public AuthenticationResponseDTO register(RegisterRequestDTO dto) {
        if (conductorRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException("Email is already registered");
        }

        ConductorEntity conductorEntity = new ConductorEntity();
        conductorEntity.setName(dto.getName());
        conductorEntity.setEmail(dto.getEmail());
        conductorEntity.setPhone(dto.getPhone());
        conductorEntity.setPassword(passwordEncoder.encode(dto.getPassword()));
        conductorEntity.setCreatedAt(LocalDateTime.now());
        conductorEntity.setUpdatedAt(LocalDateTime.now());

        ConductorEntity savedConductorEntity = conductorRepository.save(conductorEntity);

        return buildAuthenticationResponse(savedConductorEntity);
    }

    @Override
    public AuthenticationResponseDTO login(LoginRequestDTO dto) {
        ConductorEntity conductorEntity = conductorRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!verifyPassword(dto.getPassword(), conductorEntity.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return buildAuthenticationResponse(conductorEntity);
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public String generateAccessToken(ConductorEntity conductorEntity) {
        return jwtService.generateAccessToken(conductorEntity.getId(), conductorEntity.getEmail(), UserType.CONDUCTOR);
    }

    public String generateRefreshToken(ConductorEntity conductorEntity) {
        return refreshTokenService.createAndPersistRefreshToken(conductorEntity.getId(), conductorEntity.getEmail(), UserType.CONDUCTOR);
    }

    private AuthenticationResponseDTO buildAuthenticationResponse(ConductorEntity conductorEntity) {
        return new AuthenticationResponseDTO(
                generateAccessToken(conductorEntity),
                generateRefreshToken(conductorEntity),
                conductorEntity.getId(),
                conductorEntity.getEmail(),
                conductorEntity.getName()
        );
    }
}

