package ru.otus.spring.services.exam;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.BDDMockito;

import ru.otus.spring.exceptions.ExamEvaluationException;
import ru.otus.spring.model.ExamAnswerForm;
import ru.otus.spring.model.ExamForm;
import ru.otus.spring.model.ExamResult;
import ru.otus.spring.model.Question;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@DisplayName("Класс ExamEvaluationServiceImpl")
public class ExamEvaluationServiceImplTest {

    @DisplayName("выбрасывает EvaluationException когда количество ответов и вопросов не совпадает")
    @Test
    public void shouldThrowEvaluationExceptionWhenAnswersCountLessThanQuestionsCount() {
        final ExamEvaluationServiceImpl service = BDDMockito.mock(ExamEvaluationServiceImpl.class, BDDMockito.CALLS_REAL_METHODS);

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

        final ExamForm examForm = BDDMockito.mock(ExamForm.class);
        BDDMockito.given(examForm.getQuestions()).willReturn(questions);

        final ExamAnswerForm examAnswerForm = BDDMockito.mock(ExamAnswerForm.class);
        BDDMockito.given(examAnswerForm.getQuestionsForm()).willReturn(examForm);
        BDDMockito.given(examAnswerForm.getAnswers()).willReturn(filledAnswers);

        Assertions.assertThatThrownBy(() -> service.evaluate(examAnswerForm)).isInstanceOf(ExamEvaluationException.class);
    }

    @DisplayName("корректно оценивает форму с ответами")
    @Test
    public void shouldCorrectEvaluateAnswerForm() throws ExamEvaluationException {
        final ExamEvaluationServiceImpl service = BDDMockito.mock(ExamEvaluationServiceImpl.class, BDDMockito.CALLS_REAL_METHODS);

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

        final ExamForm examForm = BDDMockito.mock(ExamForm.class);
        BDDMockito.given(examForm.getQuestions()).willReturn(questions);
        BDDMockito.given(examForm.getAmountOfCorrectAnswersToPassExam()).willReturn(2);

        final ExamAnswerForm examAnswerForm = BDDMockito.mock(ExamAnswerForm.class);
        BDDMockito.given(examAnswerForm.getQuestionsForm()).willReturn(examForm);
        BDDMockito.given(examAnswerForm.getAnswers()).willReturn(filledAnswers);

        final ExamResult examResult = service.evaluate(examAnswerForm);

        Assertions.assertThat(examResult.getCorrectAnswersCount()).isEqualTo(2);
        Assertions.assertThat(examResult.getCompletionPercentage()).isEqualTo(50.0f);
        Assertions.assertThat(examResult.getExamMark()).isEqualTo("PASSED");
    }
}
