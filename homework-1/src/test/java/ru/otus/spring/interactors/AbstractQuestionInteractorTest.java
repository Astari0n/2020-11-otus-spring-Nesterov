package ru.otus.spring.interactors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import ru.otus.spring.exceptions.QuestionInteractException;
import ru.otus.spring.exceptions.QuestionLoadException;
import ru.otus.spring.loaders.QuestionLoader;
import ru.otus.spring.model.Question;

import java.util.Collections;

@SuppressWarnings("ResultOfMethodCallIgnored")
@DisplayName("Класс AbstractQuestionInteractor")
public class AbstractQuestionInteractorTest {

    @DisplayName("В конструкторе один раз вызывается метод загрузчика вопросов")
    @Test
    public void should_Call_QuestionLoader_Method() throws QuestionLoadException {
        final QuestionLoader questionLoader = Mockito.mock(QuestionLoader.class);

        Mockito.mock(AbstractQuestionInteractor.class, Mockito.withSettings().useConstructor(questionLoader));

        Mockito.verify(questionLoader, Mockito.times(1)).load();
    }

    @DisplayName("Должен вызвать interactWithQuestion для каждого отдельного вопроса")
    @Test
    public void should_Call_InteractWithQuestion() throws QuestionInteractException, QuestionLoadException {
        final QuestionLoader questionLoader = Mockito.mock(QuestionLoader.class);
        Mockito.when(questionLoader.load()).thenReturn(Collections.singletonList(Mockito.mock(Question.class)));

        final AbstractQuestionInteractor interactor = Mockito.mock(AbstractQuestionInteractor.class,
            Mockito.withSettings().useConstructor(questionLoader).defaultAnswer(Mockito.CALLS_REAL_METHODS));

        interactor.interact();
        Mockito.verify(interactor, Mockito.atLeastOnce()).interactWithQuestion(Mockito.any(Question.class));
    }
}
