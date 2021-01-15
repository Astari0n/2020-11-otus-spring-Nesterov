package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import ru.otus.spring.exception.DeletionException;
import ru.otus.spring.model.Genre;
import ru.otus.spring.services.ServiceGenres;

@ShellComponent
@RequiredArgsConstructor
public class GenresCrudShellComponent {

    private final ServiceGenres serviceGenres;

    @ShellMethod(value = "Create genre with genre name", key = { "create-genre", "cg" })
    public String createGenre(final String genreName) {
        final Genre genre = serviceGenres.createGenre(genreName);
        return "Created genre: " + genre;
    }

    @ShellMethod(value = "Rename genre", key = { "rename-genre", "rg" })
    public String renameGenreByGenreId(final long genreId, final String newGenreName) {
        final Genre genre = serviceGenres.findGenreByGenreId(genreId);
        serviceGenres.renameGenre(genre, newGenreName);
        return "Renamed genre: " + genre;
    }

    @ShellMethod(value = "Delete author", key = { "delete-genre", "dg" })
    public String deleteGenreByGenreId(final long genreId) {
        String shellMsg;

        final var genre = serviceGenres.findGenreByGenreId(genreId);

        try {
            final int deleted = serviceGenres.deleteGenre(genre);
            shellMsg = "Deleted genres: " + deleted;
        } catch (final DeletionException e) {
            shellMsg = String.format("Genre deletion failed, %s", e.getLocalizedMessage());
        }

        return shellMsg;
    }

    @ShellMethod(value = "Print genre", key = { "print-genre", "pg" })
    public String printGenreByGenreId(final long genreId) {
        final Genre genre = serviceGenres.findGenreByGenreId(genreId);
        return "Found genre: " + genre;
    }

    @ShellMethod(value = "Print all genres", key = { "print-all-genre", "pag" })
    public String printAllGenres() {
        final var genres = serviceGenres.getAll();
        return "Found genres: " + genres;
    }
}
