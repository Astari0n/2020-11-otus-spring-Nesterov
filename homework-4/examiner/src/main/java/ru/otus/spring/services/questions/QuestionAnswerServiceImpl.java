package ru.otus.spring.services.questions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ru.otus.spring.exceptions.IOServiceException;
import ru.otus.spring.exceptions.QuestionAnswerException;
import ru.otus.spring.model.Question;
import ru.otus.spring.services.io.IOService;
import ru.otus.spring.services.localization.MessageLocalizationService;

@Service
@RequiredArgsConstructor
public class QuestionAnswerServiceImpl implements QuestionAnswerService {

    private final IOService io;

    private final MessageLocalizationService localizationService;

    @Override
    public String answer(final Question question) throws QuestionAnswerException {
        if (question == null) {
            throw new QuestionAnswerException("Trying to interact with null question");
        }

        try {
            return answerTheQuestion(question);
        } catch (final IOServiceException e) {
            throw new QuestionAnswerException(e);
        }
    }

    protected String  answerTheQuestion(final Question question) throws IOServiceException {
        io.println();
        io.println(localizationService.getText("exam.question_print", question));
        io.print(localizationService.getText("exam.request_answer"));

        return io.read();
    }
}
