package ru.otus.spring.loaders;

import ru.otus.spring.exceptions.ResourceReadException;

import java.util.List;

public interface ResourceReader {
    List<String> readResource() throws ResourceReadException;
}
