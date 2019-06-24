package com.telran.springdiiocexceptionhandling.providers.exception;

public class StoreProviderException extends RuntimeException {
    public StoreProviderException(String message) {
        super(message);
    }

    public StoreProviderException(String message, Throwable cause) {
        super(message, cause);
    }
}
