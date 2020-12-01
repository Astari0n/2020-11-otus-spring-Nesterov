package ru.otus.spring.interactors;

import ru.otus.spring.exceptions.QuestionInteractException;
import ru.otus.spring.exceptions.QuestionLoadException;
import ru.otus.spring.model.Question;
import ru.otus.spring.loaders.QuestionLoader;

import java.util.List;

public abstract class AbstractQuestionInteractor {

    final private List<Question> questions;

    public AbstractQuestionInteractor(final QuestionLoader questionLoader) throws QuestionLoadException {
        this.questions = questionLoader.load();
    }

    abstract protected void interactWithQuestion(final Question question) throws QuestionInteractException;

    public void interact() throws QuestionInteractException {
        for (final Question q : questions) {
            interactWithQuestion(q);
        }
    }
}
