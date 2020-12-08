package ru.otus.spring.services;

import ru.otus.spring.exceptions.RegisterException;

public interface RegisterService<R, I> {
    R register(I registerInfo) throws RegisterException;
}
