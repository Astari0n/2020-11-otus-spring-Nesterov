package ru.otus.spring.services.localization;

public interface MessageLocalizationService {
    String getText(String key, Object... args);
}
