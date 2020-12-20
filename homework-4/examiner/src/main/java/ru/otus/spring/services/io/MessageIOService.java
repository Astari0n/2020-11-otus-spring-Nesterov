package ru.otus.spring.services.io;

import ru.otus.spring.exceptions.IOServiceException;

public interface MessageIOService {

    void printMsg(String key, Object... args) throws IOServiceException;

    void printlnMsg(String key, Object... args) throws IOServiceException;

    void println() throws IOServiceException;

    String readMsg(String key, Object... args) throws IOServiceException;
}
