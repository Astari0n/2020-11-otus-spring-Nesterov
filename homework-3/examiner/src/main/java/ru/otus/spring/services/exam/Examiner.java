package ru.otus.spring.services.exam;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ru.otus.spring.exceptions.ExamEvaluationException;
import ru.otus.spring.exceptions.ExamException;
import ru.otus.spring.exceptions.ExamFormPrepareException;
import ru.otus.spring.exceptions.ExamRegisterException;
import ru.otus.spring.exceptions.ExamResultDisplayServiceException;
import ru.otus.spring.exceptions.QuestionAnswerException;

import ru.otus.spring.model.ExamAnswerForm;
import ru.otus.spring.model.ExamForm;
import ru.otus.spring.model.ExamResult;
import ru.otus.spring.model.Question;
import ru.otus.spring.model.Student;

import ru.otus.spring.services.questions.QuestionAnswerService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Examiner {

    private final ExamFormPreparerService examFormPreparerService;

    private final RegisterStudentOnExamService registerStudentOnExamService;

    private final QuestionAnswerService questionAnswerService;

    private final ExamEvaluationService examEvaluationService;

    private final ExamResultDisplayService examResultDisplayService;

    public void exam() throws ExamException {
        final ExamForm examForm = prepareExamForm();
        final Student student = prepareStudentToExam(examForm);
        final ExamAnswerForm examAnswerForm = processExam(student, examForm);
        final ExamResult examResult = evaluateExamResult(examAnswerForm);
        displayExamResult(examResult);
    }

    protected ExamForm prepareExamForm() throws ExamFormPrepareException {
        return examFormPreparerService.prepareForm();
    }

    protected Student prepareStudentToExam(final ExamForm examForm) throws ExamRegisterException {
        return registerStudentOnExamService.register(examForm);
    }

    protected ExamAnswerForm processExam(final Student student, final ExamForm examForm) throws QuestionAnswerException {
        final List<Question> questions = examForm.getQuestions();
        final List<String> answers = new ArrayList<>(questions.size());

        for (final Question question : questions) {
            final String answer = questionAnswerService.answer(question);
            answers.add(answer);
        }

        return new ExamAnswerForm(student, examForm, answers);
    }

    protected ExamResult evaluateExamResult(final ExamAnswerForm examAnswerForm) throws ExamEvaluationException {
        return examEvaluationService.evaluate(examAnswerForm);
    }

    protected void displayExamResult(final ExamResult examResult) throws ExamResultDisplayServiceException {
        examResultDisplayService.display(examResult);
    }
}
