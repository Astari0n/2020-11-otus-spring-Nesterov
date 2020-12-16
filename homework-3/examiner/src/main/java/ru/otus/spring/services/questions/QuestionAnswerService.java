package ru.otus.spring.services.questions;

import ru.otus.spring.exceptions.QuestionAnswerException;
import ru.otus.spring.model.Question;

public interface QuestionAnswerService {
    String answer(Question question) throws QuestionAnswerException;
}
