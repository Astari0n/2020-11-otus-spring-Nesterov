package ru.otus.spring.services.exam;

import ru.otus.spring.exceptions.EvaluationException;
import ru.otus.spring.model.ExamAnswerForm;
import ru.otus.spring.model.ExamResult;

public interface ExamEvaluationService {
    ExamResult evaluate(ExamAnswerForm answerForm) throws EvaluationException;
}
