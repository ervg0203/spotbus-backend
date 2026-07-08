package com.spotBus.backend.response;

import lombok.*;

@Getter
@AllArgsConstructor
public class ApiResponseDTO<T> {

    private boolean success;
    private String message;
    private T data;
}