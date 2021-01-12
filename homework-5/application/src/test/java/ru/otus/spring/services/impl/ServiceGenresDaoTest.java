package ru.otus.spring.services.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import org.springframework.dao.EmptyResultDataAccessException;

import ru.otus.spring.dao.DaoGenres;
import ru.otus.spring.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@JdbcTest
@DisplayName("Класс ServiceGenresDao")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({ ServiceGenresDao.class })
class ServiceGenresDaoTest {

    @MockBean
    private DaoGenres daoGenres;

    @Autowired
    private ServiceGenresDao serviceGenresDao;

    @Test
    @DisplayName("должен создавать жанр через дао")
    void shouldCreateGenreUsingDao() {
        var genreName = anyString();
        var expected = serviceGenresDao.createGenre(genreName);
        var actual = then(daoGenres).should().create(genreName);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("должен переименовывать жанр и обновлять запись в базе через дао")
    void shouldRenameGenreNameAndUpdateUsingDao() {
        var genre = new Genre(1, "first variant of the name");
        serviceGenresDao.renameGenre(genre, "second variant of the name");
        then(daoGenres).should().update(genre);
    }

    @Test
    @DisplayName("должен искать жанр через дао")
    void shouldFindGenreUsingDao() {
        var expected = new Genre(1, "test name");

        when(daoGenres.getById(anyLong())).thenReturn(expected);

        var actual = serviceGenresDao.findGenreByGenreId(expected.getId());

        then(daoGenres).should().getById(expected.getId());

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("должен получать все жанры через дао")
    void shouldGetAllGenresUsingDao() {
        var expected = List.of(
            new Genre(1, "test"),
            new Genre(2, "test 2")
        );

        when(daoGenres.getAll()).thenReturn(expected);

        var actual = serviceGenresDao.getAll();

        then(daoGenres).should().getAll();

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("должен удалять жанр через дао")
    void shouldDeleteGenreUsingDao() {
        var genre = new Genre(1, "test name");
        when(daoGenres.getById(anyLong())).thenThrow(EmptyResultDataAccessException.class);

        serviceGenresDao.deleteGenre(genre);
        then(daoGenres).should().delete(genre);

        assertThatThrownBy(() -> serviceGenresDao.findGenreByGenreId(genre.getId())).isInstanceOf(EmptyResultDataAccessException.class);
    }
}