package ru.otus.spring.services;

import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;

import java.util.List;

public interface BooksService {
    Book createBook(Author author, Genre genre, String title);

    Book findBookByBookId(long bookId);

    int changeBookAuthor(Book book, Author newAuthor);

    int changeBookGenre(Book book, Genre newGenre);

    int changeBookTitle(Book book, String newTitle);

    int deleteBook(Book book);

    List<Book> getAll();
}
