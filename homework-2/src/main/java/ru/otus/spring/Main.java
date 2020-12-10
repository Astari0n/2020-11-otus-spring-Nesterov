package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import ru.otus.spring.exceptions.DisplayServiceException;
import ru.otus.spring.exceptions.EvaluationException;
import ru.otus.spring.exceptions.ExamFormPrepareException;
import ru.otus.spring.exceptions.InteractionException;
import ru.otus.spring.exceptions.RegisterException;

import ru.otus.spring.services.exam.Examiner;

@ComponentScan
public class Main {
    public static void main(final String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        Examiner examiner = context.getBean(Examiner.class);

        try {
            examiner.exam();
        } catch (
            ExamFormPrepareException
                | RegisterException
                | InteractionException
                | EvaluationException
                | DisplayServiceException e
        ) {
            System.out.println("Ошибка на экзамене: " + e);
        }
    }
}
