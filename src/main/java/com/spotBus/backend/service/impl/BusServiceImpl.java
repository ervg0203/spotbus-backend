package com.spotBus.backend.service.impl;

import com.spotBus.backend.dto.BusRequestDTO;
import com.spotBus.backend.entity.Bus;
import com.spotBus.backend.repository.BusRepository;
import com.spotBus.backend.service.BusService;
import org.springframework.stereotype.Service;

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

        return busRepository.save(bus);
    }
}