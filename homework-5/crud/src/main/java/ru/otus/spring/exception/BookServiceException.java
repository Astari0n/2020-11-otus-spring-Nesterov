package ru.otus.spring.exception;

public class BookServiceException extends ServiceException {
    public BookServiceException(final String message) {
        super(message);
    }

    public BookServiceException(final Throwable cause) {
        super(cause);
    }

    public BookServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
