package ru.otus.spring.services.questions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.otus.spring.exceptions.IOServiceException;
import ru.otus.spring.exceptions.QuestionAnswerException;
import ru.otus.spring.model.Question;
import ru.otus.spring.services.io.IOService;
import ru.otus.spring.services.localization.MessageLocalizationService;

import static org.mockito.BDDMockito.doThrow;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.mock;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@DisplayName("Класс QuestionsInteractionServiceImpl")
public class QuestionAnswerServiceImplTest {

    @Configuration
    static class QuestionAnswerServiceImplTestConfig {

        @MockBean
        private MessageLocalizationService localizationService;

        @Bean
        public QuestionAnswerServiceImpl questionAnswerServiceImpl(final IOService ioService) {
            return new QuestionAnswerServiceImpl(ioService, localizationService);
        }
    }

    @Autowired
    private QuestionAnswerServiceImpl questionAnswerService;

    @MockBean
    private IOService ioService;

    @Test
    @DisplayName("выбрасывает InteractionException при попытке взаимодействовать с null вопросом")
    public void shouldThrowInteractionExceptionWhenQuestionIsNull() {
        assertThatThrownBy(() -> questionAnswerService.answer(null)).isInstanceOf(QuestionAnswerException.class);
    }

    @Test
    @DisplayName("выбрасывает InteractionException если IOService выбросил IOServiceException")
    public void shouldThrowInteractionExceptionIfIOServiceThrownIOServiceException() throws IOServiceException {
        doThrow(IOServiceException.class).when(ioService).read();
        doThrow(IOServiceException.class).when(ioService).print(anyString());
        doThrow(IOServiceException.class).when(ioService).println(anyString());
        doThrow(IOServiceException.class).when(ioService).println();

        final Question question = mock(Question.class);

        assertThatThrownBy(() -> questionAnswerService.answer(question)).isInstanceOf(QuestionAnswerException.class);
    }
}
