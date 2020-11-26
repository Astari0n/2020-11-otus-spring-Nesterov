package ru.otus.spring.interactors;

import ru.otus.spring.model.Question;
import ru.otus.spring.parsers.QuestionParser;

import java.io.IOException;
import java.util.List;

public class ConsoleInteractor {

    final private List<Question> questions;

    public ConsoleInteractor(final QuestionParser parser) throws IOException {
        this.questions = parser.parse();
    }

    public void interact() {
        for (final Question q : questions) {
            System.out.println(q);
        }
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
