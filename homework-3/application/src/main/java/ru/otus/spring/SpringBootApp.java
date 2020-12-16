package ru.otus.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

import ru.otus.spring.configs.AppProps;
import ru.otus.spring.exceptions.ExamException;
import ru.otus.spring.services.exam.Examiner;

@SpringBootApplication
@EnableConfigurationProperties(AppProps.class)
public class SpringBootApp {

    private static final Logger logger = LoggerFactory.getLogger(AppProps.class);

    public static void main(String[] args) {
        final ConfigurableApplicationContext context =  SpringApplication.run(SpringBootApp.class, args);

        Examiner examiner = context.getBean(Examiner.class);

        try {
            examiner.exam();
        } catch (final ExamException e) {
            logger.error("Ошибка на экзамене: ", e);
        }
    }
}
