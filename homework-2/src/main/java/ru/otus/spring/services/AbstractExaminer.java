package ru.otus.spring.services;

import ru.otus.spring.exceptions.EvaluationException;
import ru.otus.spring.exceptions.ExaminationException;
import ru.otus.spring.exceptions.RegisterException;
import ru.otus.spring.exceptions.LoadException;

import ru.otus.spring.loaders.Loader;

import ru.otus.spring.model.Exam;
import ru.otus.spring.model.ExamResult;
import ru.otus.spring.model.Question;
import ru.otus.spring.model.User;

import java.util.List;

public abstract class AbstractExaminer<U extends User, Q extends Question, R extends ExamResult> {

    protected final ModifierService<List<Q>> questionModifierService;

    protected final RegisterService<U, Exam<Q>> registerService;

    protected final EvaluationService<U, R> evaluationService;

    protected final Exam<Q> exam;

    public AbstractExaminer(
        final Loader<Q> questionLoader,
        final ModifierService<List<Q>> modifierService,
        final RegisterService<U, Exam<Q>> registerService,
        final EvaluationService<U, R> evaluationService,
        final int numberOfQuestionsToAsk,
        final int numberOfQuestionsToPassTheTest
    ) throws LoadException {
        this.questionModifierService = modifierService;
        this.registerService = registerService;
        this.evaluationService = evaluationService;
        this.exam = prepareExam(questionLoader, numberOfQuestionsToAsk, numberOfQuestionsToPassTheTest);
    }

    abstract protected R processExam(final List<Q> question) throws ExaminationException;

    protected U registerStudentOnExam(final Exam<Q> exam) throws RegisterException {
        return registerService.register(exam);
    }

    protected void evaluateExamResult(final U user, final R result) throws EvaluationException {
        evaluationService.evaluate(user, result);
    }

    public void exam() throws RegisterException, ExaminationException, EvaluationException {
        final U student = registerStudentOnExam(exam);
        final R examResult = processExam(getQuestions());
        evaluateExamResult(student, examResult);
    }

    private Exam<Q> prepareExam(
        final Loader<Q> questionLoader,
        final int numberOfQuestionsToAsk,
        final int numberOfQuestionsToPassTheTest
    ) throws LoadException {
        final List<Q> questions = questionLoader.load();
        return new Exam<>(questions, Math.min(questions.size(), numberOfQuestionsToAsk), numberOfQuestionsToPassTheTest);
    }

    protected List<Q> getQuestions() {
        return questionModifierService.modify(exam.getQuestions()).subList(0, exam.getNumberOfQuestionsToAsk());
    }
}
