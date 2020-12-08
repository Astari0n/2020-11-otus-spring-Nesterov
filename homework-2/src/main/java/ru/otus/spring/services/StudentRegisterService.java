package ru.otus.spring.services;

import org.springframework.stereotype.Service;
import ru.otus.spring.exceptions.RegisterException;
import ru.otus.spring.model.Exam;
import ru.otus.spring.model.Question;
import ru.otus.spring.model.User;

import java.io.IOException;

@Service
public class StudentRegisterService implements RegisterService<User, Exam<Question>> {

    private final IOService io;

    public StudentRegisterService(final IOService ioService) {
        this.io = ioService;
    }

    @Override
    public User register(final Exam<Question> exam) throws RegisterException {
        try {
            return registerStudentOnExam(exam);
        } catch (final IOException e) {
            throw new RegisterException(e);
        }
    }

    public User registerStudentOnExam(final Exam<Question> exam) throws IOException {
        io.printf(
            "Hello! To pass the exam you need to answer %d out of %d questions!",
            exam.getNumberOfQuestionsToPassTheTest(),
            exam.getNumberOfQuestionsToAsk()
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

        return new User(firstName, lastName);
    }

    private String getNonEmptyString(final String printText, final String errorMessage) throws IOException {
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
