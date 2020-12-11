package ru.otus.spring.services.exam;

import org.springframework.stereotype.Service;

import ru.otus.spring.exceptions.ExamResultDisplayServiceException;
import ru.otus.spring.exceptions.IOServiceException;

import ru.otus.spring.model.ExamResult;
import ru.otus.spring.services.io.IOService;

@Service
public class ExamResultDisplayServiceImpl implements ExamResultDisplayService {

    private final IOService io;

    public ExamResultDisplayServiceImpl(final IOService io) {
        this.io = io;
    }

    @Override
    public void display(final ExamResult examResult) throws ExamResultDisplayServiceException {
        try {
            doDisplay(examResult);
        } catch (final IOServiceException e) {
            throw new ExamResultDisplayServiceException(e);
        }
    }

    protected void doDisplay(final ExamResult examResult) throws IOServiceException {
        io.println();
        io.printf(
            "%s %s, your result is: %d/%d (%.0f%%) correct answers! You %s the exam!",
            examResult.getStudent().getFirstName(),
            examResult.getStudent().getLastName(),
            examResult.getCorrectAnswersCount(),
            examResult.getQuestionsCount(),
            examResult.getCompletionPercentage(),
            examResult.getExamMark()
        );
        io.println();
    }
}
