package ru.otus.spring.services.io;

import ru.otus.spring.exceptions.IOServiceException;

public interface IOService {

    String read() throws IOServiceException;

    void print(String s) throws IOServiceException;

    void println(String s) throws IOServiceException;

    void println() throws IOServiceException;

    void printf(String s, final Object... args) throws IOServiceException;
}
