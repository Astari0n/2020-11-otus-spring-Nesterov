package ru.otus.spring.services;

import ru.otus.spring.exception.ServiceException;
import ru.otus.spring.model.Author;

import java.util.List;

public interface ServiceAuthors {
    Author createAuthor(String authorName) throws ServiceException;

    int renameAuthor(Author author, String newAuthorName) throws ServiceException;

    Author findAuthorByAuthorId(long authorId) throws ServiceException;

    List<Author> getAll() throws ServiceException;

    int deleteAuthor(Author author) throws ServiceException;
}
