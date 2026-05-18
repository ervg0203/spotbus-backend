package com.spotBus.backend.service;

import com.spotBus.backend.dto.BusRequestDTO;
import com.spotBus.backend.entity.Bus;

public interface BusService {

    Bus createBus(BusRequestDTO dto);


}
