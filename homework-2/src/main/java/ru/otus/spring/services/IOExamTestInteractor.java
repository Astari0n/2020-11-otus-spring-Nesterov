package ru.otus.spring.services;

import ru.otus.spring.exceptions.ExamException;
import ru.otus.spring.exceptions.QuestionInteractException;
import ru.otus.spring.exceptions.QuestionLoadException;
import ru.otus.spring.loaders.QuestionLoader;
import ru.otus.spring.model.Question;
import ru.otus.spring.model.User;

public class IOExamTestInteractor extends AbstractRandomQuestionsExamTestInteractor {

    final private IOService ioService;

    private User student;

    private int correctAnswers;

    public IOExamTestInteractor(
        final IOService ioService,
        final QuestionLoader questionLoader,
        final int numberOfQuestionsToAsk,
        final int numberOfQuestionsToPassTheTest
    ) throws QuestionLoadException {
        super(questionLoader, numberOfQuestionsToAsk, numberOfQuestionsToPassTheTest);
        this.ioService = ioService;
    }

    @Override
    protected void beforeExam() {
        println(String.format(
            "Hello! To pass the exam you need to answer %d out of %d questions!",
            examTest.getNumberOfQuestionsToPassTheTest(),
            examTest.getNumberOfQuestionsToAsk()
        ));

        final String firstName = getNonEmptyString(
            "Write your first name: ",
            "Error! Firstname is empty!");

        final String lastName = getNonEmptyString(
            String.format("%s, write your last name: ", firstName),
            "Error! Lastname is empty!");

        println();
        println(String.format("Welcome, %s %s! Creating student session...", firstName, lastName));
        student = new User(firstName, lastName);
    }

    @Override
    protected void answerTheQuestion(final Question question) {
        println();
        println("The question is: " + question);
        print("Write your answer: ");

        final String answer = read();
        if (question.isAnswerCorrect(answer)) {
            correctAnswers++;
        } else {
            println("Answer is incorrect!");
        }
    }

    @Override
    protected void afterExam() {
        println();
        println(String.format(
            "%s %s, your result is: [%d / %d] correct answers! You %s the exam!",
            student.getFirstName(),
            student.getLastName(),
            correctAnswers,
            examTest.getNumberOfQuestionsToAsk(),
            (correctAnswers >= examTest.getNumberOfQuestionsToPassTheTest() ? "PASSED" : "FAILED")
        ));
    }

    private String getNonEmptyString(final String printText, final String errorMessage) {
        print(printText);
        String result = read();

        while (result.isEmpty()) {
            println(errorMessage);
            print(printText);
            result = read();
        }
        return result;
    }

    public void println(final String s) {
        print(s);
        println();
    }

    public void println() {
        print("\n");
    }

    public void print(final String s) {
        ioService.writeString(s);
    }

    public String read() {
        return ioService.readString();
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }
}
