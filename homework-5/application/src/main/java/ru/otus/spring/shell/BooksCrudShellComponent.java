package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;

import ru.otus.spring.services.ServiceAuthors;
import ru.otus.spring.services.ServiceBooks;
import ru.otus.spring.services.ServiceGenres;

import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class BooksCrudShellComponent {

    private final ServiceAuthors serviceAuthors;

    private final ServiceGenres serviceGenres;

    private final ServiceBooks serviceBooks;

    @ShellMethod(value = "Create book with title by author id and genre id", key = { "create-book", "cb" })
    public String createBook(final long authorId, final long genreId, final String title) {
        final Author author = serviceAuthors.findAuthorByAuthorId(authorId);
        final Genre genre = serviceGenres.findGenreByGenreId(genreId);
        final Book book = serviceBooks.createBook(author, genre, title);

        return "Created book: " + book;
    }

    @ShellMethod(value = "Change book author by book id", key = { "change-book-author", "cba" })
    public String changeBookAuthorByBookId(final long bookId, final long newAuthorId) {
        final Book book = serviceBooks.findBookByBookId(bookId);
        final Author newAuthor = serviceAuthors.findAuthorByAuthorId(newAuthorId);
        serviceBooks.changeBookAuthor(book, newAuthor);

        return "Changed book: " + book;
    }

    @ShellMethod(value = "Change book genre by book id", key = { "change-book-genre", "cbg" })
    public String changeBookGenreByBookId(final long bookId, final long newGenreId) {
        final Book book = serviceBooks.findBookByBookId(bookId);
        final Genre newGenre = serviceGenres.findGenreByGenreId(newGenreId);
        serviceBooks.changeBookGenre(book, newGenre);

        return "Changed book: " + book;
    }

    @ShellMethod(value = "Change book title by book id", key = { "change-book-title", "cbt" })
    public String changeBookTitleByBookId(final long bookId, final String newTitle) {
        final Book book = serviceBooks.findBookByBookId(bookId);
        serviceBooks.changeBookTitle(book, newTitle);

        return "Changed book: " + book;
    }

    @ShellMethod(value = "Delete book by book id", key = { "delete-book", "db" })
    public String deleteBookByBookId(final long bookId) {
        final Book book = serviceBooks.findBookByBookId(bookId);
        serviceBooks.deleteBook(book);

        return "Deleted book: " + book;
    }

    @ShellMethod(value = "Print book by book id", key = { "print-book", "pb" })
    public String printBookByBookId(final long bookId) {
        final Book book = serviceBooks.findBookByBookId(bookId);
        return "Found book: " + book;
    }

    @ShellMethod(value = "Print all books", key = { "print-all-books", "pab" })
    public String printAllBooks() {
        final var books = serviceBooks.getAll();
        return "Found books: \n" + books.stream().map(Book::toString).collect(Collectors.joining("\n"));
    }
}
