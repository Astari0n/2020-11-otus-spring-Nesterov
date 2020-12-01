package ru.otus.spring.interactors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import ru.otus.spring.exceptions.QuestionInteractException;
import ru.otus.spring.exceptions.QuestionLoadException;
import ru.otus.spring.loaders.QuestionLoader;
import ru.otus.spring.model.Question;

import java.io.PrintStream;
import java.util.Collections;

@DisplayName("Класс ConsolePrintQuestionInteractor")
public class ConsolePrintQuestionInteractorTest {

    @DisplayName("Должен вызвать System.out.println с переданным в вызов println вопросом")
    @Test
    public void should_Call_InteractWithQuestion() throws QuestionInteractException, QuestionLoadException {
        final QuestionLoader questionLoader = Mockito.mock(QuestionLoader.class);
        Mockito.when(questionLoader.load()).thenReturn(Collections.singletonList(Mockito.mock(Question.class)));

        final AbstractQuestionInteractor interactor = Mockito.mock(ConsolePrintQuestionInteractor.class,
            Mockito.withSettings().useConstructor(questionLoader).defaultAnswer(Mockito.CALLS_REAL_METHODS));

        PrintStream out = Mockito.mock(PrintStream.class);
        System.setOut(out);

        final Question question = Mockito.mock(Question.class);
        interactor.interactWithQuestion(question);
        Mockito.verify(out, Mockito.atLeastOnce()).println(question);
    }

    @DisplayName("Должен выбрасывать QuestionInteractException если вопрос == null")
    @Test
    public void should_Throw() {
        final AbstractQuestionInteractor interactor = Mockito.mock(ConsolePrintQuestionInteractor.class, Mockito.CALLS_REAL_METHODS);
        Assertions.assertThrows(QuestionInteractException.class, () -> interactor.interactWithQuestion(null));
    }
}
