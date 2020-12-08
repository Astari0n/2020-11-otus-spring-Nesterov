package ru.otus.spring.services;

public interface ModifierService<D> {
    D modify(D data);
}
