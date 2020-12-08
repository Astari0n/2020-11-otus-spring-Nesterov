package ru.otus.spring.model;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


@DisplayName("Класс Question")
class QuestionTest {

    private Question question;
    private String inputQuestion;
    private List<String> answers;

    @BeforeEach
    void beforeEach() {
        this.inputQuestion = "Ответ на данный вопрос да или нет";

        this.answers = new ArrayList<>();
        answers.add("Да");
        answers.add("НЕТ");

        this.question = new Question(inputQuestion, answers);
    }

    @DisplayName("корректно создается конструктором")
    @Test
    void shouldHaveCorrectConstructor() {
        Assertions.assertThat(question.getQuestion()).isEqualTo(inputQuestion);
        Assertions.assertThat(question.getAnswers()).isEqualTo(answers);
    }

    @DisplayName("выводит вопрос в текстовом виде")
    @Test
    void shouldHaveCorrectToString() {
        Assertions.assertThat(question.toString()).isEqualTo(inputQuestion);
    }

    @DisplayName("проверяет неверный ответ на вопрос в любом регистре")
    @Test
    void shouldHaveIncorrectAnswerResult() {
        Assertions.assertThat(question.isAnswerCorrect("неверный ответ")).isFalse();
        Assertions.assertThat(question.isAnswerCorrect("даа")).isFalse();
        Assertions.assertThat(question.isAnswerCorrect("не")).isFalse();
        Assertions.assertThat(question.isAnswerCorrect("НЕЕТ")).isFalse();
    }

    @DisplayName("проверяет верный ответ на вопрос в любом регистре")
    @Test
    void shouldHaveCorrectAnswerResult() {
        Assertions.assertThat(question.isAnswerCorrect("да")).isTrue();
        Assertions.assertThat(question.isAnswerCorrect("ДА")).isTrue();
        Assertions.assertThat(question.isAnswerCorrect("нет")).isTrue();
        Assertions.assertThat(question.isAnswerCorrect("Нет")).isTrue();
        Assertions.assertThat(question.isAnswerCorrect("НЕТ")).isTrue();
    }
}
