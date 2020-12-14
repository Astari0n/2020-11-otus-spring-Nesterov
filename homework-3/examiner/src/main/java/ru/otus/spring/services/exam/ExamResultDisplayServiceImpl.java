package ru.otus.spring.services.exam;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ru.otus.spring.exceptions.ExamResultDisplayServiceException;
import ru.otus.spring.exceptions.IOServiceException;

import ru.otus.spring.model.ExamResult;
import ru.otus.spring.services.io.IOService;
import ru.otus.spring.services.localization.MessageLocalizationService;

@Service
@RequiredArgsConstructor
public class ExamResultDisplayServiceImpl implements ExamResultDisplayService {

    private final IOService io;

    private final MessageLocalizationService localizationService;

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
        io.print(localizationService.getText("exam.result_display_message",
            examResult.getStudent().getFirstName(),
            examResult.getStudent().getLastName(),
            examResult.getCorrectAnswersCount(),
            examResult.getQuestionsCount(),
            String.format(
                localizationService.getText("exam.result_display_percentage_format"),
                examResult.getCompletionPercentage()
            ),
            examResult.getExamMark()
        ));
        io.println();
    }
}
