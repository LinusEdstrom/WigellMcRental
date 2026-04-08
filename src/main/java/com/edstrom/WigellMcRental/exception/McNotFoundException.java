package com.edstrom.WigellMcRental.exception;

public class McNotFoundException extends RuntimeException {
    public McNotFoundException(Long id) {
        super("the motorcycle is missing");
    }
}
