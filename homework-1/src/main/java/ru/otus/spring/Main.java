package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.interactors.ConsoleInteractor;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");

        ConsoleInteractor interactor = context.getBean(ConsoleInteractor.class);
        interactor.interact();
    }
}
