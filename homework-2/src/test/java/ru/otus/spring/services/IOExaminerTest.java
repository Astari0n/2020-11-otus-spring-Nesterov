package ru.otus.spring.services;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import ru.otus.spring.exceptions.ExaminationException;
import ru.otus.spring.exceptions.LoadException;
import ru.otus.spring.loaders.Loader;
import ru.otus.spring.model.ExamResult;
import ru.otus.spring.model.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DisplayName("Класс IOExaminer")
public class IOExaminerTest {

    @DisplayName("после экзамена выдает корректный результат")
    @Test
    public void should_return_correct_exam_result() throws ExaminationException, IOException, LoadException {

        final int howMuchToAsk = 10;
        final int howMuchToPassExam = 2;

        final IOService ioService = Mockito.mock(IOService.class);

        final Loader questionLoader = Mockito.mock(Loader.class);

        final ModifierService modifierService = Mockito.mock(ModifierService.class);

        final RegisterService registerService = Mockito.mock(RegisterService.class);

        final EvaluationService evaluationService = Mockito.mock(EvaluationService.class);

        final IOExaminer examiner = Mockito.mock(IOExaminer.class, Mockito.withSettings()
            .useConstructor(ioService, questionLoader, modifierService, registerService, evaluationService, howMuchToAsk, howMuchToPassExam)
            .defaultAnswer(Mockito.CALLS_REAL_METHODS)
        );

        final List<Question> questionList = new ArrayList<>(Arrays.asList(
            new Question("q1", new ArrayList<>(Arrays.asList("a1_1", "a1_2", "a1_3"))),
            new Question("q2", new ArrayList<>(Arrays.asList("a2_1", "a2_2", "a2_3"))),
            new Question("q3", new ArrayList<>(Arrays.asList("a3_1", "a3_2", "a3_3"))),
            new Question("q3", new ArrayList<>(Arrays.asList("a4_1", "a4_2", "a4_3")))
        ));

        Mockito.when(questionLoader.load()).thenReturn(questionList);

        Mockito.when(ioService.read()).thenReturn("a3_2");
        final ExamResult examResultSuccess = examiner.processExam(questionList);

        Assertions.assertThat(examResultSuccess.getCorrectCount()).isEqualTo(1);
        Assertions.assertThat(examResultSuccess.getCountToPassTheTest()).isEqualTo(howMuchToPassExam);

        Mockito.when(ioService.read()).thenReturn("invalid answer");
        final ExamResult examResultError = examiner.processExam(questionList);

        Assertions.assertThat(examResultError.getCorrectCount()).isEqualTo(0);
        Assertions.assertThat(examResultError.getCountToPassTheTest()).isEqualTo(howMuchToPassExam);
    }
}
