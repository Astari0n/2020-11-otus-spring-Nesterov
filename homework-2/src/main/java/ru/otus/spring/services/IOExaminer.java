package ru.otus.spring.services;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import ru.otus.spring.exceptions.ExaminationException;
import ru.otus.spring.exceptions.LoadException;
import ru.otus.spring.loaders.Loader;
import ru.otus.spring.model.Exam;
import ru.otus.spring.model.ExamResult;
import ru.otus.spring.model.Question;
import ru.otus.spring.model.User;

import java.io.IOException;
import java.util.List;

@Service
public class IOExaminer extends AbstractExaminer<User, Question, ExamResult> {

    final private IOService io;

    public IOExaminer(
        final IOService ioService,
        final Loader<Question> questionLoader,
        final ModifierService<List<Question>> modifierService,
        final RegisterService<User, Exam<Question>> registerService,
        final EvaluationService<User, ExamResult> evaluationService,
        @Value("${exam.number_of_questions_to_ask}") final int numberOfQuestionsToAsk,
        @Value("${exam.number_of_questions_to_pass_the_test}") final int numberOfQuestionsToPassTheTest
    ) throws LoadException {
        super(
            questionLoader,
            modifierService,
            registerService,
            evaluationService,
            numberOfQuestionsToAsk,
            numberOfQuestionsToPassTheTest
        );

        this.io = ioService;
    }

    @Override
    protected ExamResult processExam(final List<Question> questions) throws ExaminationException {
        try {
            return answerTheQuestions(questions);
        } catch (final IOException e) {
            throw new ExaminationException(e);
        }
    }

    private ExamResult answerTheQuestions(final List<Question> questions) throws IOException, ExaminationException {
        int correctAnswers = 0;

        for (final Question q : questions) {
            if (isAnswerCorrect(q)) {
                correctAnswers++;
            } else {
                io.println("Answer is incorrect!");
            }
        }

        return new ExamResult(correctAnswers, exam.getNumberOfQuestionsToAsk(), exam.getNumberOfQuestionsToPassTheTest());
    }

    private boolean isAnswerCorrect(final Question q) throws IOException, ExaminationException {
        if (q == null) {
            throw new ExaminationException("Trying to interact with null question in examtest: " + exam);
        }

        io.println();
        io.println("The question is: " + q);
        io.print("Write your answer: ");

        final String answer = io.read();
        return q.isAnswerCorrect(answer);
    }
}
