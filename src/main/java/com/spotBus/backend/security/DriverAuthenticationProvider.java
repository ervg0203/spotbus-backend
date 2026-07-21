package com.spotBus.backend.security;

import com.spotBus.backend.dto.LoginRequestDTO;
import com.spotBus.backend.dto.RegisterRequestDTO;
import com.spotBus.backend.entity.DriverEntity;
import com.spotBus.backend.exception.EmailAlreadyExistsException;
import com.spotBus.backend.exception.InvalidCredentialsException;
import com.spotBus.backend.repository.DriverRepository;
import com.spotBus.backend.response.AuthenticationResponseDTO;
import com.spotBus.backend.service.JwtService;
import com.spotBus.backend.service.RefreshTokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DriverAuthenticationProvider implements UserAuthenticationProvider {

    private final DriverRepository driverRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public DriverAuthenticationProvider(DriverRepository driverRepository,
                                        PasswordEncoder passwordEncoder,
                                        JwtService jwtService,
                                        RefreshTokenService refreshTokenService) {
        this.driverRepository = driverRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public UserType getUserType() {
        return UserType.DRIVER;
    }

    @Override
    public AuthenticationResponseDTO register(RegisterRequestDTO dto) {
        if (driverRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException("Email is already registered");
        }

        DriverEntity driverEntity = new DriverEntity();
        driverEntity.setName(dto.getName());
        driverEntity.setEmail(dto.getEmail());
        driverEntity.setPhone(dto.getPhone());
        driverEntity.setPassword(passwordEncoder.encode(dto.getPassword()));
        driverEntity.setCreatedAt(LocalDateTime.now());
        driverEntity.setUpdatedAt(LocalDateTime.now());

        DriverEntity savedDriverEntity = driverRepository.save(driverEntity);

        return buildAuthenticationResponse(savedDriverEntity);
    }

    @Override
    public AuthenticationResponseDTO login(LoginRequestDTO dto) {
        DriverEntity driverEntity = driverRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!verifyPassword(dto.getPassword(), driverEntity.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return buildAuthenticationResponse(driverEntity);
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public String generateAccessToken(DriverEntity driverEntity) {
        return jwtService.generateAccessToken(driverEntity.getId(), driverEntity.getEmail(), UserType.DRIVER);
    }

    public String generateRefreshToken(DriverEntity driverEntity) {
        return refreshTokenService.createAndPersistRefreshToken(driverEntity.getId(), driverEntity.getEmail(), UserType.DRIVER);
    }

    private AuthenticationResponseDTO buildAuthenticationResponse(DriverEntity driverEntity) {
        return new AuthenticationResponseDTO(
                generateAccessToken(driverEntity),
                generateRefreshToken(driverEntity),
                driverEntity.getId(),
                driverEntity.getEmail(),
                driverEntity.getName()
        );
    }
}

