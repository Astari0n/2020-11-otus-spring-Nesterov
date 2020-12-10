package ru.otus.spring.exceptions;

public class InteractionException extends Exception {
    public InteractionException(final String message) {
        super(message);
    }

    public InteractionException(final Throwable cause) {
        super(cause);
    }
}
