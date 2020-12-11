package ru.otus.spring.services.exam;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.BDDMockito;

import ru.otus.spring.exceptions.LoadException;
import ru.otus.spring.loaders.QuestionLoader;
import ru.otus.spring.model.Question;
import ru.otus.spring.services.questions.QuestionsModifierService;

import java.util.Collections;
import java.util.List;

@DisplayName("Класс ExamFormPreparerServiceImpl")
public class ExamFormPreparerServiceImplTest {

    private List<Question> questions;

    private QuestionLoader questionLoader;

    private QuestionsModifierService modifierService;

    private ExamFormPreparerServiceImpl service;

    @BeforeEach
    public void beforeEach() {
        this.questions = Collections.singletonList(new Question("question1", Collections.singletonList("answer1")));
        this.questionLoader = BDDMockito.mock(QuestionLoader.class);

        this.modifierService = BDDMockito.mock(QuestionsModifierService.class);
        BDDMockito.given(modifierService.modify(questions)).willReturn(questions);

        this.service = BDDMockito.mock(ExamFormPreparerServiceImpl.class, BDDMockito.withSettings()
            .useConstructor(questionLoader, modifierService, 1, 1)
            .defaultAnswer(BDDMockito.CALLS_REAL_METHODS)
        );
    }

    @DisplayName("вызывает метод загрузки вопросов")
    @Test
    public void shouldCallLoadMethodInLoader() throws LoadException {
        BDDMockito.given(questionLoader.load()).willReturn(questions);
        service.loadQuestions();
        BDDMockito.then(questionLoader).should().load();
    }

    @DisplayName("вызывает метод модификации вопросов")
    @Test
    public void shouldCallModifyMethodInModifierService() {
        service.modifyQuestions(questions);
        BDDMockito.then(modifierService).should().modify(questions);
    }
}
