package ru.otus.spring.services.questions;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.BDDMockito;

import ru.otus.spring.exceptions.IOServiceException;
import ru.otus.spring.exceptions.InteractionException;
import ru.otus.spring.model.Question;
import ru.otus.spring.services.io.IOService;

@DisplayName("Класс QuestionsInteractionServiceImpl")
public class QuestionsInteractionServiceImplTest {

    @DisplayName("выбрасывает InteractionException при попытке взаимодействовать с null вопросом")
    @Test
    public void shouldThrowInteractionExceptionWhenQuestionIsNull() {
        final QuestionsInteractionServiceImpl service = BDDMockito.mock(QuestionsInteractionServiceImpl.class,
            BDDMockito.CALLS_REAL_METHODS
        );

        Assertions.assertThatThrownBy(() -> service.interact(null)).isInstanceOf(InteractionException.class);
    }

    @DisplayName("выбрасывает InteractionException если IOService выбросил IOServiceException")
    @Test
    public void shouldThrowInteractionExceptionIfIOServiceThrownIOServiceException() throws IOServiceException {
        final IOService ioService = BDDMockito.mock(IOService.class);

        BDDMockito.doThrow(IOServiceException.class).when(ioService).read();
        BDDMockito.doThrow(IOServiceException.class).when(ioService).print(BDDMockito.anyString());
        BDDMockito.doThrow(IOServiceException.class).when(ioService).println(BDDMockito.anyString());
        BDDMockito.doThrow(IOServiceException.class).when(ioService).println();

        final QuestionsInteractionServiceImpl service = BDDMockito.mock(QuestionsInteractionServiceImpl.class,
            BDDMockito.withSettings()
            .useConstructor(ioService)
            .defaultAnswer(BDDMockito.CALLS_REAL_METHODS)
        );

        final Question question = BDDMockito.mock(Question.class);

        Assertions.assertThatThrownBy(() -> service.interact(question)).isInstanceOf(InteractionException.class);
    }
}
