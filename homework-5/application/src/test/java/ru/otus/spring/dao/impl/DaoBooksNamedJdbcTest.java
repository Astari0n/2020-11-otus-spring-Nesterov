package ru.otus.spring.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import org.springframework.dao.EmptyResultDataAccessException;

import ru.otus.spring.dao.DaoBooks;
import ru.otus.spring.dao.mappers.MapperAuthor;
import ru.otus.spring.dao.mappers.MapperBook;
import ru.otus.spring.dao.mappers.MapperGenre;

import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
@DisplayName("Класс DaoBooksNamedJdbc")
@Import({ DaoBooksNamedJdbc.class, MapperBook.class, MapperAuthor.class, MapperGenre.class })
class DaoBooksNamedJdbcTest {

    @Autowired
    private DaoBooks daoBooks;

    @Test
    @DisplayName("корректно создаёт книгу")
    void shouldCorrectCreateBook() {
        var author = new Author(1, "Ray Bradbury");
        var genre = new Genre(1, "Novel");
        var title = "test book title";

        var expected = new Book(author, genre, title);

        var id = daoBooks.insert(expected);
        expected.setId(id);

        var actual = daoBooks.getByBookId(id);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("корректно получает книгу по id")
    void shouldCorrectGetBookById() {
        var author = new Author(2, "Joanne Rowling");
        var genre = new Genre(2, "Fantasy Novel");

        var expected = new Book(2, author, genre, "Harry Potter");
        var actual = daoBooks.getByBookId(expected.getId());

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("корректно получает все книги")
    void shouldCorrectGetAllBooks() {
        var expected = List.of(
            new Book(1, new Author(1, "Ray Bradbury"), new Genre(1, "Novel"), "Dandelion Wine"),
            new Book(2, new Author(2, "Joanne Rowling"), new Genre(2, "Fantasy Novel"), "Harry Potter")
        );

        var actual = daoBooks.getAll();

        assertThat(actual.size()).isEqualTo(expected.size());

        for (int i = 0; i < expected.size(); i++) { // containsExactlyInAnyOrderElementsOf not works here :\
            var expectedGenre = expected.get(i);
            var actualGenre= actual.get(i);

            assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
        }
    }

    @Test
    @DisplayName("корректно обновляет книгу в базе")
    void shouldCorrectUpdateBook() {
        var expected = daoBooks.getByBookId(1);

        expected.setTitle("updated title");
        expected.setAuthor(new Author(3, "Free Author without books"));
        expected.setGenre(new Genre(3, "Free Genre without books"));

        daoBooks.update(expected);

        var actual = daoBooks.getByBookId(1);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("корректно удаляет жанр из базы")
    void shouldCorrectDeleteBook() {
        var expected = daoBooks.getByBookId(1);

        daoBooks.delete(expected);

        assertThatThrownBy(() -> daoBooks.getByBookId(expected.getId())).isInstanceOf(EmptyResultDataAccessException.class);
    }
}