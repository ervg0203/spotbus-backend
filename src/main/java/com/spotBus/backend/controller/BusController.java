package com.spotBus.backend.controller;

import com.spotBus.backend.dto.BusRequestDTO;
import com.spotBus.backend.entity.Bus;
import com.spotBus.backend.response.ApiResponse;
import com.spotBus.backend.service.BusService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/buses")
public class BusController {

    private final BusService busService;

    public BusController(BusService busService) {
        this.busService = busService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<Bus>> createBus(@Valid @RequestBody BusRequestDTO dto) {
        Bus savedBus = busService.createBus(dto);

        ApiResponse<Bus> response =
                new ApiResponse<>(true,
                        "Bus created successfully",
                        savedBus);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}