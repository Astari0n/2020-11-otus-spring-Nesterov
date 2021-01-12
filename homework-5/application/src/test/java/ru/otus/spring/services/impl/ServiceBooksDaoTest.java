package ru.otus.spring.services.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.dao.DaoBooks;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@JdbcTest
@DisplayName("Класс ServiceBooksDao")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({ ServiceBooksDao.class })
class ServiceBooksDaoTest {

    @MockBean
    private DaoBooks daoBooks;

    @Autowired
    private ServiceBooksDao serviceBooksDao;

    @Test
    @DisplayName("должен создавать книгу через дао")
    void shouldCreateBookUsingDao() {
        var author = any(Author.class);
        var genre = any(Genre.class);
        var title = anyString();

        var expected = serviceBooksDao.createBook(author, genre, title);
        var actual = then(daoBooks).should().create(author, genre, title);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("должен искать книгу через дао")
    void shouldFindBookUsingDao() {
        var author = new Author(1, "test author");
        var genre = new Genre(1, "test genre");

        var expected = new Book(1, author, genre, "test title");

        when(daoBooks.getById(anyLong())).thenReturn(expected);

        var actual = serviceBooksDao.findBookByBookId(expected.getId());

        then(daoBooks).should().getById(expected.getId());

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("должен менять автора книги через дао")
    void shouldChangeBookAuthorUsingDao() {
        var author = new Author(1, "first author");
        var genre = new Genre(1, "test genre");

        var expected = new Book(1, author, genre, "test title");
        serviceBooksDao.changeBookAuthor(expected, new Author(2, "second author"));
        then(daoBooks).should().update(expected);
    }

    @Test
    @DisplayName("должен менять жанр книги через дао")
    void shouldChangeBookGenreUsingDao() {
        var author = new Author(1, "test author");
        var genre = new Genre(1, "first genre");

        var expected = new Book(1, author, genre, "test title");
        serviceBooksDao.changeBookGenre(expected, new Genre(2, "second genre"));
        then(daoBooks).should().update(expected);
    }

    @Test
    @DisplayName("должен менять название книги через дао")
    void shouldChangeBookTitleUsingDao() {
        var author = new Author(1, "test author");
        var genre = new Genre(1, "test genre");

        var expected = new Book(1, author, genre, "first title");
        serviceBooksDao.changeBookTitle(expected, "second title");
        then(daoBooks).should().update(expected);
    }

    @Test
    @DisplayName("должен удалять книгу через дао")
    void shouldDeleteBookUsingDao() {
        var author = new Author(1, "test author");
        var genre = new Genre(1, "test genre");
        var book = new Book(1, author, genre, "test title");

        when(daoBooks.getById(anyLong())).thenThrow(EmptyResultDataAccessException.class);

        serviceBooksDao.deleteBook(book);
        then(daoBooks).should().delete(book);

        assertThatThrownBy(() -> serviceBooksDao.findBookByBookId(book.getId())).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("должен получать все книги через дао")
    void getAll() {
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