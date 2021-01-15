package ru.otus.spring.dao.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import org.springframework.stereotype.Repository;

import ru.otus.spring.dao.DaoGenres;
import ru.otus.spring.dao.mappers.MapperGenre;
import ru.otus.spring.model.Genre;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class DaoGenresNamedJdbc implements DaoGenres {

    private final NamedParameterJdbcOperations jdbc;

    private final MapperGenre mapperGenre;

    @Override
    public long insert(final Genre genre) {
        final var holder = new GeneratedKeyHolder();

        jdbc.update(
            "insert into " +
                "genres (genre_name) " +
                "values (:genreName)",
            new MapSqlParameterSource().addValue("genreName", genre.getName()),
            holder
        );

        return (Long) Objects.requireNonNull(holder.getKeys()).get("genre_id");
    }

    @Override
    public Genre getById(final long genreId) {
        return jdbc.queryForObject(
            "select * " +
                "from genres " +
                "where genre_id = :genreId",
            Map.of("genreId", genreId),
            mapperGenre
        );
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select * from genres", mapperGenre);
    }

    @Override
    public int update(final Genre genre) {
        return jdbc.update(
            "update genres " +
                "set genre_name = :genreName " +
                "where genre_id = :genreId",
            Map.of(
                "genreName", genre.getName(),
                "genreId", genre.getId()
            )
        );
    }

    @Override
    public int delete(final Genre genre) {
        return jdbc.update(
            "delete from genres " +
                "where genre_id = :genreId",
            Map.of("genreId", genre.getId())
        );
    }
}
