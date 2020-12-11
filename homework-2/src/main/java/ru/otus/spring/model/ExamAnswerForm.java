package ru.otus.spring.model;

import java.util.List;

public class ExamAnswerForm {

    private final Student student;

    private final ExamForm questionsForm;

    private final List<String> answers;

    public ExamAnswerForm(final Student student, final ExamForm questionsForm, final List<String> answers) {
        this.student = student;
        this.questionsForm = questionsForm;
        this.answers = answers;
    }

    public Student getStudent() {
        return student;
    }

    public ExamForm getQuestionsForm() {
        return questionsForm;
    }

    public List<String> getAnswers() {
        return answers;
    }
}
