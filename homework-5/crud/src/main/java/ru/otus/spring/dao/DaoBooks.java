package ru.otus.spring.dao;

import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;

import java.util.List;

public interface DaoBooks {
    Book create(Author author, Genre genre, String title);

    Book getById(long bookId);

    List<Book> getAll();

    int update(Book book);

    int delete(Book book);
}
