package ru.otus.spring.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class Student {

    @Getter
    private final String firstName;

    @Getter
    private final String lastName;
}
