package ru.otus.spring.model;

import java.util.List;

public class ExamAnswerForm {

    private Student student;

    private ExamForm questionsForm;

    private List<String> answers;

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
