package ru.otus.spring.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@RequiredArgsConstructor
@ToString
public class ExamAnswerForm {

    @Getter
    private final Student student;

    @Getter
    private final ExamForm questionsForm;

    @Getter
    private final List<String> answers;
}
