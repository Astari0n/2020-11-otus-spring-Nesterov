package ru.otus.spring.services.exam;

import ru.otus.spring.exceptions.DisplayServiceException;
import ru.otus.spring.model.ExamResult;

public interface ExamResultDisplayService {
    void display(ExamResult examResult) throws DisplayServiceException;
}
