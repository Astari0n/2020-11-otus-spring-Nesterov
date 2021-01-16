package ru.otus.spring.dao;

import ru.otus.spring.model.Book;

import java.util.List;

public interface DaoBooks {
    long insert(Book book);

    Book getByBookId(long bookId);

    List<Book> getByAuthorId(long authorId);

    Integer countBooksWithAuthor(long authorId);

    List<Book> getByGenreId(long genreId);

    Integer countBooksWithGenreId(long genreId);

    List<Book> getAll();

    int update(Book book);

    int delete(Book book);
}
