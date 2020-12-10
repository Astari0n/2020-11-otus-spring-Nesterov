package ru.otus.spring.services.exam;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.BDDMockito;

import ru.otus.spring.exceptions.DisplayServiceException;
import ru.otus.spring.exceptions.EvaluationException;
import ru.otus.spring.exceptions.ExamFormPrepareException;
import ru.otus.spring.exceptions.InteractionException;
import ru.otus.spring.exceptions.RegisterException;

import ru.otus.spring.model.ExamAnswerForm;
import ru.otus.spring.model.ExamForm;
import ru.otus.spring.model.Question;
import ru.otus.spring.services.questions.QuestionsInteractionService;

import java.util.Collections;

@DisplayName("Класс Examiner")
public class ExaminerTest {

    private ExamFormPreparerService examFormPreparerService;

    private RegisterStudentOnExamService registerStudentOnExamService;

    private QuestionsInteractionService<String> questionsInteractionService;

    private ExamEvaluationService examEvaluationService;

    private ExamResultDisplayService examResultDisplayService;

    @BeforeEach
    public void beforeEach() {
        this.examFormPreparerService = BDDMockito.mock(ExamFormPreparerService.class);
        this.registerStudentOnExamService = BDDMockito.mock(RegisterStudentOnExamService.class);
        this.questionsInteractionService = BDDMockito.mock(QuestionsInteractionService.class);
        this.examEvaluationService = BDDMockito.mock(ExamEvaluationService.class);
        this.examResultDisplayService = BDDMockito.mock(ExamResultDisplayService.class);
    }

    @DisplayName("корректно взаимодействует с сервисами ")
    @Test
    public void shouldInteractWithServicesTheRightOrder(
    ) throws EvaluationException, DisplayServiceException, ExamFormPrepareException, RegisterException, InteractionException {
        final Examiner examiner = BDDMockito.mock(Examiner.class, BDDMockito.withSettings()
            .useConstructor(
                examFormPreparerService,
                registerStudentOnExamService,
                questionsInteractionService,
                examEvaluationService,
                examResultDisplayService
            )
            .defaultAnswer(BDDMockito.CALLS_REAL_METHODS)
        );

        final ExamForm examForm = new ExamForm(Collections.singletonList(
            new Question("question", Collections.singletonList("a1"))), 1);

        BDDMockito.given(examFormPreparerService.prepareForm()).willReturn(examForm);

        examiner.exam();

        BDDMockito.then(examFormPreparerService).should().prepareForm();
        BDDMockito.then(registerStudentOnExamService).should().register(BDDMockito.any(ExamForm.class));
        BDDMockito.then(questionsInteractionService).should().interact(BDDMockito.any(Question.class));
        BDDMockito.then(examEvaluationService).should().evaluate(BDDMockito.any(ExamAnswerForm.class));
        BDDMockito.then(examResultDisplayService).should().display(BDDMockito.any());
    }
}
