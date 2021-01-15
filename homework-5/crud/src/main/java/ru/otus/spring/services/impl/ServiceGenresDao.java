package ru.otus.spring.services.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import ru.otus.spring.dao.DaoGenres;
import ru.otus.spring.exception.DeletionException;
import ru.otus.spring.model.Genre;
import ru.otus.spring.services.ServiceBooks;
import ru.otus.spring.services.ServiceGenres;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceGenresDao implements ServiceGenres {

    private final DaoGenres daoGenres;

    private final ServiceBooks serviceBooks;

    @Override
    public Genre createGenre(final String genreName) {
        final var genre = new Genre(genreName);

        long genreId = daoGenres.insert(genre);
        genre.setId(genreId);

        return genre;
    }

    @Override
    public int renameGenre(final Genre genre, final String newGenreName) {
        genre.setName(newGenreName);
        return daoGenres.update(genre);
    }

    @Override
    public Genre findGenreByGenreId(final long genreId) {
        return daoGenres.getById(genreId);
    }

    @Override
    public List<Genre> getAll() {
        return daoGenres.getAll();
    }

    @Override
    public int deleteGenre(final Genre genre) throws DeletionException {
        final var books = serviceBooks.findBookByGenreId(genre);

        if (!books.isEmpty()) {
            throw new DeletionException(String.format(
                "there are books [%s] with genreId %d",
                books.stream().map(b -> String.valueOf(b.getId())).collect(Collectors.joining(",")),
                genre.getId())
            );
        }

        return daoGenres.delete(genre);
    }
}
