package ru.otus.spring.services.exam;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.BDDMockito;

import ru.otus.spring.exceptions.IOServiceException;
import ru.otus.spring.exceptions.RegisterException;
import ru.otus.spring.model.ExamForm;
import ru.otus.spring.model.Student;
import ru.otus.spring.services.io.IOService;

@DisplayName("Класс RegisterStudentOnExamServiceImpl")
public class RegisterStudentOnExamServiceImplTest {

    @DisplayName("корректно регистрирует студента с его именем и фамилией")
    @Test
    public void shouldCorrectRegisterStudentOnExam() throws RegisterException, IOServiceException {

        final IOService ioService = BDDMockito.mock(IOService.class);

        final RegisterStudentOnExamServiceImpl registerService = BDDMockito.mock(RegisterStudentOnExamServiceImpl.class,
            BDDMockito.withSettings()
            .useConstructor(ioService)
            .defaultAnswer(BDDMockito.CALLS_REAL_METHODS)
        );

        final ExamForm examForm = BDDMockito.mock(ExamForm.class);

        final String firstName = "Ivan";
        BDDMockito.doReturn(firstName).when(ioService).read();
        Student student = registerService.register(examForm);
        Assertions.assertThat(student.getFirstName()).isEqualTo(firstName);

        final String lastName = "Petrovich";
        BDDMockito.doReturn(lastName).when(ioService).read();
        student = registerService.register(examForm);
        Assertions.assertThat(student.getLastName()).isEqualTo(lastName);
    }
}
