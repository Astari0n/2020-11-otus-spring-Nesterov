package ru.otus.spring.services.exam;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.BDDMockito;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.exceptions.LoadException;
import ru.otus.spring.loaders.QuestionLoader;
import ru.otus.spring.model.Question;
import ru.otus.spring.services.questions.QuestionsModifierService;

import java.util.Collections;
import java.util.List;

@SpringBootTest
@DisplayName("Класс ExamFormPreparerServiceImpl")
public class ExamFormPreparerServiceImplTest {

    @MockBean
    private QuestionsModifierService modifierService;

    private ExamFormPreparerServiceImpl service;

    private List<Question> questions;

    private QuestionLoader questionLoader;

    @BeforeEach
    public void beforeEach() {
        this.questions = Collections.singletonList(new Question("question1", Collections.singletonList("answer1")));
        this.questionLoader = BDDMockito.mock(QuestionLoader.class);

        BDDMockito.given(modifierService.modify(questions)).willReturn(questions);

        this.service = new ExamFormPreparerServiceImpl(questionLoader, modifierService, 1, 1);
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
