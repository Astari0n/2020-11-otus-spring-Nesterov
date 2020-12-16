package ru.otus.spring.services.exam;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ru.otus.spring.exceptions.ExamEvaluationException;

import ru.otus.spring.exceptions.QuestionsAnswersMismatchExceptionExam;
import ru.otus.spring.model.ExamAnswerForm;
import ru.otus.spring.model.ExamForm;
import ru.otus.spring.model.ExamResult;
import ru.otus.spring.model.Question;
import ru.otus.spring.model.Student;
import ru.otus.spring.services.localization.MessageLocalizationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamEvaluationServiceImpl implements ExamEvaluationService {

    private final MessageLocalizationService localizationService;

    @Override
    public ExamResult evaluate(final ExamAnswerForm answerForm) throws ExamEvaluationException {
        final ExamForm examForm = answerForm.getQuestionsForm();

        final List<Question> questions = examForm.getQuestions();
        final int questionsCount = questions.size();

        final List<String> answers = answerForm.getAnswers();
        final int answersCount = answers.size();

        if (answersCount != questionsCount) {
            throw new QuestionsAnswersMismatchExceptionExam("The number of questions and answers does not match");
        }

        int correctAnswersCount = 0;

        for (int i = 0; i < questionsCount; i++) {
            final Question question = questions.get(i);
            final String answer = answers.get(i);

            if (question.isAnswerCorrect(answer)) {
                correctAnswersCount++;
            }
        }

        final Student student = answerForm.getStudent();
        final float completionPercentage = (float) correctAnswersCount / questionsCount * 100.0f;
        final int amountOfCorrectAnswersToPassExam = examForm.getAmountOfCorrectAnswersToPassExam();

        final String examMark = correctAnswersCount >= amountOfCorrectAnswersToPassExam
            ? localizationService.getText("exam.mark_passed") : localizationService.getText("exam.mark_failed");

        return new ExamResult(student, correctAnswersCount, questionsCount, completionPercentage, examMark);
    }
}
