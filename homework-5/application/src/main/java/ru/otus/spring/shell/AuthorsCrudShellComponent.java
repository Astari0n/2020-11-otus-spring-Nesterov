package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import ru.otus.spring.exception.ServiceException;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Genre;
import ru.otus.spring.services.ServiceAuthors;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class AuthorsCrudShellComponent {

    private final ServiceAuthors serviceAuthors;

    @ShellMethod(value = "Create author with author name", key = { "create-author", "ca" })
    public String createAuthor(final String authorName) {
        String shellMsg;

        final Author author;
        try {
            author = serviceAuthors.createAuthor(authorName);
            shellMsg = "Created author: " + author;
        } catch (final ServiceException e) {
            shellMsg = String.format("Author creation failed, %s", e.getLocalizedMessage());
        }

        return shellMsg;
    }

    @ShellMethod(value = "Rename author", key = { "rename-author", "ra" })
    public String renameAuthorByAuthorId(final long authorId, final String newAuthorName) {
        String shellMsg;

        try {
            final var author = serviceAuthors.findAuthorByAuthorId(authorId);
            serviceAuthors.renameAuthor(author, newAuthorName);

            shellMsg = "Renamed author: " + author;
        } catch (final ServiceException e) {
            shellMsg = String.format("Author rename failed, %s", e.getLocalizedMessage());
        }

        return shellMsg;
    }

    @ShellMethod(value = "Delete author", key = { "delete-author", "da" })
    public String deleteAuthorByAuthorId(final long authorId) {
        String shellMsg;

        try {
            final var author = serviceAuthors.findAuthorByAuthorId(authorId);
            final int deleted = serviceAuthors.deleteAuthor(author);

            shellMsg = "Deleted authors: " + deleted;
        } catch (final ServiceException e) {
            shellMsg = String.format("Author deletion failed, %s", e.getLocalizedMessage());
        }

        return shellMsg;
    }

    @ShellMethod(value = "Print author", key = { "print-author", "pa" })
    public String printAuthorByAuthorId(final long authorId) {
        String shellMsg;

        final Author author;
        try {
            author = serviceAuthors.findAuthorByAuthorId(authorId);
            shellMsg = "Found author: " + author;
        } catch (final ServiceException e) {
            shellMsg = String.format("Author printing failed, %s", e.getLocalizedMessage());
        }

        return shellMsg;
    }

    @ShellMethod(value = "Print all authors", key = { "print-all-authors", "paa" })
    public String printAllAuthors() {
        String shellMsg;

        final List<Author> authors;
        try {
            authors = serviceAuthors.getAll();
            shellMsg = "Found authors: \n" + authors.stream().map(Author::toString).collect(Collectors.joining("\n"));
        } catch (final ServiceException e) {
            shellMsg = String.format("Authors printing failed, %s", e.getLocalizedMessage());
        }

        return shellMsg;
    }

}
