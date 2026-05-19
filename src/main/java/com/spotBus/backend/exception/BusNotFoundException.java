package com.spotBus.backend.exception;

public class BusNotFoundException extends RuntimeException {

    public BusNotFoundException(String message) {
        super(message);
    }
}