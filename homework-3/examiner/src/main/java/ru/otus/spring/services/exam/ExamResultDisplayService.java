package ru.otus.spring.services.exam;

import ru.otus.spring.exceptions.ExamResultDisplayServiceException;
import ru.otus.spring.model.ExamResult;

public interface ExamResultDisplayService {
    void display(ExamResult examResult) throws ExamResultDisplayServiceException;
}
