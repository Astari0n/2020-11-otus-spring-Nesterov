package ru.otus.spring.services.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import org.springframework.dao.EmptyResultDataAccessException;

import ru.otus.spring.dao.DaoAuthors;
import ru.otus.spring.model.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.when;

@JdbcTest
@DisplayName("Класс AuthorsServiceDao")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({ ServiceAuthorsDao.class })
class ServiceAuthorsDaoTest {

    @MockBean
    private DaoAuthors daoAuthors;

    @Autowired
    private ServiceAuthorsDao serviceAuthorsDao;

    @Test
    @DisplayName("должен создавать автора через дао")
    void shouldCreateAuthorUsingDao() {
        var authorName = anyString();
        var expected = serviceAuthorsDao.createAuthor(authorName);
        var actual = then(daoAuthors).should().create(authorName);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("должен переименовывать автора и обновлять запись в базе через дао")
    void shouldRenameAuthorNameAndUpdateUsingDao() {
        var author = new Author(1, "first variant of the name");
        serviceAuthorsDao.renameAuthor(author, "second variant of the name");
        then(daoAuthors).should().update(author);
    }

    @Test
    @DisplayName("должен искать автора через дао")
    void shouldFindAuthorUsingDao() {
        var expected = new Author(1, "test name");

        when(daoAuthors.getById(anyLong())).thenReturn(expected);

        var actual = serviceAuthorsDao.findAuthorByAuthorId(expected.getId());

        then(daoAuthors).should().getById(expected.getId());

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("должен получать всех авторов через дао")
    void shouldGetAllAuthorsUsingDao() {
        var expected = List.of(
            new Author(1, "test"),
            new Author(2, "test 2")
        );

        when(daoAuthors.getAll()).thenReturn(expected);

        var actual = serviceAuthorsDao.getAll();

        then(daoAuthors).should().getAll();

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("должен удалять автора через дао")
    void shouldDeleteAuthorUsingDao() {
        var author = new Author(1, "test name");
        when(daoAuthors.getById(anyLong())).thenThrow(EmptyResultDataAccessException.class);

        serviceAuthorsDao.deleteAuthor(author);
        then(daoAuthors).should().delete(author);

        assertThatThrownBy(() -> serviceAuthorsDao.findAuthorByAuthorId(author.getId())).isInstanceOf(EmptyResultDataAccessException.class);
    }
}