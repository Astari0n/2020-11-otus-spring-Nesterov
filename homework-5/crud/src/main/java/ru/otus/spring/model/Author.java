package ru.otus.spring.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Author {

    @Getter
    private final long id;

    @Getter
    @Setter
    private String name;

    public Author(final long id, final String name) {
        this.id = id;
        this.name = name;
    }
}
