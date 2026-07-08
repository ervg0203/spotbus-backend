package com.spotBus.backend.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BusResponseDTO {

    private Long id;

    private String busNumber;

    private Long routeId;

    private String source;

    private String destination;
}
