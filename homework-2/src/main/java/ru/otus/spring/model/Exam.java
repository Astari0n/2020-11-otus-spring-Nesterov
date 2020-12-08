package ru.otus.spring.model;

import java.util.List;

public class Exam<Q> {

    final private List<Q> questions;

    final private int numberOfQuestionsToAsk;

    final private int numberOfQuestionsToPassTheTest;

    public Exam(
        final List<Q> questions,
        final int numberOfQuestionsToAsk,
        final int numberOfQuestionsToPassTheTest
    ) {
        this.questions = questions;
        this.numberOfQuestionsToAsk = numberOfQuestionsToAsk;
        this.numberOfQuestionsToPassTheTest = numberOfQuestionsToPassTheTest;
    }

    public List<Q> getQuestions() {
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
