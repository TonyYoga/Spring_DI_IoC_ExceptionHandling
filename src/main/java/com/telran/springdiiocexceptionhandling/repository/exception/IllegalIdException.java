package com.telran.springdiiocexceptionhandling.repository.exception;

public class IllegalIdException extends RuntimeException {
    public IllegalIdException(String message) {
        super(message);
    }

    public IllegalIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
