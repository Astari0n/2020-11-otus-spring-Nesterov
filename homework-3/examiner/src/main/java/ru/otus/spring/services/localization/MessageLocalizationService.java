package ru.otus.spring.services.localization;

@FunctionalInterface
public interface MessageLocalizationService {
    String getText(String key, Object... args);
}
