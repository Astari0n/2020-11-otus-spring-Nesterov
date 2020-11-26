package ru.otus.spring.parsers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.otus.spring.model.Question;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс CsvQuestionParser")
public class QuestionParserCsvTest {

    private Resource validResource;

    private Resource invalidResource;

    private Resource simulatedResource;

    @BeforeEach
    void beforeEach() throws IOException {
        this.validResource = new ClassPathResource("test/questions_test.csv");
        this.invalidResource = new ClassPathResource("test/questions_test_invalid.csv");
        this.simulatedResource = generateSimulationResource();
    }

    @DisplayName("Корректно создается конструктором")
    @Test
    void shouldHaveCorrectConstructor() {
        assertEquals(validResource, new QuestionParserCsv(validResource).getResource());
        assertEquals(invalidResource, new QuestionParserCsv(invalidResource).getResource());
        assertEquals(simulatedResource, new QuestionParserCsv(simulatedResource).getResource());
        assertNull(new QuestionParserCsv(null).getResource());
    }

    @DisplayName("Корректно читает строки из существующего файла ресурса и не выбрасывает исключение")
    @Test
    void resourceShouldHaveBeenCorrectlyRead() {
        assertDoesNotThrow(() -> {
            compareCorrectAndParserResults(validResource);
            compareCorrectAndParserResults(invalidResource);
            compareCorrectAndParserResults(simulatedResource);
        });
    }

    @DisplayName("Выбрасывает исключение если передали несуществующий ресурс")
    @Test
    void shouldThrowExceptionWhileReadResource() {
        assertThrows(IOException.class, () -> compareCorrectAndParserResults(new ClassPathResource("invalid resource path here")));

        assertDoesNotThrow(() -> compareCorrectAndParserResults(new ClassPathResource("")));

        //noinspection ConstantConditions
        assertThrows(IllegalArgumentException.class, () -> compareCorrectAndParserResults(new ClassPathResource(null)));
    }

    private void compareCorrectAndParserResults(final Resource resource) throws IOException {
        QuestionParserCsv parser = new QuestionParserCsv(resource);
        final List<String> correctlyReadLines = correctReadAllLinesInResource(resource);

        parser.readAllLinesInResource();
        final List<String> parserReadLines = parser.getCsvStrings();

        assertEquals(correctlyReadLines.size(), parserReadLines.size());

        for (int i = 0; i < correctlyReadLines.size(); i++) {
            final String expected = correctlyReadLines.get(i);
            final String actual = parserReadLines.get(i);
            assertEquals(expected, actual);
        }
    }

    private List<String> correctReadAllLinesInResource(final Resource resource) throws IOException {
        final List<String> result = new ArrayList<>();
        final Scanner scanner = new Scanner(resource.getInputStream());

        while (scanner.hasNextLine()) {
            result.add(scanner.nextLine());
        }
        scanner.close();

        return result;
    }

    @DisplayName("Выбрасывает IllegalArgumentException при парсинге если ресурс имеет невалидную csv структуру")
    @Test
    void shouldThrowIllegalArgumentExceptionWhileReadResource() {
        assertThrows(IllegalArgumentException.class, new QuestionParserCsv(invalidResource)::parse);
    }

    @DisplayName("Не выбрасывает исключение при парсинге корректного csv ресурса")
    @Test
    void shouldNotThrowExceptionWhileParse() {
        assertDoesNotThrow(new QuestionParserCsv(validResource)::parse);
        assertDoesNotThrow(new QuestionParserCsv(simulatedResource)::parse);
    }

    private ByteArrayResource generateSimulationResource() throws IOException {
        final List<String> resourceList = new ArrayList<>();
        resourceList.add("1 or 2?;3");
        resourceList.add("Answer is 3, 4 and 5;3;4;5");
        resourceList.add("Answer is Moscow;Moscow");
        resourceList.add("Yes or no?;yes;NO");

        return listToInputStreamResource(resourceList);
    }

    @DisplayName("Корректно парсит симулированный ресурс на вопрос и ответы к нему")
    @Test
    void shouldHaveCorrectParseToQuestionAndAnswers() throws IOException {
        final QuestionParserCsv parser = new QuestionParserCsv(simulatedResource);
        final List<Question> questions = parser.parse();

        assertTrue(questions.get(0).isAnswerCorrect("3"));
        assertFalse(questions.get(0).isAnswerCorrect("0"));

        assertTrue(questions.get(1).isAnswerCorrect("3"));
        assertTrue(questions.get(1).isAnswerCorrect("4"));
        assertTrue(questions.get(1).isAnswerCorrect("5"));
        assertFalse(questions.get(1).isAnswerCorrect("6"));

        assertTrue(questions.get(2).isAnswerCorrect("Moscow"));
        assertFalse(questions.get(2).isAnswerCorrect("St.Petersburg"));

        assertTrue(questions.get(3).isAnswerCorrect("yes"));
        assertTrue(questions.get(3).isAnswerCorrect("no"));
        assertFalse(questions.get(3).isAnswerCorrect("or"));
    }

    private ByteArrayResource listToInputStreamResource(final List<String> list) throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (final String line : list) {
            outputStream.write(line.concat("\n").getBytes());
        }
        return new ByteArrayResource(outputStream.toByteArray());
    }
}