package ru.otus.spring.services.exam;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import ru.otus.spring.exceptions.ExamEvaluationException;
import ru.otus.spring.model.ExamAnswerForm;
import ru.otus.spring.model.ExamForm;
import ru.otus.spring.model.ExamResult;
import ru.otus.spring.model.Question;
import ru.otus.spring.services.localization.MessageLocalizationService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.doReturn;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyString;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = ExamEvaluationServiceImpl.class)
@DisplayName("Класс ExamEvaluationServiceImpl")
@SuppressWarnings("ALL")
public class ExamEvaluationServiceImplTest {

    @Autowired
    private ExamEvaluationServiceImpl service;

    @MockBean
    private MessageLocalizationService localizationService;

    @BeforeEach
    public void beforeEach() {
        doReturn("exam result").when(localizationService).getText(anyString(), any());
    }

    @Test
    @DisplayName("выбрасывает EvaluationException когда количество ответов и вопросов не совпадает")
    public void shouldThrowEvaluationExceptionWhenAnswersCountLessThanQuestionsCount() {
        final List<String> questionsAnswers = Collections.singletonList("questionAnswer1");

        final List<Question> questions = Arrays.asList(
            new Question("question1", questionsAnswers),
            new Question("question2", questionsAnswers),
            new Question("question3", questionsAnswers)
        );

        final List<String> filledAnswers = Arrays.asList(
            "answer1",
            "answer2"
        );

        final ExamForm examForm = mock(ExamForm.class);
        final ExamAnswerForm examAnswerForm = mock(ExamAnswerForm.class);

        doReturn(questions).when(examForm).getQuestions();

        doReturn(examForm).when(examAnswerForm).getQuestionsForm();
        doReturn(filledAnswers).when(examAnswerForm).getAnswers();

        assertThatThrownBy(() -> service.evaluate(examAnswerForm)).isInstanceOf(ExamEvaluationException.class);
    }

    @Test
    @DisplayName("корректно оценивает форму с ответами")
    public void shouldCorrectEvaluateAnswerForm() throws ExamEvaluationException {
        final List<Question> questions = Arrays.asList(
            new Question("question1", Arrays.asList("answer1_q1", "answer2_q1", "answer3_q1")),
            new Question("question2", Arrays.asList("answer1_q2", "answer2_q2")),
            new Question("question3", Arrays.asList("answer1_q3", "answer2_q3", "answer3_q3")),
            new Question("question3", Arrays.asList("answer1_q4", "answer2_q4", "answer3_q4", "answer4_q4"))
        );

        final List<String> filledAnswers = Arrays.asList(
            "answer1",
            "answer1_q2",
            "answer3",
            "answer2_q4"
        );

        final ExamForm examForm = mock(ExamForm.class);
        final ExamAnswerForm examAnswerForm = mock(ExamAnswerForm.class);

        doReturn(questions).when(examForm).getQuestions();
        doReturn(2).when(examForm).getAmountOfCorrectAnswersToPassExam();

        doReturn(examForm).when(examAnswerForm).getQuestionsForm();
        doReturn(filledAnswers).when(examAnswerForm).getAnswers();

        doReturn("success result").when(localizationService).getText(anyString(), any());

        final ExamResult examResult = service.evaluate(examAnswerForm);

        assertThat(examResult.getCorrectAnswersCount()).isEqualTo(2);
        assertThat(examResult.getCompletionPercentage()).isEqualTo(50.0f);
        assertThat(examResult.getExamMark()).isEqualTo("success result");
    }
}
