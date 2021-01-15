package ru.otus.spring.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import org.springframework.dao.EmptyResultDataAccessException;

import ru.otus.spring.dao.DaoGenres;
import ru.otus.spring.dao.mappers.MapperGenre;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
@DisplayName("Класс DaoGenresNamedJdbc")
@Import({ DaoGenresNamedJdbc.class, MapperGenre.class })
class DaoGenresNamedJdbcTest {

    @Autowired
    private DaoGenres daoGenres;

    @Test
    @DisplayName("корректно создаёт жанр")
    void shouldCorrectCreateGenre() {
        var expected = new Genre("test genre name");

        var id = daoGenres.insert(expected);
        expected.setId(id);

        var actual = daoGenres.getById(id);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("корректно получает жанр по id")
    void shouldCorrectGetGenreById() {
        var expected = new Genre(2, "Fantasy Novel");
        var actual = daoGenres.getById(expected.getId());
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("корректно получает все жанры")
    void shouldCorrectGetAllGenres() {
        var expected = List.of(
            new Author(1, "Novel"),
            new Author(2, "Fantasy Novel"),
            new Author(3, "Free Genre without books")
        );

        var actual = daoGenres.getAll();

        assertThat(actual.size()).isEqualTo(expected.size());

        for (int i = 0; i < expected.size(); i++) { // containsExactlyInAnyOrderElementsOf not works here :\
            var expectedGenre = expected.get(i);
            var actualGenre= actual.get(i);

            assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
        }
    }

    @Test
    @DisplayName("корректно обновляет жанр в базе")
    void shouldCorrectUpdateGenre() {
        var expected = daoGenres.getById(1);

        expected.setName("updated name");
        daoGenres.update(expected);

        var actual = daoGenres.getById(1);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("корректно удаляет жанр из базы")
    void shouldCorrectDeleteGenre() {
        var expected = daoGenres.getById(3);

        daoGenres.delete(expected);

        assertThatThrownBy(() -> daoGenres.getById(expected.getId())).isInstanceOf(EmptyResultDataAccessException.class);
    }
}