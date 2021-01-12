package ru.otus.spring.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Book {

    @Getter
    private final long id;

    @Getter
    @Setter
    private Author author;

    @Getter
    @Setter
    private Genre genre;

    @Getter
    @Setter
    private String title;

    public Book(final long id, final Author author, final Genre genre, final String title) {
        this.id = id;
        this.author = author;
        this.genre = genre;
        this.title = title;
    }
}
