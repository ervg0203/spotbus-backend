package com.spotBus.backend.exception;

import com.spotBus.backend.response.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleValidationException(
            MethodArgumentNotValidException ex) {

        String errorMessage =
                Objects.requireNonNull(ex.getBindingResult()
                                .getFieldError())
                        .getDefaultMessage();

        ApiResponseDTO<Object> response =
                new ApiResponseDTO<>(false,
                        errorMessage,
                        null);

        return new ResponseEntity<>(response,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleBusNotFoundException(
            BusNotFoundException ex) {

        ApiResponseDTO<Object> response =
                new ApiResponseDTO<>(
                        false,
                        ex.getMessage(),
                        null);

        return new ResponseEntity<>(
                response,
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleEmailAlreadyExistsException(
            EmailAlreadyExistsException ex) {

        ApiResponseDTO<Object> response =
                new ApiResponseDTO<>(false, ex.getMessage(), null);

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleInvalidCredentialsException(
            InvalidCredentialsException ex) {

        ApiResponseDTO<Object> response =
                new ApiResponseDTO<>(false, ex.getMessage(), null);

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleExpiredTokenException(
            ExpiredTokenException ex) {

        ApiResponseDTO<Object> response =
                new ApiResponseDTO<>(false, ex.getMessage(), null);

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleInvalidTokenException(
            InvalidTokenException ex) {

        ApiResponseDTO<Object> response =
                new ApiResponseDTO<>(false, ex.getMessage(), null);

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnsupportedUserTypeException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleUnsupportedUserTypeException(
            UnsupportedUserTypeException ex) {

        ApiResponseDTO<Object> response =
                new ApiResponseDTO<>(false, ex.getMessage(), null);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}