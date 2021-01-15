package ru.otus.spring.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import org.springframework.context.annotation.Import;

import org.springframework.dao.EmptyResultDataAccessException;

import ru.otus.spring.dao.DaoAuthors;
import ru.otus.spring.dao.mappers.MapperAuthor;
import ru.otus.spring.model.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
@DisplayName("Класс DaoAuthorsNamedJdbc")
@Import({ DaoAuthorsNamedJdbc.class, MapperAuthor.class })
class DaoAuthorsNamedJdbcTest {

    @Autowired
    private DaoAuthors daoAuthors;

    @Test
    @DisplayName("корректно добавляет автора в базу")
    void shouldCorrectCreateAuthor() {
        var expected = new Author("test name");

        var id = daoAuthors.insert(expected);
        expected.setId(id);

        var actual = daoAuthors.getById(id);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("корректно получает автора по id")
    void shouldCorrectGetAuthorById() {
        var expectedAuthor = new Author(2, "Joanne Rowling");
        var actualAuthor = daoAuthors.getById(expectedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("корректно получает всех авторов")
    void shouldCorrectGetAllAuthors() {
        var expected = List.of(
            new Author(1, "Ray Bradbury"),
            new Author(2, "Joanne Rowling"),
            new Author(3, "Free Author without books")
        );

        var actual = daoAuthors.getAll();

        assertThat(actual.size()).isEqualTo(expected.size());

        for (int i = 0; i < expected.size(); i++) { // containsExactlyInAnyOrderElementsOf not works here :\
            var expectedAuthor = expected.get(i);
            var actualAuthor = actual.get(i);

            assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
        }
    }

    @Test
    @DisplayName("корректно обновляет информацию об авторе в базе")
    void shouldCorrectUpdateAuthor() {
        var expected = daoAuthors.getById(1);

        expected.setName("updated name");
        daoAuthors.update(expected);

        var actual = daoAuthors.getById(1);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("корректно удаляет автора из базы")
    void shouldCorrectDeleteAuthor() {
        var expected = daoAuthors.getById(3);

        daoAuthors.delete(expected);

        assertThatThrownBy(() -> daoAuthors.getById(expected.getId())).isInstanceOf(EmptyResultDataAccessException.class);
    }
}