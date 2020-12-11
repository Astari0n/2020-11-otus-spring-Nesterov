package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import ru.otus.spring.exceptions.ExamException;
import ru.otus.spring.services.exam.Examiner;

@ComponentScan
public class Main {
    public static void main(final String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        Examiner examiner = context.getBean(Examiner.class);

        try {
            examiner.exam();
        } catch (final ExamException e) {
            System.out.println("Ошибка на экзамене: " + e);
        }
    }
}
