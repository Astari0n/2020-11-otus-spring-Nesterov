package ru.otus.spring.services;

import org.springframework.stereotype.Service;
import ru.otus.spring.exceptions.EvaluationException;
import ru.otus.spring.model.ExamResult;
import ru.otus.spring.model.User;

import java.io.IOException;

@Service
public class IOExamResultEvaluationService implements EvaluationService<User, ExamResult> {

    private final IOService io;

    public IOExamResultEvaluationService(final IOService ioService) {
        this.io = ioService;
    }

    @Override
    public void evaluate(final User user, final ExamResult examResult) throws EvaluationException {
        try {
            evaluateResult(user, examResult);
        } catch (final IOException e) {
            throw new EvaluationException(e);
        }
    }

    public void evaluateResult(final User user, final ExamResult examResult) throws IOException {
        io.println();
        io.printf(
            "%s %s, your result is: [%d / %d] correct answers! You %s the exam!",
            user.getFirstName(),
            user.getLastName(),
            examResult.getCorrectCount(),
            examResult.getAskedCount(),
            (examResult.getCorrectCount() >= examResult.getCountToPassTheTest() ? "PASSED" : "FAILED")
        );
        io.println();
    }
}
