package ru.otus.spring.exceptions;

public class LoadException extends Exception {
    public LoadException(final String errorMessage) {
        super(errorMessage);
    }

    public LoadException(final Throwable cause) {
        super(cause);
    }
}
