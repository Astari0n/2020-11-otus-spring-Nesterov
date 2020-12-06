package ru.otus.spring.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import ru.otus.spring.exceptions.ExamException;
import ru.otus.spring.exceptions.QuestionInteractException;
import ru.otus.spring.exceptions.QuestionLoadException;
import ru.otus.spring.loaders.QuestionLoader;
import ru.otus.spring.model.Question;

import java.util.Collections;

@SuppressWarnings("ResultOfMethodCallIgnored")
@DisplayName("Класс AbstractExamTestInteractor")
public class AbstractExamTestInteractorTest {

    @DisplayName("В конструкторе один раз вызывается метод загрузчика вопросов")
    @Test
    public void should_Call_QuestionLoader_Method() throws QuestionLoadException {
        final QuestionLoader questionLoader = Mockito.mock(QuestionLoader.class);

        Mockito.mock(AbstractExamTestInteractor.class, Mockito.withSettings().useConstructor(questionLoader, 1, 1));

        Mockito.verify(questionLoader, Mockito.times(1)).load();
    }

    @DisplayName("Должен вызвать answerTheQuestion для каждого отдельного вопроса")
    @Test
    public void should_Call_AnswerTheQuestion() throws QuestionInteractException, QuestionLoadException, ExamException {
        final QuestionLoader questionLoader = Mockito.mock(QuestionLoader.class);
        Mockito.when(questionLoader.load()).thenReturn(Collections.singletonList(Mockito.mock(Question.class)));

        final AbstractExamTestInteractor interactor = Mockito.mock(AbstractExamTestInteractor.class,
            Mockito.withSettings().useConstructor(questionLoader, 1, 1).defaultAnswer(Mockito.CALLS_REAL_METHODS));

        interactor.exam();
        Mockito.verify(interactor, Mockito.atLeastOnce()).answerTheQuestion(Mockito.any(Question.class));
    }

    @DisplayName("Должен выбрасывать QuestionInteractException если вопрос == null")
    @Test
    public void should_Throw_QuestionInteractException_If_Question_Null() throws QuestionLoadException {
        final QuestionLoader questionLoader = Mockito.mock(QuestionLoader.class);
        Mockito.when(questionLoader.load()).thenReturn(Collections.singletonList(null));

        final AbstractExamTestInteractor interactor = Mockito.mock(AbstractExamTestInteractor.class,
            Mockito.withSettings().useConstructor(questionLoader, 1, 1).defaultAnswer(Mockito.CALLS_REAL_METHODS));

        Assertions.assertThrows(QuestionInteractException.class, interactor::exam);
    }

    @DisplayName("Должен вызывать все стадии экзамена: before, answerTheQuestion, after")
    @Test
    public void should_Call_Before_AnswerTheQuestion_After() throws ExamException, QuestionInteractException, QuestionLoadException {
        final QuestionLoader questionLoader = Mockito.mock(QuestionLoader.class);
        Mockito.when(questionLoader.load()).thenReturn(Collections.singletonList(null));

        final AbstractExamTestInteractor interactor = Mockito.mock(AbstractExamTestInteractor.class,
            Mockito.withSettings().useConstructor(questionLoader, 1, 1).defaultAnswer(Mockito.CALLS_REAL_METHODS));

        Mockito.when(interactor.getQuestions()).thenReturn(Collections.singletonList(Mockito.mock(Question.class)));

        interactor.exam();

        Mockito.verify(interactor, Mockito.times(1)).beforeExam();
        Mockito.verify(interactor, Mockito.times(1)).answerTheQuestion(Mockito.any());
        Mockito.verify(interactor, Mockito.times(1)).afterExam();
    }
}
