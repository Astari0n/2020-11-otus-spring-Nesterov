package ru.otus.spring.configs;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import ru.otus.spring.providers.ResourceProvider;
import ru.otus.spring.services.exam.Examiner;
import ru.otus.spring.services.localization.MessageLocalizationService;

@Configuration
@ConditionalOnClass({Examiner.class, LocalizationConfig.class})
@RequiredArgsConstructor
public class ExaminerLocalizationAutoConfiguration {

    private final MessageSource messageSource;

    private final AppProps appProps;

    @Bean
    @ConditionalOnMissingBean
    public ResourceProvider csvResourceProvider() {
        final String resourceFile = messageSource.getMessage("resource.questions_file", null, appProps.getLocale());
        final Resource resource = new ClassPathResource(resourceFile);
        return new ResourceProvider(resource);
    }

    @Bean
    @ConditionalOnMissingBean
    public MessageLocalizationService localizationService() {
        return (key, args) -> messageSource.getMessage(key, args, appProps.getLocale());
    }
}
