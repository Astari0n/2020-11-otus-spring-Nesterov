package ru.otus.spring.parsers;

import org.springframework.core.io.Resource;
import ru.otus.spring.model.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class QuestionParserCsv implements QuestionParser {

    final private Resource resource;

    final private List<String> csvStrings;

    public QuestionParserCsv(final Resource resource) {
        this.resource = resource;
        this.csvStrings = new ArrayList<>();
    }

    @Override
    public List<Question> parse() throws IllegalArgumentException, IOException {
        readAllLinesInResource();

        final List<Question> questions = new ArrayList<>();

        for (int i = 0; i < csvStrings.size(); i++) {
            final String csvString = csvStrings.get(i);
            final String[] csvStringData = csvString.split(";");

            if (csvStringData.length < 2) {
                throw new IllegalArgumentException(String.format("Invalid csv string on line %d: '%s'", (i + 1), csvString));
            }

            final String question = csvStringData[0];
            final List<String> answers = Arrays.asList(csvStringData).subList(1, csvStringData.length);

            questions.add(new Question(question, answers));
        }

        return questions;
    }

    protected void readAllLinesInResource() throws IOException {
        csvStrings.clear();

        final Scanner scanner = new Scanner(resource.getInputStream());
        while (scanner.hasNextLine()) {
            csvStrings.add(scanner.nextLine());
        }
        scanner.close();
    }

    public Resource getResource() {
        return resource;
    }

    public List<String> getCsvStrings() {
        return csvStrings;
    }
}
