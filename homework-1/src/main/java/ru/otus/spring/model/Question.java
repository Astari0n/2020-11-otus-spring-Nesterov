package ru.otus.spring.model;

import java.util.List;

public class Question {
    final private String question;

    final private List<String> answers;

    public Question(final String question, final List<String> answers) {
        this.question = question;
        this.answers = answers;
    }

    public boolean isAnswerCorrect(final String answer) {
        return answers.stream().anyMatch(a -> a.equalsIgnoreCase(answer));
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    @Override
    public String toString() {
        return question;
    }
}
