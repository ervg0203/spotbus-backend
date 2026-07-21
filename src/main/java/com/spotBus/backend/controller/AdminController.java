package com.spotBus.backend.controller;

import com.spotBus.backend.dto.CreateStaffRequestDTO;
import com.spotBus.backend.response.ApiResponseDTO;
import com.spotBus.backend.response.StaffResponseDTO;
import com.spotBus.backend.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/drivers")
    public ResponseEntity<ApiResponseDTO<StaffResponseDTO>> createDriver(
            @Valid @RequestBody CreateStaffRequestDTO dto) {

        StaffResponseDTO driver = adminService.createDriver(dto);

        ApiResponseDTO<StaffResponseDTO> response =
                new ApiResponseDTO<>(true, "Driver created successfully", driver);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/conductors")
    public ResponseEntity<ApiResponseDTO<StaffResponseDTO>> createConductor(
            @Valid @RequestBody CreateStaffRequestDTO dto) {

        StaffResponseDTO conductor = adminService.createConductor(dto);

        ApiResponseDTO<StaffResponseDTO> response =
                new ApiResponseDTO<>(true, "Conductor created successfully", conductor);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
