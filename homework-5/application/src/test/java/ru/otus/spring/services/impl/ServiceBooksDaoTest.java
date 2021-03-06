package ru.otus.spring.services.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.dao.DaoBooks;
import ru.otus.spring.exception.ServiceException;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Класс ServiceBooksDao")
class ServiceBooksDaoTest {

    @MockBean
    private DaoBooks daoBooks;

    @Autowired
    private ServiceBooksDao serviceBooksDao;

    @Test
    @DisplayName("должен создавать книгу через дао")
    void shouldCreateBookUsingDao() throws ServiceException {
        var author = new Author(1, "first author");
        var genre = new Genre(1, "test genre");
        var title = "test title";

        var expected = serviceBooksDao.createBook(author, genre, title);
        then(daoBooks).should().insert(expected);
    }

    @Test
    @DisplayName("должен искать книгу через дао")
    void shouldFindBookUsingDao() throws ServiceException {
        var author = new Author(1, "test author");
        var genre = new Genre(1, "test genre");

        var expected = new Book(1, author, genre, "test title");

        when(daoBooks.getByBookId(anyLong())).thenReturn(expected);

        var actual = serviceBooksDao.findBookByBookId(expected.getId());

        then(daoBooks).should().getByBookId(expected.getId());

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("должен менять автора книги через дао")
    void shouldChangeBookAuthorUsingDao() throws ServiceException {
        var author = new Author(1, "first author");
        var genre = new Genre(1, "test genre");

        var expected = new Book(1, author, genre, "test title");
        serviceBooksDao.changeBookAuthor(expected, new Author(2, "second author"));
        then(daoBooks).should().update(expected);
    }

    @Test
    @DisplayName("должен менять жанр книги через дао")
    void shouldChangeBookGenreUsingDao() throws ServiceException {
        var author = new Author(1, "test author");
        var genre = new Genre(1, "first genre");

        var expected = new Book(1, author, genre, "test title");
        serviceBooksDao.changeBookGenre(expected, new Genre(2, "second genre"));
        then(daoBooks).should().update(expected);
    }

    @Test
    @DisplayName("должен менять название книги через дао")
    void shouldChangeBookTitleUsingDao() throws ServiceException {
        var author = new Author(1, "test author");
        var genre = new Genre(1, "test genre");

        var expected = new Book(1, author, genre, "first title");
        serviceBooksDao.changeBookTitle(expected, "second title");
        then(daoBooks).should().update(expected);
    }

    @Test
    @DisplayName("должен удалять книгу через дао")
    void shouldDeleteBookUsingDao() throws ServiceException {
        var author = new Author(1, "test author");
        var genre = new Genre(1, "test genre");
        var book = new Book(1, author, genre, "test title");

        when(daoBooks.getByBookId(anyLong())).thenThrow(EmptyResultDataAccessException.class);

        serviceBooksDao.deleteBook(book);
        then(daoBooks).should().delete(book);

        assertThatThrownBy(() -> serviceBooksDao.findBookByBookId(book.getId()))
            .hasCauseInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("должен получать все книги через дао")
    void getAll() throws ServiceException {
        var expected = List.of(
            new Book(1, new Author(1, "test author"), new Genre(1, "test genre"), "test title 1"),
            new Book(2, new Author(2, "test author"), new Genre(2, "test genre"), "test title 2")
        );

        when(daoBooks.getAll()).thenReturn(expected);

        var actual = serviceBooksDao.getAll();

        then(daoBooks).should().getAll();

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}