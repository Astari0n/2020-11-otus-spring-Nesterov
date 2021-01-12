package ru.otus.spring.services.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import ru.otus.spring.dao.DaoGenres;
import ru.otus.spring.model.Genre;
import ru.otus.spring.services.GenresService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceGenresDao implements GenresService {

    private final DaoGenres daoGenres;

    @Override
    public Genre createGenre(final String genreName) {
        return daoGenres.create(genreName);
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
    public int deleteGenre(final Genre genre) {
        return daoGenres.delete(genre);
    }
}
