package com.spotBus.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusRequestDTO {

    @NotBlank(message = "Bus number is required")
    private String busNumber;

    @NotBlank(message = "Route name is required")
    private String routeName;
}
