package com.spotBus.backend.service.impl;

import com.spotBus.backend.dto.BusRequestDTO;
import com.spotBus.backend.entity.Bus;
import com.spotBus.backend.exception.BusNotFoundException;
import com.spotBus.backend.repository.BusRepository;
import com.spotBus.backend.service.BusService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;

    public BusServiceImpl(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    @Override
    public Bus createBus(BusRequestDTO dto) {

        Bus bus = new Bus();

        bus.setBusNumber(dto.getBusNumber());
        bus.setRouteName(dto.getRouteName());

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

        Bus existingBus = getBusById(id);

        existingBus.setBusNumber(dto.getBusNumber());
        existingBus.setRouteName(dto.getRouteName());

        existingBus.setUpdatedAt(LocalDateTime.now());

        return busRepository.save(existingBus);
    }

    @Override
    public void deleteBus(Long id) {

        Bus bus = getBusById(id);

        busRepository.delete(bus);
    }
}