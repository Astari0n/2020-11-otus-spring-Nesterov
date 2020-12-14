package ru.otus.spring.services.exam;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.exceptions.ExamFormPrepareException;
import ru.otus.spring.exceptions.LoadException;

import ru.otus.spring.loaders.QuestionLoader;

import ru.otus.spring.model.ExamForm;
import ru.otus.spring.model.Question;
import ru.otus.spring.services.questions.QuestionsModifierService;

import java.util.List;

@RequiredArgsConstructor
public class ExamFormPreparerServiceImpl implements ExamFormPreparerService {

    private final QuestionLoader questionLoader;

    private final QuestionsModifierService questionsModifierService;

    private final int amountOfQuestionsToAsk;

    private final int amountOfCorrectAnswersToPassExam;

    @Override
    public ExamForm prepareForm() throws ExamFormPrepareException {
        try {
            List<Question> questions = loadQuestions();
            questions = modifyQuestions(questions);
            return new ExamForm(questions, amountOfCorrectAnswersToPassExam);
        } catch (final LoadException e) {
            throw new ExamFormPrepareException(e);
        }
    }

    protected List<Question> loadQuestions() throws LoadException {
        return questionLoader.load();
    }

    protected List<Question> modifyQuestions(final List<Question> questions) {
        return questionsModifierService.modify(questions).subList(0, amountOfQuestionsToAsk);
    }
}
