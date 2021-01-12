package ru.otus.spring.services.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import ru.otus.spring.dao.DaoBooks;

import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;

import ru.otus.spring.services.BooksService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceBooksDao implements BooksService {

    private final DaoBooks daoBooks;

    @Override
    public Book createBook(final Author author, final Genre genre, final String title) {
        return daoBooks.create(author, genre, title);
    }

    @Override
    public Book findBookByBookId(final long bookId) {
        return daoBooks.getById(bookId);
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
