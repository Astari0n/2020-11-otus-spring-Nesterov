package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import ru.otus.spring.exceptions.QuestionLoadException;
import ru.otus.spring.loaders.QuestionLoader;
import ru.otus.spring.loaders.QuestionLoaderCsv;
import ru.otus.spring.providers.ResourceProvider;
import ru.otus.spring.services.IOExamTestInteractor;
import ru.otus.spring.services.IOService;
import ru.otus.spring.services.StreamIOService;

@Configuration
public class Config {

    @Bean
    public ResourceProvider csvResourceProvider() {
        final Resource resource = new ClassPathResource("questions.csv");
        return new ResourceProvider(resource);
    }

    @Bean
    public IOService streamIOService() {
        return new StreamIOService(System.in, System.out);
    }

    @Bean
    public QuestionLoader questionLoaderCsv() {
        return new QuestionLoaderCsv(csvResourceProvider());
    }

    @Bean
    public IOExamTestInteractor ioExamTestInteractor() throws QuestionLoadException {
        final int numberOfQuestionsToAsk = 5;
        final int numberOfQuestionsToPassTheTest = 4;
        return new IOExamTestInteractor(streamIOService(), questionLoaderCsv(), numberOfQuestionsToAsk, numberOfQuestionsToPassTheTest);
    }
}
