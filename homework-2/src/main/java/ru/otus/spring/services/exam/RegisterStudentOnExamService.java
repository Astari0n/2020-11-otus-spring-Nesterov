package ru.otus.spring.services.exam;

import ru.otus.spring.exceptions.RegisterException;
import ru.otus.spring.model.ExamForm;
import ru.otus.spring.model.Student;

public interface RegisterStudentOnExamService {
    Student register(ExamForm exam) throws RegisterException;
}
