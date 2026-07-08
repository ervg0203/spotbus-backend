package com.spotBus.backend.controller;

import com.spotBus.backend.dto.BusRequestDTO;
import com.spotBus.backend.response.BusResponseDTO;
import com.spotBus.backend.entity.Bus;
import com.spotBus.backend.response.ApiResponseDTO;
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
    public ResponseEntity<ApiResponseDTO<Bus>> createBus(@Valid @RequestBody BusRequestDTO dto) {
        Bus savedBus = busService.createBus(dto);

        ApiResponseDTO<Bus> response =
                new ApiResponseDTO<>(true,
                        "Bus created successfully",
                        savedBus);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<Bus>>> getAllBuses() {

        List<Bus> buses = busService.getAllBuses();

        ApiResponseDTO<List<Bus>> response =
                new ApiResponseDTO<>(
                        true,
                        "Buses fetched successfully",
                        buses);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<BusResponseDTO>> getBusById(
            @PathVariable Long id) {

        Bus bus = busService.getBusById(id);

        BusResponseDTO busDto = new BusResponseDTO(
                bus.getId(),
                bus.getBusNumber(),
                bus.getRoute().getId(),
                bus.getRoute().getSource(),
                bus.getRoute().getDestination()
        );

        ApiResponseDTO<BusResponseDTO> response =
                new ApiResponseDTO<>(
                        true,
                        "Bus fetched successfully",
                        busDto);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Bus>> updateBus(
            @PathVariable Long id,
            @Valid @RequestBody BusRequestDTO dto) {

        Bus updatedBus = busService.updateBus(id, dto);

        ApiResponseDTO<Bus> response =
                new ApiResponseDTO<>(
                        true,
                        "Bus updated successfully",
                        updatedBus);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Object>> deleteBus(
            @PathVariable Long id) {

        busService.deleteBus(id);

        ApiResponseDTO<Object> response =
                new ApiResponseDTO<>(
                        true,
                        "Bus deleted successfully",
                        null);

        return ResponseEntity.ok(response);
    }
}