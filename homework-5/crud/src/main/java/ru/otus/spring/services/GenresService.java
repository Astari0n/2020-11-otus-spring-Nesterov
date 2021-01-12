package ru.otus.spring.services;

import ru.otus.spring.model.Genre;

import java.util.List;

public interface GenresService {
    Genre createGenre(String genreName);

    int renameGenre(Genre genre, String newGenreName);

    Genre findGenreByGenreId(long genreId);

    List<Genre> getAll();

    int deleteGenre(Genre genre);
}
