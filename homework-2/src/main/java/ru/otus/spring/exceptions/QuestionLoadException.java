package ru.otus.spring.exceptions;

public class QuestionLoadException extends Exception {
    public QuestionLoadException(final String errorMessage) {
        super(errorMessage);
    }

    public QuestionLoadException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
