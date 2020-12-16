package ru.otus.spring.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class Question {
    @Getter
    final private String question;

    @Getter
    final private List<String> answers;

    public boolean isAnswerCorrect(final String answer) {
        return answers.stream().anyMatch(a -> a.equalsIgnoreCase(answer));
    }

    @Override
    public String toString() {
        return question;
    }
}
