package ru.otus.spring.services;

import ru.otus.spring.exception.ServiceException;
import ru.otus.spring.model.Genre;

import java.util.List;

public interface ServiceGenres {
    Genre createGenre(String genreName) throws ServiceException;

    int renameGenre(Genre genre, String newGenreName) throws ServiceException;

    Genre findGenreByGenreId(long genreId) throws ServiceException;

    List<Genre> getAll() throws ServiceException;

    int deleteGenre(Genre genre) throws ServiceException;
}
