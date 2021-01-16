package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import ru.otus.spring.exception.ServiceException;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;

import ru.otus.spring.services.ServiceAuthors;
import ru.otus.spring.services.ServiceBooks;
import ru.otus.spring.services.ServiceGenres;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class BooksCrudShellComponent {

    private final ServiceAuthors serviceAuthors;

    private final ServiceGenres serviceGenres;

    private final ServiceBooks serviceBooks;

    @ShellMethod(value = "Create book with title by author id and genre id", key = { "create-book", "cb" })
    public String createBook(final long authorId, final long genreId, final String title) {
        String shellMsg;

        try {
            final Author author = serviceAuthors.findAuthorByAuthorId(authorId);
            final Genre genre = serviceGenres.findGenreByGenreId(genreId);
            final Book book = serviceBooks.createBook(author, genre, title);

            shellMsg = "Created book: " + book;
        } catch (final ServiceException e) {
            shellMsg = String.format("Book creation failed, %s", e.getLocalizedMessage());
        }

        return shellMsg;
    }

    @ShellMethod(value = "Change book author by book id", key = { "change-book-author", "cba" })
    public String changeBookAuthorByBookId(final long bookId, final long newAuthorId) {
        String shellMsg;

        try {
            final Book book = serviceBooks.findBookByBookId(bookId);
            final Author newAuthor = serviceAuthors.findAuthorByAuthorId(newAuthorId);
            serviceBooks.changeBookAuthor(book, newAuthor);

            shellMsg = "Changed book: " + book;
        } catch (final ServiceException e) {
            shellMsg = String.format("Changing book author failed, %s", e.getLocalizedMessage());
        }

        return shellMsg;
    }

    @ShellMethod(value = "Change book genre by book id", key = { "change-book-genre", "cbg" })
    public String changeBookGenreByBookId(final long bookId, final long newGenreId) {
        String shellMsg;

        try {
            final Book book = serviceBooks.findBookByBookId(bookId);
            final Genre newGenre = serviceGenres.findGenreByGenreId(newGenreId);
            serviceBooks.changeBookGenre(book, newGenre);

            shellMsg = "Changed book: " + book;
        } catch (final ServiceException e) {
            shellMsg = String.format("Changing book genre failed, %s", e.getLocalizedMessage());
        }

        return shellMsg;
    }

    @ShellMethod(value = "Change book title by book id", key = { "change-book-title", "cbt" })
    public String changeBookTitleByBookId(final long bookId, final String newTitle) {
        String shellMsg;

        try {
            final Book book = serviceBooks.findBookByBookId(bookId);
            serviceBooks.changeBookTitle(book, newTitle);

            shellMsg = "Changed book: " + book;
        } catch (final ServiceException e) {
            shellMsg = String.format("Changing book title failed, %s", e.getLocalizedMessage());
        }

        return shellMsg;
    }

    @ShellMethod(value = "Delete book by book id", key = { "delete-book", "db" })
    public String deleteBookByBookId(final long bookId) {
        String shellMsg;

        try {
            final Book book = serviceBooks.findBookByBookId(bookId);
            serviceBooks.deleteBook(book);

            shellMsg = "Deleted book: " + book;
        } catch (final ServiceException e) {
            shellMsg = String.format("Book deletion failed, %s", e.getLocalizedMessage());
        }

        return shellMsg;
    }

    @ShellMethod(value = "Print book by book id", key = { "print-book", "pb" })
    public String printBookByBookId(final long bookId) {
        String shellMsg;

        try {
            final Book book = serviceBooks.findBookByBookId(bookId);
            shellMsg = "Found book: " + book;
        } catch (final ServiceException e) {
            shellMsg = String.format("Book printing failed, %s", e.getLocalizedMessage());
        }

        return shellMsg;
    }

    @ShellMethod(value = "Print books by author id", key = { "print-books-author", "pba" })
    public String printBookByAuthorId(final long authorId) {
        String shellMsg;

        try {
            final Author author = serviceAuthors.findAuthorByAuthorId(authorId);
            final List<Book> books = serviceBooks.findBooksByAuthor(author);
            shellMsg = "Found books: \n" + books.stream().map(Book::toString).collect(Collectors.joining("\n"));
        } catch (final ServiceException e) {
            shellMsg = String.format("Books printing failed, %s", e.getLocalizedMessage());
        }

        return shellMsg;
    }

    @ShellMethod(value = "Print books by genre id", key = { "print-books-genre", "pbg" })
    public String printBookByGenreId(final long genreId) {
        String shellMsg;

        try {
            final Genre genre = serviceGenres.findGenreByGenreId(genreId);
            final List<Book> books = serviceBooks.findBooksByGenre(genre);
            shellMsg = "Found books: \n" + books.stream().map(Book::toString).collect(Collectors.joining("\n"));
        } catch (final ServiceException e) {
            shellMsg = String.format("Books printing failed, %s", e.getLocalizedMessage());
        }

        return shellMsg;
    }

    @ShellMethod(value = "Print all books", key = { "print-all-books", "pab" })
    public String printAllBooks() {
        String shellMsg;

        final List<Book> books;
        try {
            books = serviceBooks.getAll();
            shellMsg = "Found books: \n" + books.stream().map(Book::toString).collect(Collectors.joining("\n"));
        } catch (final ServiceException e) {
            shellMsg = String.format("Book printing failed, %s", e.getLocalizedMessage());
        }

        return shellMsg;
    }
}
