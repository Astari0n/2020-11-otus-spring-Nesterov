package ru.otus.spring.configs;

import lombok.Getter;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "application")
public class AppProps {

    @Getter
    @Setter
    private Locale locale;

    @Getter
    @Setter
    private String resourceFileNameTemplate;
}
