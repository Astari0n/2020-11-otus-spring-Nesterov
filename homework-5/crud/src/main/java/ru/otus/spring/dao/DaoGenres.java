package ru.otus.spring.dao;

import ru.otus.spring.model.Genre;

import java.util.List;

public interface DaoGenres {
    long insert(Genre genre);

    Genre getById(long genreId);

    List<Genre> getAll();

    int update(Genre genre);

    int delete(Genre genre);
}
