package com.spotBus.backend.service.impl;

import com.spotBus.backend.dto.RouteRequestDTO;
import com.spotBus.backend.entity.Route;
import com.spotBus.backend.repository.RouteRepository;
import com.spotBus.backend.service.RouteService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;

    public RouteServiceImpl(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Override
    public Route createRoute(RouteRequestDTO dto) {

        Route route = new Route();

        route.setSource(dto.getSource());
        route.setDestination(dto.getDestination());

        route.setCreatedAt(LocalDateTime.now());
        route.setUpdatedAt(LocalDateTime.now());

        return routeRepository.save(route);
    }
}