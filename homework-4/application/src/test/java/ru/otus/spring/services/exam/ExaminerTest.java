package ru.otus.spring.services.exam;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.BDDMockito;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.exceptions.ExamException;
import ru.otus.spring.model.ExamAnswerForm;
import ru.otus.spring.model.ExamForm;
import ru.otus.spring.model.Question;
import ru.otus.spring.services.questions.QuestionAnswerService;

import java.util.Collections;

@SpringBootTest
@DisplayName("Класс Examiner")
public class ExaminerTest {

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

    @DisplayName("корректно взаимодействует с сервисами ")
    @Test
    public void shouldInteractWithServicesTheRightOrder() throws ExamException {
        final Examiner examiner = new Examiner(
            examFormPreparerService,
            registerStudentOnExamService,
            questionAnswerService,
            examEvaluationService,
            examResultDisplayService
        );

        final ExamForm examForm = new ExamForm(Collections.singletonList(
            new Question("question", Collections.singletonList("a1"))), 1);

        BDDMockito.given(examFormPreparerService.prepareForm()).willReturn(examForm);

        examiner.exam();

        BDDMockito.then(examFormPreparerService).should().prepareForm();
        BDDMockito.then(registerStudentOnExamService).should().register(BDDMockito.any(ExamForm.class));
        BDDMockito.then(questionAnswerService).should().answer(BDDMockito.any(Question.class));
        BDDMockito.then(examEvaluationService).should().evaluate(BDDMockito.any(ExamAnswerForm.class));
        BDDMockito.then(examResultDisplayService).should().display(BDDMockito.any());
    }
}
