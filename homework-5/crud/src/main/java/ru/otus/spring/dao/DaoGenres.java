package ru.otus.spring.dao;

import ru.otus.spring.model.Genre;

import java.util.List;

public interface DaoGenres {
    Genre create(String genreName);

    Genre getById(long genreId);

    List<Genre> getAll();

    int update(Genre genre);

    int delete(Genre genre);
}
