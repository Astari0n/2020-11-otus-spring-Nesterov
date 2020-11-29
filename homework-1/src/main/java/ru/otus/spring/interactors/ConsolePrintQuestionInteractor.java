package ru.otus.spring.interactors;

import ru.otus.spring.exceptions.QuestionInteractException;
import ru.otus.spring.exceptions.QuestionLoadException;
import ru.otus.spring.loaders.QuestionLoader;
import ru.otus.spring.model.Question;

public class ConsolePrintQuestionInteractor extends AbstractQuestionInteractor {
    public ConsolePrintQuestionInteractor(final QuestionLoader questionLoader) throws QuestionLoadException {
        super(questionLoader);
    }

    @Override
    protected void interactWithQuestion(final Question question) throws QuestionInteractException {
        if (question == null) {
            throw new QuestionInteractException("Trying to interact with null question");
        }

        System.out.println(question);
    }
}
