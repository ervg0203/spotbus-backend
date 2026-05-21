package com.spotBus.backend.service;

import com.spotBus.backend.dto.RouteRequestDTO;
import com.spotBus.backend.entity.Route;

public interface RouteService {
    Route createRoute(RouteRequestDTO dto);
}
