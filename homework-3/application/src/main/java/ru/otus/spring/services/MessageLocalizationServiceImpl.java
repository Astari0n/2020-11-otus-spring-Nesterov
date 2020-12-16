package ru.otus.spring.services;

import lombok.RequiredArgsConstructor;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import ru.otus.spring.configs.AppProps;
import ru.otus.spring.services.localization.MessageLocalizationService;

@Component
@RequiredArgsConstructor
public class MessageLocalizationServiceImpl implements MessageLocalizationService {

    private final MessageSource messageSource;

    private final AppProps appProps;

    @Override
    public String getText(final String key, final Object... args) {
        return messageSource.getMessage(key, args, appProps.getLocale());
    }
}
