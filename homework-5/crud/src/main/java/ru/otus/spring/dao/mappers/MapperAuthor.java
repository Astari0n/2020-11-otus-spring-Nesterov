package ru.otus.spring.dao.mappers;

import org.springframework.jdbc.core.RowMapper;

import org.springframework.stereotype.Component;
import ru.otus.spring.model.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MapperAuthor implements RowMapper<Author> {
    @Override
    public Author mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return new Author(rs.getLong("author_id"), rs.getString("author_name"));
    }
}