package ru.otus.spring.services.exam;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ru.otus.spring.exceptions.ExamResultDisplayServiceException;
import ru.otus.spring.exceptions.IOServiceException;

import ru.otus.spring.model.ExamResult;
import ru.otus.spring.services.io.MessageIOService;

@Service
@RequiredArgsConstructor
public class ExamResultDisplayServiceImpl implements ExamResultDisplayService {

    private final MessageIOService io;

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
        io.printlnMsg("exam.result_display_message",
            examResult.getStudent().getFirstName(),
            examResult.getStudent().getLastName(),
            examResult.getCorrectAnswersCount(),
            examResult.getQuestionsCount(),
            String.format(io.readMsg("exam.result_display_percentage_format"), examResult.getCompletionPercentage()),
            examResult.getExamMark()
        );
    }
}
