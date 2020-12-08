package ru.otus.spring.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.spring.exceptions.RegisterException;
import ru.otus.spring.model.Exam;
import ru.otus.spring.model.User;

import java.io.IOException;

@DisplayName("Класс StudentRegisterService")
public class StudentRegisterServiceTest {

    @DisplayName("корректно регистрирует студента")
    @Test
    public void should_correct_register_student() throws RegisterException, IOException {

        final IOService ioService = Mockito.mock(IOService.class);


        final StudentRegisterService registerService = Mockito.mock(StudentRegisterService.class, Mockito.withSettings()
            .useConstructor(ioService)
            .defaultAnswer(Mockito.CALLS_REAL_METHODS)
        );

        final Exam exam = Mockito.mock(Exam.class);

        final String firstName = "Ivan";
        Mockito.when(ioService.read()).thenReturn(firstName);
        User user = registerService.register(exam);
        Assertions.assertThat(user.getFirstName()).isEqualTo(firstName);

        final String lastName = "Petrovich";
        Mockito.when(ioService.read()).thenReturn(lastName);
        user = registerService.register(exam);
        Assertions.assertThat(user.getLastName()).isEqualTo(lastName);
    }
}
