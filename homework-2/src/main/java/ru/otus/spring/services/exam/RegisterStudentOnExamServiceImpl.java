package ru.otus.spring.services.exam;

import org.springframework.stereotype.Service;

import ru.otus.spring.exceptions.IOServiceException;
import ru.otus.spring.exceptions.ExamRegisterException;

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
    public Student register(final ExamForm exam) throws ExamRegisterException {
        try {
            return registerStudent(exam);
        } catch (final IOServiceException e) {
            throw new ExamRegisterException(e);
        }
    }

    public Student registerStudent(final ExamForm examForm) throws IOServiceException {
        io.printf(
            "Hello! To pass the exam you need to answer %d out of %d questions!",
            examForm.getAmountOfCorrectAnswersToPassExam(),
            examForm.getQuestions().size()
        );
        io.println();

        final String firstName = io.readNonEmptyWithInfo(
            "Write your first name: ",
            "Error! Firstname is empty!");

        final String lastName = io.readNonEmptyWithInfo(
            String.format("%s, write your last name: ", firstName),
            "Error! Lastname is empty!");

        io.println();
        io.printf("Welcome, %s %s! Creating student session...", firstName, lastName);
        io.println();

        return new Student(firstName, lastName);
    }
}
