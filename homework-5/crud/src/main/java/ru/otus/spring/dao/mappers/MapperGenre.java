package ru.otus.spring.dao.mappers;

import org.springframework.jdbc.core.RowMapper;

import org.springframework.stereotype.Component;
import ru.otus.spring.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MapperGenre implements RowMapper<Genre> {
    @Override
    public Genre mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final var genre = new Genre(rs.getString("genre_name"));
        genre.setId(rs.getLong("genre_id"));

        return genre;
    }
}
