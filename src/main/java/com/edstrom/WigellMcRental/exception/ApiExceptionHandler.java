package com.edstrom.WigellMcRental.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<?> handleCustomerNotFound(CustomerNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                        "timestamp", LocalDateTime.now().toString(),
                        "status", HttpStatus.NOT_FOUND.value(),
                        "error", "Customer not found",
                        "message", e.getMessage()
                )
        );
    }
    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<?> handleAddressNotFound(AddressNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                        "timestamp", LocalDateTime.now().toString(),
                        "status", HttpStatus.NOT_FOUND.value(),
                        "error", "Address is not found",
                        "message", e.getMessage()
                )
        );
    }
}
