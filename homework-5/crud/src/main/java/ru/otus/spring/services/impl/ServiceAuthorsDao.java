package ru.otus.spring.services.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import ru.otus.spring.exception.AuthorServiceException;
import ru.otus.spring.exception.ServiceException;

import ru.otus.spring.services.ServiceAuthors;
import ru.otus.spring.services.ServiceBooks;

import ru.otus.spring.dao.DaoAuthors;
import ru.otus.spring.model.Author;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceAuthorsDao implements ServiceAuthors {

    private final DaoAuthors daoAuthors;

    private final ServiceBooks serviceBooks;

    @Override
    public Author createAuthor(final String authorName) throws ServiceException {
        try {
            final var author = new Author(authorName);
            long authorId = daoAuthors.insert(author);
            author.setId(authorId);

            return author;
        } catch (final DataAccessException e) {
            throw new AuthorServiceException(e);
        }
    }

    @Override
    public int renameAuthor(final Author author, final String newAuthorName) throws ServiceException {
        try {
            author.setName(newAuthorName);
            return daoAuthors.update(author);
        } catch (final DataAccessException e) {
            throw new AuthorServiceException(e);
        }
    }

    @Override
    public Author findAuthorByAuthorId(final long authorId) throws ServiceException {
        try {
            return daoAuthors.getById(authorId);
        } catch (final EmptyResultDataAccessException e) {
            throw new AuthorServiceException("Author not found with authorId " + authorId, e);
        } catch (final DataAccessException e) {
            throw new AuthorServiceException(e);
        }
    }

    @Override
    public List<Author> getAll() throws ServiceException {
        try {
            return daoAuthors.getAll();
        } catch (final DataAccessException e) {
            throw new AuthorServiceException(e);
        }
    }

    @Override
    public int deleteAuthor(final Author author) throws ServiceException {
        if (serviceBooks.existsBooksWithAuthor(author)) {
            throw new AuthorServiceException("There are some books with authorId " + author.getId());
        }

        try {
            return daoAuthors.delete(author);
        } catch (final DataAccessException e) {
            throw new AuthorServiceException(e);
        }
    }
}
