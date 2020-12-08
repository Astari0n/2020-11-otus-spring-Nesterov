package ru.otus.spring.loaders;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import ru.otus.spring.exceptions.LoadException;
import ru.otus.spring.model.Question;
import ru.otus.spring.providers.ResourceProvider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DisplayName("Класс QuestionLoaderCsv")
public class QuestionLoaderCsvTest {

    @DisplayName("корректно читает строки из существующего файла ресурса и не выбрасывает исключение")
    @Test
    public void should_Correctly_Read_Resource() {
        final List<String> resourceList = new ArrayList<>(Arrays.asList(
            "1 or 2?;3",
            "Answer is 3, 4 and 5;3;4;5",
            "Answer is Moscow;Moscow",
            "Yes or no?;yes;NO"
        ));

        final ResourceProvider resourceProvider = Mockito.mock(ResourceProvider.class);
        Mockito.when(resourceProvider.getResource()).thenReturn(listToMockResource(resourceList));

        final QuestionLoaderCsv loaderCsv = Mockito.mock(QuestionLoaderCsv.class,
            Mockito.withSettings().useConstructor(resourceProvider).defaultAnswer(Mockito.CALLS_REAL_METHODS));

        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(resourceList, loaderCsv.readAllLinesInResource()));
    }

    @DisplayName("выбрасывает QuestionLoadException если при чтении из файла ресурса был выброшен IOException")
    @Test
    public void should_Throw_While_Read_Resource() throws IOException {
        final Resource resource = Mockito.mock(Resource.class);
        Mockito.when(resource.getInputStream()).thenThrow(new IOException());

        final ResourceProvider resourceProvider = Mockito.mock(ResourceProvider.class);
        Mockito.when(resourceProvider.getResource()).thenReturn(resource);

        final QuestionLoaderCsv loaderCsv = Mockito.mock(QuestionLoaderCsv.class,
            Mockito.withSettings().useConstructor(resourceProvider).defaultAnswer(Mockito.CALLS_REAL_METHODS));

        Assertions.assertThrows(LoadException.class, loaderCsv::readAllLinesInResource);
    }


    @DisplayName("выбрасывает QuestionLoadException при парсинге если ресурс имеет невалидную csv структуру")
    @Test
    public void should_Throw_QuestionLoadException_On_Invalid_CSV_Structure() {
        final List<String> resourceList = new ArrayList<>(Arrays.asList(
            "1 or 2?;3",
            "Answer is 3, 4 and 5;3;4;5",
            "invalid line in resource", // invalid line
            "Answer is Moscow;Moscow",
            "Yes or no?;yes;NO"
        ));

        final ResourceProvider resourceProvider = Mockito.mock(ResourceProvider.class);
        Mockito.when(resourceProvider.getResource()).thenReturn(listToMockResource(resourceList));

        final QuestionLoaderCsv loaderCsv = Mockito.mock(QuestionLoaderCsv.class,
            Mockito.withSettings().useConstructor(resourceProvider).defaultAnswer(Mockito.CALLS_REAL_METHODS));

        Assertions.assertThrows(LoadException.class, loaderCsv::load);
    }

    @DisplayName("корректно парсит симулированный ресурс на вопрос и ответы к нему")
    @Test
    public void shouldHaveCorrectParseToQuestionAndAnswers() throws LoadException {
        final List<String> resourceList = new ArrayList<>(Arrays.asList(
            "1 or 2?;3",
            "Answer is 3, 4 and 5;3;4;5",
            "Answer is Moscow;Moscow",
            "Yes or no?;yes;NO"
        ));

        final ResourceProvider resourceProvider = Mockito.mock(ResourceProvider.class);
        Mockito.when(resourceProvider.getResource()).thenReturn(listToMockResource(resourceList));

        final QuestionLoaderCsv loaderCsv = Mockito.mock(QuestionLoaderCsv.class,
            Mockito.withSettings().useConstructor(resourceProvider).defaultAnswer(Mockito.CALLS_REAL_METHODS));

        final List<Question> questions = loaderCsv.load();

        Assertions.assertTrue(questions.get(0).isAnswerCorrect("3"));
        Assertions.assertFalse(questions.get(0).isAnswerCorrect("0"));

        Assertions.assertTrue(questions.get(1).isAnswerCorrect("3"));
        Assertions.assertTrue(questions.get(1).isAnswerCorrect("4"));
        Assertions.assertTrue(questions.get(1).isAnswerCorrect("5"));
        Assertions.assertFalse(questions.get(1).isAnswerCorrect("6"));

        Assertions.assertTrue(questions.get(2).isAnswerCorrect("Moscow"));
        Assertions.assertFalse(questions.get(2).isAnswerCorrect("St.Petersburg"));

        Assertions.assertTrue(questions.get(3).isAnswerCorrect("yes"));
        Assertions.assertTrue(questions.get(3).isAnswerCorrect("no"));
        Assertions.assertFalse(questions.get(3).isAnswerCorrect("or"));
    }

    private ByteArrayResource listToMockResource(final List<String> list) {
        try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            for (final String line : list) {
                outputStream.write(line.concat("\n").getBytes());
            }
            return new ByteArrayResource(outputStream.toByteArray());
        } catch (final IOException ioException) {
            ioException.printStackTrace();
            return Mockito.mock(ByteArrayResource.class);
        }
    }
}
