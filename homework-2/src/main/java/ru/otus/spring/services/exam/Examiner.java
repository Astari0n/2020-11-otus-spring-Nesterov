package ru.otus.spring.services.exam;

import org.springframework.stereotype.Service;

import ru.otus.spring.exceptions.DisplayServiceException;
import ru.otus.spring.exceptions.EvaluationException;
import ru.otus.spring.exceptions.ExamFormPrepareException;
import ru.otus.spring.exceptions.InteractionException;
import ru.otus.spring.exceptions.RegisterException;

import ru.otus.spring.model.ExamAnswerForm;
import ru.otus.spring.model.ExamForm;
import ru.otus.spring.model.ExamResult;
import ru.otus.spring.model.Question;
import ru.otus.spring.model.Student;
import ru.otus.spring.services.questions.QuestionsInteractionService;

import java.util.ArrayList;
import java.util.List;

@Service
public class Examiner {

    private final ExamFormPreparerService examFormPreparerService;

    private final RegisterStudentOnExamService registerStudentOnExamService;

    private final QuestionsInteractionService<String> questionsInteractionService;

    private final ExamEvaluationService examEvaluationService;

    private final ExamResultDisplayService examResultDisplayService;

    public Examiner(
        final ExamFormPreparerService examFormPreparerService,
        final RegisterStudentOnExamService registerStudentOnExamService,
        final QuestionsInteractionService<String> questionsInteractionService,
        final ExamEvaluationService examEvaluationService,
        final ExamResultDisplayService examResultDisplayService
    ) {
        this.examFormPreparerService = examFormPreparerService;
        this.registerStudentOnExamService = registerStudentOnExamService;
        this.questionsInteractionService = questionsInteractionService;
        this.examEvaluationService = examEvaluationService;
        this.examResultDisplayService = examResultDisplayService;
    }

    public void exam(
    ) throws ExamFormPrepareException, RegisterException, EvaluationException, DisplayServiceException, InteractionException {
        final ExamForm examForm = prepareExamForm();
        final Student student = prepareStudentToExam(examForm);
        final ExamAnswerForm examAnswerForm = processExam(student, examForm);
        final ExamResult examResult = evaluateExamResult(examAnswerForm);
        displayExamResult(examResult);
    }

    protected ExamForm prepareExamForm() throws ExamFormPrepareException {
        return examFormPreparerService.prepareForm();
    }

    protected Student prepareStudentToExam(final ExamForm examForm) throws RegisterException {
        return registerStudentOnExamService.register(examForm);
    }

    protected ExamAnswerForm processExam(final Student student, final ExamForm examForm) throws InteractionException {
        final List<Question> questions = examForm.getQuestions();
        final List<String> answers = new ArrayList<>(questions.size());

        for (final Question question : questions) {
            final String answer = questionsInteractionService.interact(question);
            answers.add(answer);
        }

        return new ExamAnswerForm(student, examForm, answers);
    }

    protected ExamResult evaluateExamResult(final ExamAnswerForm examAnswerForm) throws EvaluationException {
        return examEvaluationService.evaluate(examAnswerForm);
    }

    protected void displayExamResult(final ExamResult examResult) throws DisplayServiceException {
        examResultDisplayService.display(examResult);
    }
}
