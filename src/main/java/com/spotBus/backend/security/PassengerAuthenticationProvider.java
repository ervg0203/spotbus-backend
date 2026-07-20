package com.spotBus.backend.security;

import com.spotBus.backend.dto.LoginRequestDTO;
import com.spotBus.backend.dto.RegisterRequestDTO;
import com.spotBus.backend.entity.PassengerEntity;
import com.spotBus.backend.exception.EmailAlreadyExistsException;
import com.spotBus.backend.exception.InvalidCredentialsException;
import com.spotBus.backend.repository.PassengerRepository;
import com.spotBus.backend.response.AuthenticationResponseDTO;
import com.spotBus.backend.service.JwtService;
import com.spotBus.backend.service.RefreshTokenService;
import com.spotBus.backend.security.UserType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PassengerAuthenticationProvider implements UserAuthenticationProvider {

    private final PassengerRepository passengerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public PassengerAuthenticationProvider(PassengerRepository passengerRepository,
                                             PasswordEncoder passwordEncoder,
                                             JwtService jwtService,
                                             RefreshTokenService refreshTokenService) {
        this.passengerRepository = passengerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public UserType getUserType() {
        return UserType.PASSENGER;
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

        return buildAuthenticationResponse(savedPassenger);
    }

    @Override
    public AuthenticationResponseDTO login(LoginRequestDTO dto) {
        PassengerEntity passenger = passengerRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!verifyPassword(dto.getPassword(), passenger.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return buildAuthenticationResponse(passenger);
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public String generateAccessToken(PassengerEntity passenger) {
        return jwtService.generateAccessToken(passenger.getId(), passenger.getEmail());
    }

    public String generateRefreshToken(PassengerEntity passenger) {
        return refreshTokenService.createAndPersistRefreshToken(passenger.getId(), passenger.getEmail(), UserType.PASSENGER);
    }

    private AuthenticationResponseDTO buildAuthenticationResponse(PassengerEntity passenger) {
        return new AuthenticationResponseDTO(
                generateAccessToken(passenger),
                generateRefreshToken(passenger),
                passenger.getId(),
                passenger.getEmail(),
                passenger.getName()
        );
    }
}
