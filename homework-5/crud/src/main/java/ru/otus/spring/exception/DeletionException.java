package ru.otus.spring.exception;

public class DeletionException extends Exception {

    public DeletionException(final String message) {
        super(message);
    }

    public DeletionException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
