package com.spotBus.backend.controller;

import com.spotBus.backend.dto.RouteRequestDTO;
import com.spotBus.backend.entity.Route;
import com.spotBus.backend.response.ApiResponse;
import com.spotBus.backend.service.RouteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Route>> createRoute(
            @Valid @RequestBody RouteRequestDTO dto) {

        Route savedRoute = routeService.createRoute(dto);

        ApiResponse<Route> response =
                new ApiResponse<>(
                        true,
                        "Route created successfully",
                        savedRoute);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }
}