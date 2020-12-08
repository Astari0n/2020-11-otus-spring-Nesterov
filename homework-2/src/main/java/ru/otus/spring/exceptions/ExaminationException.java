package ru.otus.spring.exceptions;

public class ExaminationException extends Exception {

    public ExaminationException(final String message) {
        super(message);
    }

    public ExaminationException(final Throwable cause) {
        super(cause);
    }

}
