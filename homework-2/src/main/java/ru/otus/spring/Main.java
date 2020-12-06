package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import ru.otus.spring.exceptions.ExamException;
import ru.otus.spring.exceptions.QuestionInteractException;
import ru.otus.spring.services.AbstractExamTestInteractor;
import ru.otus.spring.services.IOExamTestInteractor;

@ComponentScan
public class Main {
    public static void main(final String[] args) throws QuestionInteractException, ExamException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        AbstractExamTestInteractor examTestInteractor = context.getBean(IOExamTestInteractor.class);
        examTestInteractor.exam();
    }
}
