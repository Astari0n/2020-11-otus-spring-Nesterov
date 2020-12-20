package ru.otus.spring.services.questions;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.BDDMockito;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.exceptions.IOServiceException;
import ru.otus.spring.exceptions.QuestionAnswerException;
import ru.otus.spring.model.Question;
import ru.otus.spring.services.io.IOService;
import ru.otus.spring.services.localization.MessageLocalizationService;

@SpringBootTest
@DisplayName("Класс QuestionsInteractionServiceImpl")
public class QuestionAnswerServiceImplTest {

    @MockBean
    private MessageLocalizationService localizationService;

    @MockBean
    private IOService ioService;

    @DisplayName("выбрасывает InteractionException при попытке взаимодействовать с null вопросом")
    @Test
    public void shouldThrowInteractionExceptionWhenQuestionIsNull() {
        final QuestionAnswerServiceImpl questionAnswerService = BDDMockito.mock(QuestionAnswerServiceImpl.class, BDDMockito.CALLS_REAL_METHODS);

        Assertions.assertThatThrownBy(() -> questionAnswerService.answer(null)).isInstanceOf(QuestionAnswerException.class);
    }

    @DisplayName("выбрасывает InteractionException если IOService выбросил IOServiceException")
    @Test
    public void shouldThrowInteractionExceptionIfIOServiceThrownIOServiceException() throws IOServiceException {
        BDDMockito.doThrow(IOServiceException.class).when(ioService).read();
        BDDMockito.doThrow(IOServiceException.class).when(ioService).print(BDDMockito.anyString());
        BDDMockito.doThrow(IOServiceException.class).when(ioService).println(BDDMockito.anyString());
        BDDMockito.doThrow(IOServiceException.class).when(ioService).println();

        final QuestionAnswerServiceImpl questionAnswerService = new QuestionAnswerServiceImpl(ioService, localizationService);

        final Question question = BDDMockito.mock(Question.class);

        Assertions.assertThatThrownBy(() -> questionAnswerService.answer(question)).isInstanceOf(QuestionAnswerException.class);
    }
}
