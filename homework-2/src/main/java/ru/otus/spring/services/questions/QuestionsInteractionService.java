package ru.otus.spring.services.questions;

import ru.otus.spring.exceptions.InteractionException;
import ru.otus.spring.model.Question;

public interface QuestionsInteractionService<R> {
    R interact(Question question) throws InteractionException;
}
