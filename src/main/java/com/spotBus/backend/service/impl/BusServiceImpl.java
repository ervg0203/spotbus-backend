package com.spotBus.backend.service.impl;

import com.spotBus.backend.dto.BusRequestDTO;
import com.spotBus.backend.entity.Bus;
import com.spotBus.backend.entity.Route;
import com.spotBus.backend.exception.BusNotFoundException;
import com.spotBus.backend.repository.BusRepository;
import com.spotBus.backend.repository.RouteRepository;
import com.spotBus.backend.service.BusService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;
    private final RouteRepository routeRepository;

    public BusServiceImpl(BusRepository busRepository,
                      RouteRepository routeRepository) {

        this.busRepository = busRepository;
        this.routeRepository = routeRepository;
    }

    @Override
    public Bus createBus(BusRequestDTO dto) {

        Route route = routeRepository.findById(dto.getRouteId())
                .orElseThrow(() ->
                        new RuntimeException("Route not found"));

        Bus bus = new Bus();

        bus.setBusNumber(dto.getBusNumber());
        bus.setRoute(route);

        bus.setCreatedAt(LocalDateTime.now());
        bus.setUpdatedAt(LocalDateTime.now());

        return busRepository.save(bus);
    }

    @Override
    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    @Override
    public Bus getBusById(Long id) {

        return busRepository.findById(id)
                .orElseThrow(() ->
                        new BusNotFoundException(
                                "Bus not found with id: " + id));
    }

    @Override
    public Bus updateBus(Long id, BusRequestDTO dto) {

        Route route = routeRepository.findById(dto.getRouteId())
                .orElseThrow(() ->
                        new RuntimeException("Route not found"));

        Bus existingBus = getBusById(id);

        existingBus.setBusNumber(dto.getBusNumber());
        existingBus.setRoute(route);

        existingBus.setUpdatedAt(LocalDateTime.now());

        return busRepository.save(existingBus);
    }

    @Override
    public void deleteBus(Long id) {

        Bus bus = getBusById(id);

        busRepository.delete(bus);
    }
}