package ru.otus.spring.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import ru.otus.spring.exceptions.ExamException;
import ru.otus.spring.exceptions.QuestionInteractException;
import ru.otus.spring.loaders.QuestionLoader;
import ru.otus.spring.model.Question;

@DisplayName("Класс IOExamTestInteractor")
public class IOExamTestInteractorTest {

    @DisplayName("Должен увеличивать answerTheQuestion если ответ на вопрос правильный")
    @Test
    public void should_Increment_AnswerTheQuestion_If_Answer_Correct() {
        final IOService ioService = Mockito.mock(IOService.class);
        final QuestionLoader questionLoader = Mockito.mock(QuestionLoader.class);

        final IOExamTestInteractor interactor = Mockito.mock(IOExamTestInteractor.class, Mockito.withSettings()
            .useConstructor(ioService, questionLoader, 1, 1)
            .defaultAnswer(Mockito.CALLS_REAL_METHODS)
        );

        int correctAnswers = interactor.getCorrectAnswers();

        final Question question = Mockito.mock(Question.class);
        Mockito.when(question.isAnswerCorrect(Mockito.any())).thenReturn(true);

        int numberOfAsks = 10;
        for (int i = 0; i < numberOfAsks; i++) {
            interactor.answerTheQuestion(question);
        }

        Assertions.assertEquals(correctAnswers + numberOfAsks, interactor.getCorrectAnswers());
    }
}
