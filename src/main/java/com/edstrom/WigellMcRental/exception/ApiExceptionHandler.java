package com.edstrom.WigellMcRental.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCustomerNotFound(CustomerNotFoundException e, HttpServletRequest request) {
        return buildResponse(e.getMessage(), "Customer not found", HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleAddressNotFound(AddressNotFoundException e, HttpServletRequest request) {
        return buildResponse(e.getMessage(), "Address not found", HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(CustomerAndAddressNoMatchException.class)
    public ResponseEntity<Map<String, Object>> handleNoMatch(CustomerAndAddressNoMatchException e,
                                                             HttpServletRequest request) {
        return buildResponse(e.getMessage(), "Customer and address do not match", HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleEmailExisting(EmailAlreadyExistsException e, HttpServletRequest request) {
        return buildResponse(e.getMessage(), "Email already exists", HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(McNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleMcNotFound(McNotFoundException e, HttpServletRequest request) {
        return buildResponse(e.getMessage(), "Motorcycle not found", HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(IllegalMcDuplicateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalDuplicate(IllegalMcDuplicateException e,
                                                                      HttpServletRequest request) {
        return buildResponse(e.getMessage(),
                "No duplicated motorcycles allowed", HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(IllegalMcUnavailableException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalUnavailability(IllegalMcUnavailableException e,
                                                                           HttpServletRequest request) {
        return buildResponse(e.getMessage(), "Motorcycle(s) unavailable for chosen period", HttpStatus.CONFLICT, request);
    }
    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleBookingNotFound(BookingNotFoundException e,
                                                                     HttpServletRequest request){
        return buildResponse(e.getMessage(), "Booking with that id not found", HttpStatus.NOT_FOUND, request);
    }
    @ExceptionHandler(IllegalDatesException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalDates(IllegalDatesException e,
                                                                  HttpServletRequest request){
        return buildResponse(e.getMessage(), "Invalid choosen dates"
        , HttpStatus.CONFLICT, request);
    }
    @ExceptionHandler(IllegalAgeException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalAge(IllegalAgeException e,
                                                                HttpServletRequest request){
        return buildResponse(e.getMessage(), "Not allowed to rent", HttpStatus.CONFLICT, request);
    }

    private ResponseEntity<Map<String, Object>> buildResponse(String message, String error,
                                                              HttpStatus status, HttpServletRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        body.put("path", request.getRequestURI());

        return ResponseEntity.status(status).body(body);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return errors;
    }
}
