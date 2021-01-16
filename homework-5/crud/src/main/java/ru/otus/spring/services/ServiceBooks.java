package ru.otus.spring.services;

import ru.otus.spring.exception.ServiceException;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;

import java.util.List;

public interface ServiceBooks {
    Book createBook(Author author, Genre genre, String title) throws ServiceException;

    Book findBookByBookId(long bookId) throws ServiceException;

    List<Book> findBooksByAuthor(Author author) throws ServiceException;

    int countBooksWithAuthor(Author author) throws ServiceException;

    List<Book> findBooksByGenre(Genre genre) throws ServiceException;

    int countBooksWithGenre(Genre genre) throws ServiceException;

    int changeBookAuthor(Book book, Author newAuthor) throws ServiceException;

    int changeBookGenre(Book book, Genre newGenre) throws ServiceException;

    int changeBookTitle(Book book, String newTitle) throws ServiceException;

    int deleteBook(Book book) throws ServiceException;

    List<Book> getAll() throws ServiceException;
}
