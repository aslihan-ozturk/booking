package com.vngrs.booking.exception;

public class BaseException extends RuntimeException{
    private final String message;

    protected BaseException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
