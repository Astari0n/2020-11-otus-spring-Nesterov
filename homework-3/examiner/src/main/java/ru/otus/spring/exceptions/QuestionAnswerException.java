package ru.otus.spring.exceptions;

public class QuestionAnswerException extends ExamException {
    public QuestionAnswerException(final String message) {
        super(message);
    }

    public QuestionAnswerException(final Throwable cause) {
        super(cause);
    }
}
