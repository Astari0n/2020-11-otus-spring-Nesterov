package ru.otus.spring.loaders;

import ru.otus.spring.exceptions.QuestionLoadException;
import ru.otus.spring.model.Question;
import ru.otus.spring.providers.ResourceProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class QuestionLoaderCsv implements QuestionLoader {

    final private ResourceProvider resourceProvider;

    public QuestionLoaderCsv(final ResourceProvider resourceProvider) {
        this.resourceProvider = resourceProvider;
    }

    @Override
    public List<Question> load() throws QuestionLoadException {
        final List<Question> questions = new ArrayList<>();
        final List<String> csvStrings = readAllLinesInResource();

        for (int i = 0; i < csvStrings.size(); i++) {
            final String csvString = csvStrings.get(i);
            final String[] csvStringData = csvString.split(";");

            if (csvStringData.length < 2) {
                throw new QuestionLoadException(String.format("Invalid csv string on line %d: '%s'", (i + 1), csvString));
            }

            final String question = csvStringData[0];
            final List<String> answers = Arrays.asList(csvStringData).subList(1, csvStringData.length);

            questions.add(new Question(question, answers));
        }

        return questions;
    }

    public List<String> readAllLinesInResource() throws QuestionLoadException {
        final List<String> csvStrings = new ArrayList<>();

        try (final Scanner scanner = new Scanner(resourceProvider.getResource().getInputStream())) {
            while (scanner.hasNextLine()) {
                csvStrings.add(scanner.nextLine());
            }
        } catch (final IOException e) {
            throw new QuestionLoadException("Ошибка обработки ресурса", e);
        }

        return csvStrings;
    }
}
