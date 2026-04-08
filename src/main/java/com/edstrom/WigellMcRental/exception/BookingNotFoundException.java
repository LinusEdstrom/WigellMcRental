package com.edstrom.WigellMcRental.exception;

public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException(Long id) {
        super(" Found not booking with that id");
    }
}
