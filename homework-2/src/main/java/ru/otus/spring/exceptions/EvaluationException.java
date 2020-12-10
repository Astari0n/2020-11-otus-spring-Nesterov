package ru.otus.spring.exceptions;

public class EvaluationException extends Exception {

    public EvaluationException(final String message) {
        super(message);
    }

    public EvaluationException(final Throwable cause) {
        super(cause);
    }
}
