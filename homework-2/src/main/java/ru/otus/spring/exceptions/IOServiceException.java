package ru.otus.spring.exceptions;

public class IOServiceException extends Exception {
    public IOServiceException(final String message) {
        super(message);
    }

    public IOServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
