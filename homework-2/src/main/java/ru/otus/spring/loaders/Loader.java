package ru.otus.spring.loaders;

import ru.otus.spring.exceptions.LoadException;

import java.util.List;

public interface Loader<E> {
    List<E> load() throws LoadException;
}
