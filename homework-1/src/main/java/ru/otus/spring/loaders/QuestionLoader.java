package ru.otus.spring.loaders;

import ru.otus.spring.exceptions.QuestionLoadException;
import ru.otus.spring.model.Question;

import java.util.List;

public interface QuestionLoader {
    List<Question> load() throws QuestionLoadException;
}
