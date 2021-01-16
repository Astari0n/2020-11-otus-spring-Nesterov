package ru.otus.spring.dao.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import org.springframework.stereotype.Repository;

import ru.otus.spring.dao.DaoBooks;
import ru.otus.spring.dao.mappers.MapperBook;

import ru.otus.spring.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class DaoBooksNamedJdbc implements DaoBooks {

    private final NamedParameterJdbcOperations jdbc;

    private final MapperBook mapperBook;

    @Override
    public long insert(final Book book) {
        final var holder = new GeneratedKeyHolder();

        jdbc.update(
            "insert into " +
                "books (author_id, genre_id, title) " +
                "values (:authorId, :genreId, :title)",
            new MapSqlParameterSource()
                .addValue("authorId", book.getAuthor().getId())
                .addValue("genreId", book.getGenre().getId())
                .addValue("title", book.getTitle()),
            holder
        );

        return (Long) Objects.requireNonNull(holder.getKeys()).get("book_id");
    }

    @Override
    public Book getByBookId(final long bookId) {
        return jdbc.queryForObject(
            "select * " +
                "from books b " +
                "left join authors a on a.author_id = b.author_id " +
                "left join genres g on g.genre_id = b.genre_id " +
                "where b.book_id = :bookId",
            Map.of("bookId", bookId),
            mapperBook
        );
    }

    @Override
    public List<Book> getByAuthorId(final long authorId) {
        return jdbc.query(
            "select * " +
                "from books b " +
                "left join authors a on a.author_id = b.author_id " +
                "left join genres g on g.genre_id = b.genre_id " +
                "where b.author_id = :authorId",
            Map.of("authorId", authorId),
            mapperBook
        );
    }

    @Override
    public Integer countBooksWithAuthor(final long authorId) {
        return jdbc.queryForObject(
            "select count(*) as count " +
                "from books b " +
                "left join authors a on a.author_id = b.author_id " +
                "left join genres g on g.genre_id = b.genre_id " +
                "where b.author_id = :authorId",
            Map.of("authorId", authorId),
            (rs, rowNum) -> rs.getInt("count")
        );
    }

    @Override
    public List<Book> getByGenreId(final long genreId) {
        return jdbc.query(
            "select * " +
                "from books b " +
                "left join authors a on a.author_id = b.author_id " +
                "left join genres g on g.genre_id = b.genre_id " +
                "where b.genre_id = :genreId",
            Map.of("genreId", genreId),
            mapperBook
        );
    }

    @Override
    public Integer countBooksWithGenreId(final long genreId) {
        return jdbc.queryForObject(
            "select count(*) as count " +
                "from books b " +
                "left join authors a on a.author_id = b.author_id " +
                "left join genres g on g.genre_id = b.genre_id " +
                "where b.genre_id = :genreId",
            Map.of("genreId", genreId),
            (rs, rowNum) -> rs.getInt("count")
        );
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query(
            "select * " +
                "from books b " +
                "left join authors a on a.author_id = b.author_id " +
                "left join genres g on g.genre_id = b.genre_id ",
            mapperBook
        );
    }

    @Override
    public int update(final Book book) {
        return jdbc.update(
            "update books set " +
                "author_id = :authorId, " +
                "genre_id = :genreId, " +
                "title = :title " +
                "where book_id = :bookId",
            Map.of(
                "authorId", book.getAuthor().getId(),
                "genreId", book.getGenre().getId(),
                "title", book.getTitle(),
                "bookId", book.getId()
            )
        );
    }

    @Override
    public int delete(final Book book) {
        return jdbc.update(
            "delete from books " +
                "where book_id = :bookId",
            Map.of("bookId", book.getId())
        );
    }
}
