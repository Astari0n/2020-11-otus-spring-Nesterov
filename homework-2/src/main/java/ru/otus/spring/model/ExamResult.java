package ru.otus.spring.model;

public class ExamResult {

    private final int correctCount;

    private final int askedCount;

    private final int countToPassTheTest;

    public ExamResult(int correctCount, int askedCount, int countToPassTheTest) {
        this.correctCount = correctCount;
        this.askedCount = askedCount;
        this.countToPassTheTest = countToPassTheTest;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public int getAskedCount() {
        return askedCount;
    }

    public int getCountToPassTheTest() {
        return countToPassTheTest;
    }

    @Override
    public String toString() {
        return "ExamResult{" +
            "correctCount=" + correctCount +
            ", askedCount=" + askedCount +
            ", countToPassTheTest=" + countToPassTheTest +
            '}';
    }
}
