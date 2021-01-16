package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import ru.otus.spring.exception.ServiceException;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Genre;
import ru.otus.spring.services.ServiceGenres;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class GenresCrudShellComponent {

    private final ServiceGenres serviceGenres;

    @ShellMethod(value = "Create genre with genre name", key = { "create-genre", "cg" })
    public String createGenre(final String genreName) {
        String shellMsg;

        try {
            final Genre genre = serviceGenres.createGenre(genreName);
            shellMsg = "Created genre: " + genre;
        } catch (final ServiceException e) {
            shellMsg = String.format("Genre creation failed, %s", e.getLocalizedMessage());
        }

        return shellMsg;
    }

    @ShellMethod(value = "Rename genre", key = { "rename-genre", "rg" })
    public String renameGenreByGenreId(final long genreId, final String newGenreName) {
        String shellMsg;

        try {
            final Genre genre = serviceGenres.findGenreByGenreId(genreId);
            serviceGenres.renameGenre(genre, newGenreName);

            shellMsg = "Renamed genre: " + genre;
        } catch (final ServiceException e) {
            shellMsg = String.format("Genre rename failed, %s", e.getLocalizedMessage());
        }

        return shellMsg;
    }

    @ShellMethod(value = "Delete author", key = { "delete-genre", "dg" })
    public String deleteGenreByGenreId(final long genreId) {
        String shellMsg;

        try {
            final var genre = serviceGenres.findGenreByGenreId(genreId);
            final int deleted = serviceGenres.deleteGenre(genre);

            shellMsg = "Deleted genres: " + deleted;
        } catch (final ServiceException e) {
            shellMsg = String.format("Genre deletion failed, %s", e.getLocalizedMessage());
        }

        return shellMsg;
    }

    @ShellMethod(value = "Print genre", key = { "print-genre", "pg" })
    public String printGenreByGenreId(final long genreId) {
        String shellMsg;

        try {
            final Genre genre = serviceGenres.findGenreByGenreId(genreId);
            shellMsg = "Found genre: " + genre;
        } catch (final ServiceException e) {
            shellMsg = String.format("Genre printing failed, %s", e.getLocalizedMessage());
        }

        return shellMsg;
    }

    @ShellMethod(value = "Print all genres", key = { "print-all-genre", "pag" })
    public String printAllGenres()  {
        String shellMsg;

        try {
            final List<Genre> genres = serviceGenres.getAll();
            shellMsg = "Found genres: \n" + genres.stream().map(Genre::toString).collect(Collectors.joining("\n"));
        } catch (final ServiceException e) {
            shellMsg = String.format("Genres printing failed, %s", e.getLocalizedMessage());
        }

        return shellMsg;
    }
}
