package ru.otus.spring.services.exam;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.otus.spring.exceptions.ExamException;
import ru.otus.spring.model.ExamAnswerForm;
import ru.otus.spring.model.ExamForm;
import ru.otus.spring.model.Question;
import ru.otus.spring.services.questions.QuestionAnswerService;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.doReturn;

@SpringBootTest
@DisplayName("Класс Examiner")
public class ExaminerTest {

    @Configuration
    static class ExaminerTestConfig {
        @Bean
        public Examiner examiner(
            final ExamFormPreparerService examFormPreparerService,
            final RegisterStudentOnExamService registerStudentOnExamService,
            final QuestionAnswerService questionAnswerService,
            final ExamEvaluationService examEvaluationService,
            final ExamResultDisplayService examResultDisplayService
        ) {
            return new Examiner(
                examFormPreparerService,
                registerStudentOnExamService,
                questionAnswerService,
                examEvaluationService,
                examResultDisplayService
            );
        }
    }

    @Autowired
    private Examiner examiner;

    @MockBean
    private ExamFormPreparerService examFormPreparerService;

    @MockBean
    private RegisterStudentOnExamService registerStudentOnExamService;

    @MockBean
    private QuestionAnswerService questionAnswerService;

    @MockBean
    private ExamEvaluationService examEvaluationService;

    @MockBean
    private ExamResultDisplayService examResultDisplayService;

    @Test
    @DisplayName("корректно взаимодействует с сервисами ")
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void shouldInteractWithServicesTheRightOrder() throws ExamException {
        final List<Question> questions = Collections.singletonList(new Question("question", Collections.singletonList("a1")));

        final ExamForm examForm = mock(ExamForm.class);

        doReturn(questions).when(examForm).getQuestions();
        doReturn(examForm).when(examFormPreparerService).prepareForm();

        examiner.exam();

        then(examFormPreparerService).should().prepareForm();
        then(registerStudentOnExamService).should().register(any(ExamForm.class));
        then(questionAnswerService).should().answer(any(Question.class));
        then(examEvaluationService).should().evaluate(any(ExamAnswerForm.class));
        then(examResultDisplayService).should().display(any());
    }
}
