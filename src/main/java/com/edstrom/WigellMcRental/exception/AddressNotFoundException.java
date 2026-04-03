package com.edstrom.WigellMcRental.exception;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(Long id) {
        super("Address with id " + id + " not found");
    }
}
