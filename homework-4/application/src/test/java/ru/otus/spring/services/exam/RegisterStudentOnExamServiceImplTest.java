package ru.otus.spring.services.exam;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.BDDMockito;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.exceptions.IOServiceException;
import ru.otus.spring.exceptions.ExamRegisterException;
import ru.otus.spring.model.ExamForm;
import ru.otus.spring.model.Student;
import ru.otus.spring.services.io.IOService;
import ru.otus.spring.services.localization.MessageLocalizationService;

@SpringBootTest
@DisplayName("Класс RegisterStudentOnExamServiceImpl")
public class RegisterStudentOnExamServiceImplTest {

    @MockBean
    private IOService ioService;

    @DisplayName("корректно регистрирует студента с его именем и фамилией")
    @Test
    public void shouldCorrectRegisterStudentOnExam() throws ExamRegisterException, IOServiceException {

        final MessageLocalizationService localizationService = BDDMockito.mock(MessageLocalizationService.class);
        BDDMockito.doReturn("non important text").when(localizationService).getText(BDDMockito.anyString(), BDDMockito.any());

        final RegisterStudentOnExamServiceImpl registerService = BDDMockito.mock(RegisterStudentOnExamServiceImpl.class,
            BDDMockito.withSettings()
            .useConstructor(ioService, localizationService)
            .defaultAnswer(BDDMockito.CALLS_REAL_METHODS)
        );

        final ExamForm examForm = BDDMockito.mock(ExamForm.class);

        final String firstName = "Ivan";
        BDDMockito.doReturn(firstName).when(ioService).readNonEmptyWithInfo(BDDMockito.anyString(), BDDMockito.anyString());
        Student student = registerService.register(examForm);
        Assertions.assertThat(student.getFirstName()).isEqualTo(firstName);

        final String lastName = "Petrovich";
        BDDMockito.doReturn(lastName).when(ioService).readNonEmptyWithInfo(BDDMockito.anyString(), BDDMockito.anyString());
        student = registerService.register(examForm);
        Assertions.assertThat(student.getLastName()).isEqualTo(lastName);
    }
}
