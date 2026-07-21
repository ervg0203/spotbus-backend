package com.spotBus.backend.service.impl;

import com.spotBus.backend.dto.CreateStaffRequestDTO;
import com.spotBus.backend.entity.ConductorEntity;
import com.spotBus.backend.entity.DriverEntity;
import com.spotBus.backend.exception.EmailAlreadyExistsException;
import com.spotBus.backend.repository.ConductorRepository;
import com.spotBus.backend.repository.DriverRepository;
import com.spotBus.backend.response.StaffResponseDTO;
import com.spotBus.backend.service.AdminService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdminServiceImpl implements AdminService {

    private final DriverRepository driverRepository;
    private final ConductorRepository conductorRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(DriverRepository driverRepository,
                            ConductorRepository conductorRepository,
                            PasswordEncoder passwordEncoder) {
        this.driverRepository = driverRepository;
        this.conductorRepository = conductorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public StaffResponseDTO createDriver(CreateStaffRequestDTO dto) {
        if (driverRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException("Email is already registered");
        }

        DriverEntity driver = new DriverEntity();
        driver.setName(dto.getName());
        driver.setEmail(dto.getEmail());
        driver.setPhone(dto.getPhone());
        driver.setPassword(passwordEncoder.encode(dto.getPassword()));
        driver.setCreatedAt(LocalDateTime.now());
        driver.setUpdatedAt(LocalDateTime.now());

        DriverEntity savedDriver = driverRepository.save(driver);
        return toStaffResponse(savedDriver.getId(), savedDriver.getName(), savedDriver.getEmail(), savedDriver.getPhone());
    }

    @Override
    public StaffResponseDTO createConductor(CreateStaffRequestDTO dto) {
        if (conductorRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException("Email is already registered");
        }

        ConductorEntity conductor = new ConductorEntity();
        conductor.setName(dto.getName());
        conductor.setEmail(dto.getEmail());
        conductor.setPhone(dto.getPhone());
        conductor.setPassword(passwordEncoder.encode(dto.getPassword()));
        conductor.setCreatedAt(LocalDateTime.now());
        conductor.setUpdatedAt(LocalDateTime.now());

        ConductorEntity savedConductor = conductorRepository.save(conductor);
        return toStaffResponse(savedConductor.getId(), savedConductor.getName(), savedConductor.getEmail(), savedConductor.getPhone());
    }

    private StaffResponseDTO toStaffResponse(Long id, String name, String email, String phone) {
        return new StaffResponseDTO(id, name, email, phone);
    }
}
