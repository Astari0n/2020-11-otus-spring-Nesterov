package ru.otus.spring.dao;

import ru.otus.spring.model.Author;

import java.util.List;

public interface DaoAuthors {
    Author create(String authorName);

    Author getById(long authorId);

    List<Author> getAll();

    int update(Author author);

    int delete(Author author);
}
