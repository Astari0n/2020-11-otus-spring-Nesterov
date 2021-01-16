package ru.otus.spring.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Genre {

    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String name;

    public Genre(final String name) {
        this.name = name;
    }

    public Genre(final long id, final String name) {
        this.id = id;
        this.name = name;
    }
}
