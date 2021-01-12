package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import ru.otus.spring.model.Genre;
import ru.otus.spring.services.GenresService;

@ShellComponent
@RequiredArgsConstructor
public class GenresCrudShellComponent {

    private final GenresService genresService;

    @ShellMethod(value = "Create genre with genre name", key = { "create-genre", "cg" })
    public String createGenre(final String genreName) {
        final Genre genre = genresService.createGenre(genreName);
        return "Created genre: " + genre;
    }

    @ShellMethod(value = "Rename genre", key = { "rename-genre", "rg" })
    public String renameGenreByGenreId(final long genreId, final String newGenreName) {
        final Genre genre = genresService.findGenreByGenreId(genreId);
        genresService.renameGenre(genre, newGenreName);
        return "Renamed genre: " + genre;
    }

    @ShellMethod(value = "Print genre", key = { "print-genre", "pg" })
    public String printGenreByGenreId(final long genreId) {
        final Genre genre = genresService.findGenreByGenreId(genreId);
        return "Found genre: " + genre;
    }

    @ShellMethod(value = "Print all genres", key = { "print-all-genre", "pag" })
    public String printAllGenres() {
        final var genres = genresService.getAll();
        return "Found genres: " + genres;
    }
}
