package com.spotBus.backend.service.impl;

import com.spotBus.backend.response.AuthenticationResponseDTO;
import com.spotBus.backend.dto.LoginRequestDTO;
import com.spotBus.backend.dto.RegisterRequestDTO;
import com.spotBus.backend.entity.PassengerEntity;
import com.spotBus.backend.exception.EmailAlreadyExistsException;
import com.spotBus.backend.exception.InvalidCredentialsException;
import com.spotBus.backend.repository.PassengerRepository;
import com.spotBus.backend.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    private final PassengerRepository passengerRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(PassengerRepository passengerRepository,
                           PasswordEncoder passwordEncoder) {
        this.passengerRepository = passengerRepository;
        this.passwordEncoder = passwordEncoder;
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

    private AuthenticationResponseDTO toAuthenticationResponse(PassengerEntity passenger) {
        return new AuthenticationResponseDTO(
                null,
                null,
                passenger.getId(),
                passenger.getEmail(),
                passenger.getName()
        );
    }
}
