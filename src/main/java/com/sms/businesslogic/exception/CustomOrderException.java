package com.sms.businesslogic.exception;

public class CustomOrderException extends RuntimeException {

    public CustomOrderException(String message) {
        super(message);
    }

    public CustomOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}
