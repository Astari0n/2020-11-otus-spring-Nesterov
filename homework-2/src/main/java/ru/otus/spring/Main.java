package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import ru.otus.spring.exceptions.EvaluationException;
import ru.otus.spring.exceptions.ExaminationException;
import ru.otus.spring.exceptions.RegisterException;
import ru.otus.spring.services.AbstractExaminer;
import ru.otus.spring.services.IOExaminer;

@ComponentScan
public class Main {
    public static void main(final String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        AbstractExaminer examTestInteractor = context.getBean(IOExaminer.class);

        try {
            examTestInteractor.exam();
        } catch (RegisterException | ExaminationException | EvaluationException e) {
            System.out.println("Ошибка на экзамене: " + e);
        }
    }
}
