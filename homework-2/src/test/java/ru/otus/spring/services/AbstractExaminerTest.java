package ru.otus.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import ru.otus.spring.exceptions.EvaluationException;
import ru.otus.spring.exceptions.ExaminationException;
import ru.otus.spring.exceptions.LoadException;
import ru.otus.spring.exceptions.RegisterException;
import ru.otus.spring.loaders.Loader;
import ru.otus.spring.model.Question;

import java.util.Collections;

@SuppressWarnings("ResultOfMethodCallIgnored")
@DisplayName("Класс AbstractExaminer")
public class AbstractExaminerTest {

    private Loader questionLoader;

    private ModifierService modifierService;

    private RegisterService registerService;

    private EvaluationService evaluationService;

    @BeforeEach
    public void before_each() throws LoadException {
        questionLoader = Mockito.mock(Loader.class);
        Mockito.when(questionLoader.load()).thenReturn(Collections.singletonList(Mockito.mock(Question.class)));

        modifierService = Mockito.mock(ModifierService.class);
        Mockito.when(modifierService.modify(Mockito.anyList()))
            .thenReturn(Collections.singletonList(Mockito.mock(Question.class)));

        registerService = Mockito.mock(RegisterService.class);

        evaluationService = Mockito.mock(EvaluationService.class);
    }

    @DisplayName("подготавливает вопросы для экзамена")
    @Test
    public void should_call_loader_method_in_constructor() throws LoadException {
        Mockito.mock(AbstractExaminer.class, Mockito.withSettings()
            .useConstructor(questionLoader, modifierService, registerService, evaluationService, 1, 1)
        );

        Mockito.verify(questionLoader, Mockito.atLeastOnce()).load();
    }

    @DisplayName("регистрирует студента на экзамене")
    @Test
    public void should_register_student_on_exam() throws RegisterException, ExaminationException, EvaluationException {
        final AbstractExaminer examiner = Mockito.mock(AbstractExaminer.class, Mockito.withSettings()
            .useConstructor(questionLoader, modifierService, registerService, evaluationService, 1, 1)
            .defaultAnswer(Mockito.CALLS_REAL_METHODS)
        );

        examiner.exam();
        Mockito.verify(registerService, Mockito.atLeastOnce()).register(Mockito.any());
    }

    @DisplayName("проводит экзамен")
    @Test
    public void should_process_exam() throws RegisterException, ExaminationException, EvaluationException {
        final AbstractExaminer examiner = Mockito.mock(AbstractExaminer.class, Mockito.withSettings()
            .useConstructor(questionLoader, modifierService, registerService, evaluationService, 1, 1)
            .defaultAnswer(Mockito.CALLS_REAL_METHODS)
        );

        examiner.exam();
        Mockito.verify(examiner, Mockito.atLeastOnce()).processExam(Mockito.anyList());
    }

    @DisplayName("показывает результат по прохождению экзамена")
    @Test
    public void should_evaluate_exam_results() throws RegisterException, ExaminationException, EvaluationException {
        final AbstractExaminer examiner = Mockito.mock(AbstractExaminer.class, Mockito.withSettings()
            .useConstructor(questionLoader, modifierService, registerService, evaluationService, 1, 1)
            .defaultAnswer(Mockito.CALLS_REAL_METHODS)
        );

        examiner.exam();
        Mockito.verify(evaluationService, Mockito.atLeastOnce()).evaluate(Mockito.any(), Mockito.any());
    }
}
