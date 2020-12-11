package ru.otus.spring.model;

import java.util.List;

public class ExamForm {

    private final List<Question> questions;

    private final int amountOfCorrectAnswersToPassExam;

    public ExamForm(
        final List<Question> questions,
        final int amountOfCorrectAnswersToPassExam
    ) {
        this.questions = questions;
        this.amountOfCorrectAnswersToPassExam = amountOfCorrectAnswersToPassExam;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int getAmountOfCorrectAnswersToPassExam() {
        return amountOfCorrectAnswersToPassExam;
    }

    @Override
    public String toString() {
        return "ExamForm{" +
            "questions=" + questions +
            ", amountOfCorrectAnswersToPassExam=" + amountOfCorrectAnswersToPassExam +
            '}';
    }
}
