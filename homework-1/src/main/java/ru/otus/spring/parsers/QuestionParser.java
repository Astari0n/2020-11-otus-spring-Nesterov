package ru.otus.spring.parsers;

import ru.otus.spring.model.Question;

import java.io.IOException;
import java.util.List;

public interface QuestionParser {
    List<Question> parse() throws IllegalArgumentException, IOException;
}
