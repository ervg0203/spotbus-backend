package com.spotBus.backend.controller;

import com.spotBus.backend.dto.BusRequestDTO;
import com.spotBus.backend.entity.Bus;
import com.spotBus.backend.response.ApiResponse;
import com.spotBus.backend.service.BusService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/buses")
public class BusController {

    private final BusService busService;

    public BusController(BusService busService) {
        this.busService = busService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Bus>> createBus(@Valid @RequestBody BusRequestDTO dto) {
        Bus savedBus = busService.createBus(dto);

        ApiResponse<Bus> response =
                new ApiResponse<>(true,
                        "Bus created successfully",
                        savedBus);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Bus>>> getAllBuses() {

        List<Bus> buses = busService.getAllBuses();

        ApiResponse<List<Bus>> response =
                new ApiResponse<>(
                        true,
                        "Buses fetched successfully",
                        buses);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Bus>> getBusById(
            @PathVariable Long id) {

        Bus bus = busService.getBusById(id);

        ApiResponse<Bus> response =
                new ApiResponse<>(
                        true,
                        "Bus fetched successfully",
                        bus);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Bus>> updateBus(
            @PathVariable Long id,
            @Valid @RequestBody BusRequestDTO dto) {

        Bus updatedBus = busService.updateBus(id, dto);

        ApiResponse<Bus> response =
                new ApiResponse<>(
                        true,
                        "Bus updated successfully",
                        updatedBus);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteBus(
            @PathVariable Long id) {

        busService.deleteBus(id);

        ApiResponse<Object> response =
                new ApiResponse<>(
                        true,
                        "Bus deleted successfully",
                        null);

        return ResponseEntity.ok(response);
    }
}