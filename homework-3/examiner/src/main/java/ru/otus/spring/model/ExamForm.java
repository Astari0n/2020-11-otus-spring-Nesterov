package ru.otus.spring.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@RequiredArgsConstructor
@ToString
public class ExamForm {

    @Getter
    private final List<Question> questions;

    @Getter
    private final int amountOfCorrectAnswersToPassExam;
}
