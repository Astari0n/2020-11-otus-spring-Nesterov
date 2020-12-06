package ru.otus.spring.model;

import java.util.List;

public class ExamTest {

    final private List<Question> questions;

    final private int numberOfQuestionsToAsk;

    final private int numberOfQuestionsToPassTheTest;

    public ExamTest(
        final List<Question> questions,
        final int numberOfQuestionsToAsk,
        final int numberOfQuestionsToPassTheTest
    ) {
        this.questions = questions;
        this.numberOfQuestionsToAsk = numberOfQuestionsToAsk;
        this.numberOfQuestionsToPassTheTest = numberOfQuestionsToPassTheTest;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int getNumberOfQuestionsToAsk() {
        return numberOfQuestionsToAsk;
    }

    public int getNumberOfQuestionsToPassTheTest() {
        return numberOfQuestionsToPassTheTest;
    }

    @Override
    public String toString() {
        return "ExamTest{" +
            "questions=" + questions +
            ", numberOfQuestionsToAsk=" + numberOfQuestionsToAsk +
            ", numberOfQuestionsToPassTheTest=" + numberOfQuestionsToPassTheTest +
            '}';
    }
}
