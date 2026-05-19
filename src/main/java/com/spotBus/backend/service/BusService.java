package com.spotBus.backend.service;

import com.spotBus.backend.dto.BusRequestDTO;
import com.spotBus.backend.entity.Bus;

import java.util.List;

public interface BusService {

    Bus createBus(BusRequestDTO dto);

    List<Bus> getAllBuses();

    Bus getBusById(Long id);

    Bus updateBus(Long id, BusRequestDTO dto);

    void deleteBus(Long id);
}
