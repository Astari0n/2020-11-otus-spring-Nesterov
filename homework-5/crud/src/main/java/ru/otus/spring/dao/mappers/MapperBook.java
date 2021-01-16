package ru.otus.spring.dao.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.stereotype.Component;

import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
@SuppressWarnings("NullableProblems")
public class MapperBook implements RowMapper<Book> {

    private final MapperAuthor mapperAuthor;

    private final MapperGenre mapperGenre;

    @Override
    public Book mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Author author = mapperAuthor.mapRow(rs, rowNum);
        final Genre genre = mapperGenre.mapRow(rs, rowNum);

        final var book = new Book(author, genre, rs.getString("title"));
        book.setId(rs.getLong("book_id"));

        return book;
    }
}
