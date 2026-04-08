package com.edstrom.WigellMcRental.exception;

public class IllegalDatesException extends RuntimeException {
    public IllegalDatesException() {
        super(" Invalid choosen dates");
    }
}
