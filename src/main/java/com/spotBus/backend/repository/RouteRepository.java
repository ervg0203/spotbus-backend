package com.spotBus.backend.repository;

import com.spotBus.backend.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository
        extends JpaRepository<Route, Long> {

}