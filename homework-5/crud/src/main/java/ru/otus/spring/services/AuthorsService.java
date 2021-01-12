package ru.otus.spring.services;

import ru.otus.spring.model.Author;

import java.util.List;

public interface AuthorsService {
    Author createAuthor(String authorName);

    int renameAuthor(Author author, String newAuthorName);

    Author findAuthorByAuthorId(long authorId);

    List<Author> getAll();

    int deleteAuthor(Author author);
}
