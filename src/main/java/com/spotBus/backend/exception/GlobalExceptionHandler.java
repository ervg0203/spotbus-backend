package com.spotBus.backend.exception;

import com.spotBus.backend.response.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<Map<String, String>>> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> error.getDefaultMessage() != null ? error.getDefaultMessage() : "Invalid value",
                        (first, second) -> first,
                        LinkedHashMap::new));

        String message = fieldErrors.size() == 1
                ? fieldErrors.values().iterator().next()
                : "Validation failed";

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseDTO<>(false, message, fieldErrors));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleMalformedRequestBody(
            HttpMessageNotReadableException ex) {

        return errorResponse(HttpStatus.BAD_REQUEST, "Malformed or missing request body");
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleEmailAlreadyExistsException(
            EmailAlreadyExistsException ex) {

        return errorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleInvalidCredentialsException(
            InvalidCredentialsException ex) {

        return errorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleExpiredTokenException(
            ExpiredTokenException ex) {

        return errorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleInvalidTokenException(
            InvalidTokenException ex) {

        return errorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(UnsupportedUserTypeException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleUnsupportedUserTypeException(
            UnsupportedUserTypeException ex) {

        return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(BusNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleBusNotFoundException(
            BusNotFoundException ex) {

        return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    private ResponseEntity<ApiResponseDTO<Object>> errorResponse(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(new ApiResponseDTO<>(false, message, null));
    }
}
