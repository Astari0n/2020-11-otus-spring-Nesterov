package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import ru.otus.spring.model.Author;
import ru.otus.spring.services.AuthorsService;

@ShellComponent
@RequiredArgsConstructor
public class AuthorsCrudShellComponent {

    private final AuthorsService authorsService;

    @ShellMethod(value = "Create author with author name", key = { "create-author", "ca" })
    public String createAuthor(final String authorName) {
        final Author author = authorsService.createAuthor(authorName);
        return "Created author: " + author;
    }

    @ShellMethod(value = "Rename author", key = { "rename-author", "ra" })
    public String renameAuthorByAuthorId(final long authorId, final String newAuthorName) {
        final Author author = authorsService.findAuthorByAuthorId(authorId);
        authorsService.renameAuthor(author, newAuthorName);
        return "Renamed author: " + author;
    }

    @ShellMethod(value = "Print author", key = { "print-author", "pa" })
    public String printAuthorByAuthorId(final long authorId) {
        final Author author = authorsService.findAuthorByAuthorId(authorId);
        return "Found author: " + author;
    }

    @ShellMethod(value = "Print all authors", key = { "print-all-authors", "paa" })
    public String printAllAuthors() {
        final var authors = authorsService.getAll();
        return "Found authors: " + authors;
    }

}
