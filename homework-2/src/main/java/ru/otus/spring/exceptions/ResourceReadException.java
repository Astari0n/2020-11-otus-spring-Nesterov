package ru.otus.spring.exceptions;

public class ResourceReadException extends Exception {

    public ResourceReadException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
