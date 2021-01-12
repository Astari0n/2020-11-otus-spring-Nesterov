package ru.otus.spring.dao.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import org.springframework.stereotype.Repository;

import ru.otus.spring.dao.DaoBooks;
import ru.otus.spring.dao.mappers.MapperBook;

import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class DaoBooksNamedJdbc implements DaoBooks {

    private final NamedParameterJdbcOperations jdbc;

    private final MapperBook mapperBook;

    @Override
    public Book create(final Author author, final Genre genre, final String title) {
        final var holder = new GeneratedKeyHolder();

        jdbc.update(
            "insert into " +
                "public.books (author_id, genre_id, title) " +
                "values (:authorId, :genreId, :title)",
            new MapSqlParameterSource()
                .addValue("authorId", author.getId())
                .addValue("genreId", genre.getId())
                .addValue("title", title),
            holder
        );

        return new Book((Long) Objects.requireNonNull(holder.getKeys()).get("book_id"), author, genre, title);
    }

    @Override
    public Book getById(final long bookId) {
        return jdbc.queryForObject(
            "select * " +
                "from public.books " +
                "left join public.authors using (author_id) " +
                "left join public.genres using (genre_id) " +
                "where book_id = :bookId",
            Map.of("bookId", bookId),
            mapperBook
        );
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query(
            "select * " +
                "from public.books " +
                "left join public.authors using (author_id) " +
                "left join public.genres using (genre_id) ",
            mapperBook
        );
    }

    @Override
    public int update(final Book book) {
        return jdbc.update(
            "update public.books set " +
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
            "delete from public.books " +
                "where book_id = :bookId",
            Map.of("bookId", book.getId())
        );
    }
}
