package ru.otus.spring.model;

public class ExamResult {

    private final Student student;

    private final int correctAnswersCount;

    private final int questionsCount;

    private final float completionPercentage;

    private final String examMark;

    public ExamResult(
        final Student student,
        final int questionsCount,
        final int correctAnswersCount,
        final float completionPercentage,
        final String examMark
    ) {
        this.student = student;
        this.correctAnswersCount = correctAnswersCount;
        this.questionsCount = questionsCount;
        this.completionPercentage = completionPercentage;
        this.examMark = examMark;
    }

    public Student getStudent() {
        return student;
    }

    public int getCorrectAnswersCount() {
        return correctAnswersCount;
    }

    public int getQuestionsCount() {
        return questionsCount;
    }

    public float getCompletionPercentage() {
        return completionPercentage;
    }

    public String getExamMark() {
        return examMark;
    }

    @Override
    public String toString() {
        return "ExamResult{" +
            "student=" + student +
            ", correctAnswersCount=" + correctAnswersCount +
            ", questionsCount=" + questionsCount +
            ", completionPercentage=" + completionPercentage +
            ", examMark='" + examMark + '\'' +
            '}';
    }
}
