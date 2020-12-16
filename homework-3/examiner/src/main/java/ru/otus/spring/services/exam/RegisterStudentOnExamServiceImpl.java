package ru.otus.spring.services.exam;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ru.otus.spring.exceptions.IOServiceException;
import ru.otus.spring.exceptions.ExamRegisterException;

import ru.otus.spring.model.ExamForm;
import ru.otus.spring.model.Student;
import ru.otus.spring.services.io.IOService;
import ru.otus.spring.services.localization.MessageLocalizationService;

@Service
@RequiredArgsConstructor
public class RegisterStudentOnExamServiceImpl implements RegisterStudentOnExamService {

    private final IOService io;

    private final MessageLocalizationService localizationService;

    @Override
    public Student register(final ExamForm exam) throws ExamRegisterException {
        try {
            return registerStudent(exam);
        } catch (final IOServiceException e) {
            throw new ExamRegisterException(e);
        }
    }

    public Student registerStudent(final ExamForm examForm) throws IOServiceException {
        io.print(localizationService.getText("exam.info_message",
            examForm.getAmountOfCorrectAnswersToPassExam(),
            examForm.getQuestions().size()));

        io.println();

        final String firstName = io.readNonEmptyWithInfo(
            localizationService.getText("exam.write_first_name"),
            localizationService.getText("exam.write_first_name_error"));

        final String lastName = io.readNonEmptyWithInfo(
            localizationService.getText("exam.write_last_name", firstName),
            localizationService.getText("exam.write_last_name_error"));

        io.println();
        io.printf(localizationService.getText("exam.welcome_after_register", firstName, lastName));
        io.println();

        return new Student(firstName, lastName);
    }
}
