package ru.otus.spring.services;

import ru.otus.spring.exceptions.EvaluationException;
import ru.otus.spring.model.ExamResult;
import ru.otus.spring.model.User;

public interface EvaluationService<U extends User, R extends ExamResult> {
    void evaluate(U user, R result) throws EvaluationException;
}
