package ru.otus.spring.exception;

public class GenreServiceException extends ServiceException {
    public GenreServiceException(final String message) {
        super(message);
    }

    public GenreServiceException(final Throwable cause) {
        super(cause);
    }

    public GenreServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
