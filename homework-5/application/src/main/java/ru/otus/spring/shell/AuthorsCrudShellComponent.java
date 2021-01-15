package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import ru.otus.spring.exception.DeletionException;
import ru.otus.spring.model.Author;
import ru.otus.spring.services.ServiceAuthors;

@ShellComponent
@RequiredArgsConstructor
public class AuthorsCrudShellComponent {

    private final ServiceAuthors serviceAuthors;

    @ShellMethod(value = "Create author with author name", key = { "create-author", "ca" })
    public String createAuthor(final String authorName) {
        final Author author = serviceAuthors.createAuthor(authorName);
        return "Created author: " + author;
    }

    @ShellMethod(value = "Rename author", key = { "rename-author", "ra" })
    public String renameAuthorByAuthorId(final long authorId, final String newAuthorName) {
        final Author author = serviceAuthors.findAuthorByAuthorId(authorId);
        serviceAuthors.renameAuthor(author, newAuthorName);
        return "Renamed author: " + author;
    }

    @ShellMethod(value = "Delete author", key = { "delete-author", "da" })
    public String deleteAuthorByAuthorId(final long authorId) {
        String shellMsg;

        final var author = serviceAuthors.findAuthorByAuthorId(authorId);

        try {
            final int deleted = serviceAuthors.deleteAuthor(author);
            shellMsg = "Deleted authors: " + deleted;
        } catch (final DeletionException e) {
            shellMsg = String.format("Author deletion failed, %s", e.getLocalizedMessage());
        }

        return shellMsg;
    }

    @ShellMethod(value = "Print author", key = { "print-author", "pa" })
    public String printAuthorByAuthorId(final long authorId) {
        final Author author = serviceAuthors.findAuthorByAuthorId(authorId);
        return "Found author: " + author;
    }

    @ShellMethod(value = "Print all authors", key = { "print-all-authors", "paa" })
    public String printAllAuthors() {
        final var authors = serviceAuthors.getAll();
        return "Found authors: " + authors;
    }

}
