package ru.otus.spring.configs;

import lombok.RequiredArgsConstructor;

import org.jline.reader.LineReader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.otus.spring.loaders.QuestionLoader;
import ru.otus.spring.services.exam.ExamFormPreparerService;
import ru.otus.spring.services.exam.ExamFormPreparerServiceImpl;
import ru.otus.spring.services.exam.Examiner;
import ru.otus.spring.services.io.IOService;
import ru.otus.spring.services.io.StreamIOService;
import ru.otus.spring.services.questions.QuestionsModifierService;

@Configuration
@ConditionalOnClass(Examiner.class)
@EnableConfigurationProperties(ExaminerProps.class)
@RequiredArgsConstructor
public class ExaminerAutoConfiguration {

    private final ExaminerProps examinerProps;

    private final QuestionLoader questionLoader;

    private final QuestionsModifierService questionsModifierService;

    @Bean
    public IOService streamIOService() {
        return new StreamIOService(System.in, System.out);
    }

    @Bean
    public ExamFormPreparerService examFormPreparerServiceImpl() {
        return new ExamFormPreparerServiceImpl(
            questionLoader,
            questionsModifierService,
            examinerProps.getAmountOfQuestionsToAsk(),
            examinerProps.getAmountOfCorrectAnswersToPassExam()
        );
    }
}
