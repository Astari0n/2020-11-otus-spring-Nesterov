package ru.otus.spring.services.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import ru.otus.spring.dao.DaoGenres;
import ru.otus.spring.exception.ServiceException;
import ru.otus.spring.model.Genre;
import ru.otus.spring.services.ServiceBooks;
import ru.otus.spring.services.ServiceGenres;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceGenresDao implements ServiceGenres {

    private final DaoGenres daoGenres;

    private final ServiceBooks serviceBooks;

    @Override
    public Genre createGenre(final String genreName) throws ServiceException {
        try {
            final var genre = new Genre(genreName);
            long genreId = daoGenres.insert(genre);
            genre.setId(genreId);

            return genre;
        } catch (final DataAccessException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int renameGenre(final Genre genre, final String newGenreName) throws ServiceException {
        try {
            genre.setName(newGenreName);
            return daoGenres.update(genre);
        } catch (final DataAccessException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Genre findGenreByGenreId(final long genreId) throws ServiceException {
        try {
            return daoGenres.getById(genreId);
        } catch (final EmptyResultDataAccessException e) {
            throw new ServiceException("Genre not found with genreId " + genreId, e);
        } catch (final DataAccessException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Genre> getAll() {
        return daoGenres.getAll();
    }

    @Override
    public int deleteGenre(final Genre genre) throws ServiceException {
        final var booksWithGenre = serviceBooks.countBooksWithGenre(genre);

        if (booksWithGenre > 0) {
            throw new ServiceException(String.format("There are %d books with genre %d", booksWithGenre, genre.getId()));
        }

        try {
            return daoGenres.delete(genre);
        } catch (final DataAccessException e) {
            throw new ServiceException(e);
        }
    }
}
