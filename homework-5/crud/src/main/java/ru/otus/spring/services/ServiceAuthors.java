package ru.otus.spring.services;

import ru.otus.spring.exception.DeletionException;
import ru.otus.spring.model.Author;

import java.util.List;

public interface ServiceAuthors {
    Author createAuthor(String authorName);

    int renameAuthor(Author author, String newAuthorName);

    Author findAuthorByAuthorId(long authorId);

    List<Author> getAll();

    int deleteAuthor(Author author) throws DeletionException;
}
