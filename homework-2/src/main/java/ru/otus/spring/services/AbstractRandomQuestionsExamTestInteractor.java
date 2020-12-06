package ru.otus.spring.services;

import ru.otus.spring.exceptions.QuestionLoadException;
import ru.otus.spring.loaders.QuestionLoader;
import ru.otus.spring.model.Question;

import java.util.Collections;
import java.util.List;

public abstract class AbstractRandomQuestionsExamTestInteractor extends AbstractExamTestInteractor {

    public AbstractRandomQuestionsExamTestInteractor(
        final QuestionLoader questionLoader,
        final int numberOfQuestionsToAsk,
        final int numberOfQuestionsToPassTheTest
    ) throws QuestionLoadException {
        super(questionLoader, numberOfQuestionsToAsk, numberOfQuestionsToPassTheTest);
    }

    @Override
    public List<Question> getQuestions() {
        final List<Question> questions = examTest.getQuestions();
        Collections.shuffle(questions);
        return questions.subList(0, examTest.getNumberOfQuestionsToAsk());
    }
}
