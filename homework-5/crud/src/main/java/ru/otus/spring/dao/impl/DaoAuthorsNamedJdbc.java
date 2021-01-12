package ru.otus.spring.dao.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import org.springframework.stereotype.Repository;

import ru.otus.spring.dao.DaoAuthors;
import ru.otus.spring.dao.mappers.MapperAuthor;
import ru.otus.spring.model.Author;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class DaoAuthorsNamedJdbc implements DaoAuthors {

    private final NamedParameterJdbcOperations jdbc;

    private final MapperAuthor mapperAuthor;

    @Override
    public Author create(final String authorName) {
        final var holder = new GeneratedKeyHolder();

        jdbc.update(
            "insert into " +
                "public.authors (author_name) " +
                "values (:authorName)",
            new MapSqlParameterSource()
                .addValue("authorName", authorName),
            holder
        );

        return new Author((Long) Objects.requireNonNull(holder.getKeys()).get("author_id"), authorName);
    }

    @Override
    public Author getById(final long authorId) {
        return jdbc.queryForObject(
            "select * " +
                "from public.authors " +
                "where author_id = :authorId",
            Map.of("authorId", authorId),
            mapperAuthor
        );
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select * from public.authors", mapperAuthor);
    }

    @Override
    public int update(final Author author) {
        return jdbc.update(
            "update public.authors set " +
                "author_name = :authorName " +
                "where author_id = :authorId",
            Map.of(
                "authorName", author.getName(),
                "authorId", author.getId()
            )
        );
    }

    @Override
    public int delete(final Author author) {
        return jdbc.update(
            "delete from public.authors " +
            "where author_id = :authorId",
            Map.of("authorId", author.getId())
        );
    }
}
