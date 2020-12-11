package ru.otus.spring.services.exam;

import ru.otus.spring.exceptions.ExamFormPrepareException;
import ru.otus.spring.model.ExamForm;

public interface ExamFormPreparerService {
    ExamForm prepareForm() throws ExamFormPrepareException;
}
