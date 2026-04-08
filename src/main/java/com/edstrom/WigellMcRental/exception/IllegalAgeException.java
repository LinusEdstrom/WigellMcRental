package com.edstrom.WigellMcRental.exception;

public class IllegalAgeException extends RuntimeException {
    public IllegalAgeException() {
        super("You are too young punk!!");
    }
}
