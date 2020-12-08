package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import ru.otus.spring.providers.ResourceProvider;
import ru.otus.spring.services.IOService;
import ru.otus.spring.services.StreamIOService;

@Configuration
@PropertySource("classpath:application.properties")
public class Config {

    @Bean
    public ResourceProvider csvResourceProvider(@Value("${questions_source_file}") final String resourceFile) {
        final Resource resource = new ClassPathResource(resourceFile);
        return new ResourceProvider(resource);
    }

    @Bean
    public IOService streamIOService() {
        return new StreamIOService(System.in, System.out);
    }
}
