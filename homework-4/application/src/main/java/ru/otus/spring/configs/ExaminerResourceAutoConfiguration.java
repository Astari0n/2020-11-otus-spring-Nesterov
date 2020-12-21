package ru.otus.spring.configs;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import ru.otus.spring.loaders.QuestionLoader;
import ru.otus.spring.providers.ResourceProvider;

@Configuration
@ConditionalOnClass(QuestionLoader.class)
@EnableConfigurationProperties(AppProps.class)
@RequiredArgsConstructor
public class ExaminerResourceAutoConfiguration {

    private final AppProps appProps;

    @Bean
    public ResourceProvider csvResourceProvider() {
        final String resourceFileName = appProps.getLocalizedResourceFileName();
        final Resource resource = new ClassPathResource(resourceFileName);
        return new ResourceProvider(resource);
    }
}
