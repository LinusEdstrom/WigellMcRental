package com.edstrom.WigellMcRental.exception;

public class IllegalMcUnavailableException extends RuntimeException {
    public IllegalMcUnavailableException() {
        super(" Motorcycle(s) are unavailable for booking during this period");
    }
}
