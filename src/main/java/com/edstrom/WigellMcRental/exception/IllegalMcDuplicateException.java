package com.edstrom.WigellMcRental.exception;

public class IllegalMcDuplicateException extends RuntimeException {
    public IllegalMcDuplicateException() {
        super("Reality crash, no duplicates exists");
    }
}
