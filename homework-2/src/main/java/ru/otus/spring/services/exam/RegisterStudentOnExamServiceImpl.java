package ru.otus.spring.services.exam;

import org.springframework.stereotype.Service;

import ru.otus.spring.exceptions.IOServiceException;
import ru.otus.spring.exceptions.RegisterException;

import ru.otus.spring.model.ExamForm;
import ru.otus.spring.model.Student;
import ru.otus.spring.services.io.IOService;

@Service
public class RegisterStudentOnExamServiceImpl implements RegisterStudentOnExamService {

    private final IOService io;

    public RegisterStudentOnExamServiceImpl(final IOService ioService) {
        this.io = ioService;
    }

    @Override
    public Student register(final ExamForm exam) throws RegisterException {
        try {
            return registerStudent(exam);
        } catch (final IOServiceException e) {
            throw new RegisterException(e);
        }
    }

    public Student registerStudent(final ExamForm examForm) throws IOServiceException {
        io.printf(
            "Hello! To pass the exam you need to answer %d out of %d questions!",
            examForm.getAmountOfCorrectAnswersToPassExam(),
            examForm.getQuestions().size()
        );
        io.println();

        final String firstName = getNonEmptyString(
            "Write your first name: ",
            "Error! Firstname is empty!");

        final String lastName = getNonEmptyString(
            String.format("%s, write your last name: ", firstName),
            "Error! Lastname is empty!");

        io.println();
        io.printf("Welcome, %s %s! Creating student session...", firstName, lastName);
        io.println();

        return new Student(firstName, lastName);
    }

    private String getNonEmptyString(final String printText, final String errorMessage) throws IOServiceException {
        io.print(printText);
        String result = io.read();

        while (result.isEmpty()) {
            io.println(errorMessage);
            io.print(printText);
            result = io.read();
        }
        return result;
    }
}
