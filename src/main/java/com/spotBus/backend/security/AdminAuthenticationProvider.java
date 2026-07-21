package com.spotBus.backend.security;

import com.spotBus.backend.dto.LoginRequestDTO;
import com.spotBus.backend.dto.RegisterRequestDTO;
import com.spotBus.backend.entity.AdminEntity;
import com.spotBus.backend.exception.InvalidCredentialsException;
import com.spotBus.backend.exception.UnsupportedUserTypeException;
import com.spotBus.backend.repository.AdminRepository;
import com.spotBus.backend.response.AuthenticationResponseDTO;
import com.spotBus.backend.service.JwtService;
import com.spotBus.backend.service.RefreshTokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminAuthenticationProvider implements UserAuthenticationProvider {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AdminAuthenticationProvider(AdminRepository adminRepository,
                                       PasswordEncoder passwordEncoder,
                                       JwtService jwtService,
                                       RefreshTokenService refreshTokenService) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public UserType getUserType() {
        return UserType.ADMIN;
    }

    @Override
    public AuthenticationResponseDTO register(RegisterRequestDTO dto) {
        throw new UnsupportedUserTypeException("Admin self-registration is not allowed");
    }

    @Override
    public AuthenticationResponseDTO login(LoginRequestDTO dto) {
        AdminEntity adminEntity = adminRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(dto.getPassword(), adminEntity.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return buildAuthenticationResponse(adminEntity);
    }

    private AuthenticationResponseDTO buildAuthenticationResponse(AdminEntity adminEntity) {
        return new AuthenticationResponseDTO(
                jwtService.generateAccessToken(adminEntity.getId(), adminEntity.getEmail(), UserType.ADMIN),
                refreshTokenService.createAndPersistRefreshToken(adminEntity.getId(), adminEntity.getEmail(), UserType.ADMIN),
                adminEntity.getId(),
                adminEntity.getEmail(),
                adminEntity.getName()
        );
    }
}
