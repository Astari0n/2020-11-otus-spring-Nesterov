package ru.otus.spring.exceptions;

public class ExamException extends Exception {

    public ExamException(final String message) {
        super(message);
    }

    public ExamException(final Throwable cause) {
        super(cause);
    }
}
