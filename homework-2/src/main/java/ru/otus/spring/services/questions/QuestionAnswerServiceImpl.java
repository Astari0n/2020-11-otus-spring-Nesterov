package ru.otus.spring.services.questions;

import org.springframework.stereotype.Service;

import ru.otus.spring.exceptions.IOServiceException;
import ru.otus.spring.exceptions.QuestionAnswerException;
import ru.otus.spring.model.Question;
import ru.otus.spring.services.io.IOService;

@Service
public class QuestionAnswerServiceImpl implements QuestionAnswerService {

    private final IOService io;

    public QuestionAnswerServiceImpl(final IOService io) {
        this.io = io;
    }

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
        io.println("The question is: " + question);
        io.print("Write your answer: ");

        return io.read();
    }
}
