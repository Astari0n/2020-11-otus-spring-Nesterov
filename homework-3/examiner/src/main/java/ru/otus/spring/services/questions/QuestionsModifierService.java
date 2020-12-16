package ru.otus.spring.services.questions;

import ru.otus.spring.model.Question;

import java.util.List;

public interface QuestionsModifierService {
    List<Question> modify(List<Question> data);
}
