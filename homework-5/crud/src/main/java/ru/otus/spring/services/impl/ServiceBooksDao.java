package ru.otus.spring.services.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import ru.otus.spring.dao.DaoBooks;

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
    public Book createBook(final Author author, final Genre genre, final String title) {
        final var book = new Book(author, genre, title);

        long bookId = daoBooks.insert(book);
        book.setId(bookId);

        return book;
    }

    @Override
    public Book findBookByBookId(final long bookId) {
        return daoBooks.getByBookId(bookId);
    }

    @Override
    public List<Book> findBookByAuthorId(final Author author) {
        return daoBooks.getByAuthorId(author.getId());
    }

    @Override
    public List<Book> findBookByGenreId(final Genre genre) {
        return daoBooks.getByGenreId(genre.getId());
    }

    @Override
    public int changeBookAuthor(final Book book, final Author newAuthor) {
        book.setAuthor(newAuthor);
        return daoBooks.update(book);
    }

    @Override
    public int changeBookGenre(final Book book, final Genre newGenre) {
        book.setGenre(newGenre);
        return daoBooks.update(book);
    }

    @Override
    public int changeBookTitle(final Book book, final String newTitle) {
        book.setTitle(newTitle);
        return daoBooks.update(book);
    }

    @Override
    public int deleteBook(final Book book) {
        return daoBooks.delete(book);
    }

    @Override
    public List<Book> getAll() {
        return daoBooks.getAll();
    }
}
