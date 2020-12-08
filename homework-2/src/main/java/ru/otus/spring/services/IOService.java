package ru.otus.spring.services;

import java.io.IOException;

public interface IOService {

    String read() throws IOException;

    void print(String s) throws IOException;

    void println(String s) throws IOException;

    void println() throws IOException;

    void printf(String s, final Object... args) throws IOException;
}
