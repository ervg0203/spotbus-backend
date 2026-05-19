package com.spotBus.backend.exception;

import com.spotBus.backend.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(
            MethodArgumentNotValidException ex) {

        String errorMessage =
                Objects.requireNonNull(ex.getBindingResult()
                                .getFieldError())
                        .getDefaultMessage();

        ApiResponse<Object> response =
                new ApiResponse<>(false,
                        errorMessage,
                        null);

        return new ResponseEntity<>(response,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleBusNotFoundException(
            BusNotFoundException ex) {

        ApiResponse<Object> response =
                new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null);

        return new ResponseEntity<>(
                response,
                HttpStatus.NOT_FOUND);
    }
}