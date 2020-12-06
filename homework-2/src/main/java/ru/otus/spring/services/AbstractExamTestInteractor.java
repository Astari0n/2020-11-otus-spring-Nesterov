package ru.otus.spring.services;

import ru.otus.spring.exceptions.ExamException;
import ru.otus.spring.exceptions.QuestionInteractException;
import ru.otus.spring.exceptions.QuestionLoadException;
import ru.otus.spring.loaders.QuestionLoader;
import ru.otus.spring.model.ExamTest;
import ru.otus.spring.model.Question;

import java.util.List;

public abstract class AbstractExamTestInteractor {

    final protected ExamTest examTest;

    public AbstractExamTestInteractor(
        final QuestionLoader questionLoader,
        final int numberOfQuestionsToAsk,
        final int numberOfQuestionsToPassTheTest
    ) throws QuestionLoadException {
        this.examTest = prepareExamTest(questionLoader, numberOfQuestionsToAsk, numberOfQuestionsToPassTheTest);
    }

    abstract protected void answerTheQuestion(final Question question) throws QuestionInteractException, ExamException;

    protected void beforeExam() {

    }

    protected void afterExam() {

    }

    public void exam() throws QuestionInteractException, ExamException {
        beforeExam();

        for (final Question q : getQuestions()) {
            if (q == null) {
                throw new QuestionInteractException("Trying to interact with null question in examtest: " + examTest);
            }
            answerTheQuestion(q);
        }

        afterExam();
    }

    public List<Question> getQuestions() {
        return examTest.getQuestions().subList(0, examTest.getNumberOfQuestionsToAsk());
    }

    private ExamTest prepareExamTest(
        final QuestionLoader questionLoader,
        final int numberOfQuestionsToAsk,
        final int numberOfQuestionsToPassTheTest
    ) throws QuestionLoadException {
        final List<Question> questions = questionLoader.load();
        return new ExamTest(questions, numberOfQuestionsToAsk, numberOfQuestionsToPassTheTest);
    }
}
