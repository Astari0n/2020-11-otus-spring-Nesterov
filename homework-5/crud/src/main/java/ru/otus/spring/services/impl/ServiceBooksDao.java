package ru.otus.spring.services.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import ru.otus.spring.dao.DaoBooks;

import ru.otus.spring.exception.BookServiceException;
import ru.otus.spring.exception.ServiceException;

import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;

import ru.otus.spring.services.ServiceBooks;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceBooksDao implements ServiceBooks {

    private final DaoBooks daoBooks;

    @Override
    public Book createBook(final Author author, final Genre genre, final String title) throws ServiceException {
        try {
            final var book = new Book(author, genre, title);

            long bookId = daoBooks.insert(book);
            book.setId(bookId);

            return book;
        } catch (final DataAccessException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public Book findBookByBookId(final long bookId) throws ServiceException {
        try {
            return daoBooks.getByBookId(bookId);
        } catch (final EmptyResultDataAccessException e) {
            throw new BookServiceException("Book not found with bookId " + bookId, e);
        } catch (final DataAccessException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public List<Book> findBooksByAuthor(final Author author) throws ServiceException {
        try {
            return daoBooks.getByAuthorId(author.getId());
        } catch (final EmptyResultDataAccessException e) {
            throw new BookServiceException("Book not found with author " + author, e);
        } catch (final DataAccessException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public int countBooksWithAuthor(final Author author) throws ServiceException {
        try {
            return daoBooks.countBooksWithAuthor(author.getId());
        } catch (final DataAccessException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public boolean existsBooksWithAuthor(final Author author) throws ServiceException {
        return countBooksWithAuthor(author) > 0;
    }

    @Override
    public List<Book> findBooksByGenre(final Genre genre) throws ServiceException {
        try {
            return daoBooks.getByGenreId(genre.getId());
        } catch (final EmptyResultDataAccessException e) {
            throw new BookServiceException("Book not found with genre " + genre, e);
        } catch (final DataAccessException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public int countBooksWithGenre(final Genre genre) throws ServiceException {
        try {
            return daoBooks.countBooksWithGenre(genre.getId());
        } catch (final DataAccessException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public boolean existsBooksWithGenre(final Genre genre) throws ServiceException {
        return countBooksWithGenre(genre) > 0;
    }

    @Override
    public int changeBookAuthor(final Book book, final Author newAuthor) throws ServiceException {
        try {
            book.setAuthor(newAuthor);
            return daoBooks.update(book);
        } catch (final DataAccessException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public int changeBookGenre(final Book book, final Genre newGenre) throws ServiceException {
        try {
            book.setGenre(newGenre);
            return daoBooks.update(book);
        } catch (final DataAccessException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public int changeBookTitle(final Book book, final String newTitle) throws ServiceException {
        try {
            book.setTitle(newTitle);
            return daoBooks.update(book);
        } catch (final DataAccessException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public int deleteBook(final Book book) throws ServiceException {
        try {
            return daoBooks.delete(book);
        } catch (final DataAccessException e) {
            throw new BookServiceException(e);
        }
    }

    @Override
    public List<Book> getAll() throws ServiceException {
        try {
            return daoBooks.getAll();
        } catch (final DataAccessException e) {
            throw new BookServiceException(e);
        }
    }
}
