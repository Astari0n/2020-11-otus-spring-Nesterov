package ru.otus.spring.services.exam;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.otus.spring.exceptions.LoadException;
import ru.otus.spring.loaders.QuestionLoader;
import ru.otus.spring.model.Question;
import ru.otus.spring.services.questions.QuestionsModifierService;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@DisplayName("Класс ExamFormPreparerServiceImpl")
public class ExamFormPreparerServiceImplTest {

    @Configuration
    static class ExamFormPreparerServiceImplTestConfig {
        @Bean
        public ExamFormPreparerServiceImpl examFormPreparerServiceImpl(
            final QuestionLoader questionLoader,
            final QuestionsModifierService modifierService
        ) {
            return new ExamFormPreparerServiceImpl(questionLoader, modifierService, 1, 1);
        }
    }

    @Autowired
    private ExamFormPreparerServiceImpl service;

    @MockBean
    private QuestionsModifierService modifierService;

    @MockBean
    private QuestionLoader questionLoader;

    private List<Question> questions;

    @BeforeEach
    public void beforeEach() {
        questions = Collections.singletonList(new Question("question1", Collections.singletonList("answer1")));
        doReturn(questions).when(modifierService).modify(questions);
    }

    @Test
    @DisplayName("вызывает метод загрузки вопросов")
    public void shouldCallLoadMethodInLoader() throws LoadException {
        doReturn(questions).when(questionLoader).load();
        service.loadQuestions();
        then(questionLoader).should().load();
    }

    @Test
    @DisplayName("вызывает метод модификации вопросов")
    public void shouldCallModifyMethodInModifierService() {
        service.modifyQuestions(questions);
        then(modifierService).should().modify(questions);
    }
}
