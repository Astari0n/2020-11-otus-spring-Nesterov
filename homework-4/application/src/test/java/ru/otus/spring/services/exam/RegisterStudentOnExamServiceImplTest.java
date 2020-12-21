package ru.otus.spring.services.exam;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.otus.spring.exceptions.IOServiceException;
import ru.otus.spring.exceptions.ExamRegisterException;
import ru.otus.spring.model.ExamForm;
import ru.otus.spring.model.Student;
import ru.otus.spring.services.io.IOService;
import ru.otus.spring.services.localization.MessageLocalizationService;

import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.doReturn;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyString;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("Класс RegisterStudentOnExamServiceImpl")
public class RegisterStudentOnExamServiceImplTest {

    @Configuration
    static class RegisterStudentOnExamServiceImplTestConfig {
        @Bean
        public RegisterStudentOnExamServiceImpl registerStudentOnExamServiceImpl(
            final IOService ioService,
            final MessageLocalizationService localizationService
        ) {
            return new RegisterStudentOnExamServiceImpl(ioService, localizationService);
        }
    }

    @Autowired
    private RegisterStudentOnExamServiceImpl registerService;

    @MockBean
    private IOService ioService;

    @MockBean
    private MessageLocalizationService localizationService;

    @Test
    @DisplayName("корректно регистрирует студента с его именем и фамилией")
    public void shouldCorrectRegisterStudentOnExam() throws ExamRegisterException, IOServiceException {
        final ExamForm examForm = mock(ExamForm.class);

        doReturn("non important text").when(localizationService).getText(anyString(), any());

        doReturn("Ivan").when(ioService).readNonEmptyWithInfo(anyString(), anyString());
        Student student = registerService.register(examForm);
        assertThat(student.getFirstName()).isEqualTo("Ivan");

        doReturn("Petrovich").when(ioService).readNonEmptyWithInfo(anyString(), anyString());
        student = registerService.register(examForm);
        assertThat(student.getLastName()).isEqualTo("Petrovich");
    }
}
