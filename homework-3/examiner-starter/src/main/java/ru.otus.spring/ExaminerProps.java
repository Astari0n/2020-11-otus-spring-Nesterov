package ru.otus.spring;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "examiner")
public class ExaminerProps {

    @Getter
    @Setter
    private int amountOfQuestionsToAsk;

    @Getter
    @Setter
    private int amountOfCorrectAnswersToPassExam;
}
