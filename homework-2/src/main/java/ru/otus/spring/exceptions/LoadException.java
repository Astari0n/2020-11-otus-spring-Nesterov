package ru.otus.spring.exceptions;

public class LoadException extends Exception {
    public LoadException(final String errorMessage) {
        super(errorMessage);
    }

    public LoadException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
