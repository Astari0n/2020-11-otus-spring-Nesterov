package ru.otus.spring.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @DisplayName("Корректно создается конструктором")
    @Test
    void shouldHaveCorrectConstructor() {
        assertEquals(inputQuestion, question.getQuestion());
        assertEquals(answers, question.getAnswers());
    }

    @DisplayName("Выводит вопрос в текстовом виде")
    @Test
    void shouldHaveCorrectToString() {
        assertEquals(inputQuestion, question.toString());
    }

    @DisplayName("Проверяет неверный ответ на вопрос в любом регистре")
    @Test
    void shouldHaveIncorrectAnswerResult() {
        assertFalse(question.isAnswerCorrect("неверный ответ"));
        assertFalse(question.isAnswerCorrect("даа"));
        assertFalse(question.isAnswerCorrect("не"));
        assertFalse(question.isAnswerCorrect("НЕЕТ"));
    }

    @DisplayName("Проверяет верный ответ на вопрос в любом регистре")
    @Test
    void shouldHaveCorrectAnswerResult() {
        assertTrue(question.isAnswerCorrect("да"));
        assertTrue(question.isAnswerCorrect("ДА"));
        assertTrue(question.isAnswerCorrect("нет"));
        assertTrue(question.isAnswerCorrect("Нет"));
        assertTrue(question.isAnswerCorrect("НЕТ"));
    }
}
