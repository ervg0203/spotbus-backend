package com.spotBus.backend.controller;

import com.spotBus.backend.entity.Bus;
import com.spotBus.backend.repository.BusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/buses")
public class BusController {

    private final BusRepository busRepository;

    public BusController(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    @PostMapping("/upload")
    public Bus createBus(@RequestBody Bus bus) {
        return busRepository.save(bus);
    }
}