package ru.otus.spring.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class ExamResult {

    @Getter
    private final Student student;

    @Getter
    private final int correctAnswersCount;

    @Getter
    private final int questionsCount;

    @Getter
    private final float completionPercentage;

    @Getter
    private final String examMark;
}
