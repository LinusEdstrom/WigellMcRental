package com.edstrom.WigellMcRental.exception;

public class CustomerAndAddressNoMatchException extends RuntimeException {
    public CustomerAndAddressNoMatchException(Long id) {
        super(" Customer and address are not connected");
    }
}
