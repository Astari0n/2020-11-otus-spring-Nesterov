package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.exceptions.QuestionInteractException;
import ru.otus.spring.interactors.AbstractQuestionInteractor;

public class Main {
    public static void main(final String[] args) throws QuestionInteractException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");

        AbstractQuestionInteractor interactor = context.getBean(AbstractQuestionInteractor.class, "consoleInteractor");
        interactor.interact();
    }
}
