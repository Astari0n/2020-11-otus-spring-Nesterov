package ru.otus.spring.services.exam;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.BDDMockito;

import ru.otus.spring.exceptions.ExamException;
import ru.otus.spring.model.ExamAnswerForm;
import ru.otus.spring.model.ExamForm;
import ru.otus.spring.model.Question;
import ru.otus.spring.services.questions.QuestionAnswerService;

import java.util.Collections;

@DisplayName("Класс Examiner")
public class ExaminerTest {

    private ExamFormPreparerService examFormPreparerService;

    private RegisterStudentOnExamService registerStudentOnExamService;

    private QuestionAnswerService questionAnswerService;

    private ExamEvaluationService examEvaluationService;

    private ExamResultDisplayService examResultDisplayService;

    @BeforeEach
    public void beforeEach() {
        this.examFormPreparerService = BDDMockito.mock(ExamFormPreparerService.class);
        this.registerStudentOnExamService = BDDMockito.mock(RegisterStudentOnExamService.class);
        this.questionAnswerService = BDDMockito.mock(QuestionAnswerService.class);
        this.examEvaluationService = BDDMockito.mock(ExamEvaluationService.class);
        this.examResultDisplayService = BDDMockito.mock(ExamResultDisplayService.class);
    }

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
