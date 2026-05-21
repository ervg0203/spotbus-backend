package com.spotBus.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusRequestDTO {

    @NotBlank(message = "Bus number is required")
    private String busNumber;

    @NotNull(message = "Route id is required")
    private Long routeId;
}