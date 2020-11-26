package ru.otus.spring.interactors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.otus.spring.model.Question;
import ru.otus.spring.parsers.QuestionParserCsv;
import ru.otus.spring.parsers.QuestionParser;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Класс ConsoleInteractor")
public class ConsoleInteractorTest {

    private Resource validResource;

    private QuestionParser parser;

    @BeforeEach
    void beforeEach() {
        this.validResource = new ClassPathResource("test/questions_test.csv");
        this.parser = new QuestionParserCsv(validResource);
    }

    @DisplayName("Корректно создается конструктором")
    @Test
    void shouldHaveCorrectConstructor() throws IOException {
        final ConsoleInteractor interactor = new ConsoleInteractor(parser);

        final List<Question> expectedQuestions = parser.parse();
        final List<Question> actualQuestions = interactor.getQuestions();

        compareQuestionLists(expectedQuestions, actualQuestions);
    }

    private void compareQuestionLists(final List<Question> expectedQuestions, final List<Question> actualQuestions) {
        assertEquals(expectedQuestions.size(), actualQuestions.size());

        for (int i = 0; i < expectedQuestions.size(); i++) {
            final Question expectedQuestion = expectedQuestions.get(i);
            final Question actualQuestion = actualQuestions.get(i);
            compareQuestions(expectedQuestion, actualQuestion);
        }
    }

    private void compareQuestions(final Question expectedQuestion, final Question actualQuestion) {
        assertEquals(expectedQuestion.toString(), actualQuestion.toString());
        assertEquals(expectedQuestion.getQuestion(), actualQuestion.getQuestion());

        final List<String> expectedAnswers = expectedQuestion.getAnswers();
        final List<String> actualAnswers = actualQuestion.getAnswers();

        compareAnswerLists(expectedAnswers, actualAnswers, expectedQuestion, actualQuestion);
    }

    private void compareAnswerLists(
        final List<String> expectedAnswers,
        final List<String> actualAnswers,
        final Question expectedQuestion,
        final Question actualQuestion
    ) {
        assertEquals(expectedAnswers.size(), actualAnswers.size());

        for (int i = 0; i < expectedAnswers.size(); i++) {
            final String expectedAnswer = expectedAnswers.get(i);
            final String actualAnswer = actualAnswers.get(i);
            compareAnswers(expectedAnswer, actualAnswer, expectedQuestion, actualQuestion);
        }
    }

    private void compareAnswers(
        final String expectedAnswer,
        final String actualAnswer,
        final Question expectedQuestion,
        final Question actualQuestion
    ) {
        assertEquals(expectedAnswer, actualAnswer);
        assertTrue(actualQuestion.isAnswerCorrect(expectedAnswer));
        assertTrue(expectedQuestion.isAnswerCorrect(actualAnswer));
    }
}
