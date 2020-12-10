package ru.otus.spring.services.questions;

import org.springframework.stereotype.Service;
import ru.otus.spring.exceptions.IOServiceException;
import ru.otus.spring.exceptions.InteractionException;
import ru.otus.spring.model.Question;
import ru.otus.spring.services.io.IOService;

@Service
public class QuestionsInteractionServiceImpl implements QuestionsInteractionService<String> {

    private final IOService io;

    public QuestionsInteractionServiceImpl(final IOService io) {
        this.io = io;
    }

    @Override
    public String interact(final Question question) throws InteractionException {
        if (question == null) {
            throw new InteractionException("Trying to interact with null question");
        }

        try {
            return answerTheQuestion(question);
        } catch (final IOServiceException e) {
            throw new InteractionException(e);
        }
    }

    protected String  answerTheQuestion(final Question question) throws IOServiceException {
        io.println();
        io.println("The question is: " + question);
        io.print("Write your answer: ");

        return io.read();
    }
}
