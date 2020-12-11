package ru.otus.spring.loaders;

import org.springframework.stereotype.Component;

import ru.otus.spring.exceptions.LoadException;
import ru.otus.spring.exceptions.ResourceReadException;
import ru.otus.spring.model.Question;
import ru.otus.spring.providers.ResourceProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Component
public class QuestionLoaderCsv implements QuestionLoader, ResourceReader {

    private final ResourceProvider resourceProvider;

    public QuestionLoaderCsv(final ResourceProvider resourceProvider) {
        this.resourceProvider = resourceProvider;
    }

    @Override
    public List<Question> load() throws LoadException {
        try {
            return doLoad();
        } catch (ResourceReadException e) {
            throw new LoadException(e);
        }
    }

    protected List<Question> doLoad() throws LoadException, ResourceReadException {
        final List<Question> questions = new ArrayList<>();
        final List<String> csvStrings = readResource();

        for (int i = 0; i < csvStrings.size(); i++) {
            final String csvString = csvStrings.get(i);
            final String[] csvStringData = csvString.split(";");

            if (csvStringData.length < 2) {
                throw new LoadException(String.format("Invalid csv string on line %d: '%s'", (i + 1), csvString));
            }

            final String question = csvStringData[0];
            final List<String> answers = Arrays.asList(csvStringData).subList(1, csvStringData.length);

            questions.add(new Question(question, answers));
        }

        return questions;
    }

    @Override
    public List<String> readResource() throws ResourceReadException {
        final List<String> fileStrings = new ArrayList<>();

        try (final Scanner scanner = new Scanner(resourceProvider.getResource().getInputStream())) {
            while (scanner.hasNextLine()) {
                fileStrings.add(scanner.nextLine());
            }
        } catch (final IOException e) {
            throw new ResourceReadException("Ошибка обработки ресурса", e);
        }

        return fileStrings;
    }
}
