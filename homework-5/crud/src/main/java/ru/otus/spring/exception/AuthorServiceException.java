package ru.otus.spring.exception;

public class AuthorServiceException extends ServiceException {
    public AuthorServiceException(final String message) {
        super(message);
    }

    public AuthorServiceException(final Throwable cause) {
        super(cause);
    }

    public AuthorServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
