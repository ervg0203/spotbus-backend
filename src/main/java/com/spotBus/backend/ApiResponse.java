package com.spotBus.backend;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiResponse {

    private String message;
    private int status;

    public ApiResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }

}