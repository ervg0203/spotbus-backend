package com.spotBus.backend.service;

import com.spotBus.backend.dto.CreateStaffRequestDTO;
import com.spotBus.backend.response.StaffResponseDTO;

public interface AdminService {

    StaffResponseDTO createDriver(CreateStaffRequestDTO dto);

    StaffResponseDTO createConductor(CreateStaffRequestDTO dto);
}
