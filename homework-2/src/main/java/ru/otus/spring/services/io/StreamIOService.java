package ru.otus.spring.services.io;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class StreamIOService implements IOService {

    private final Scanner scanner;

    private final PrintStream printStream;

    public StreamIOService(final InputStream inputStream, final PrintStream printStream) {
        this.scanner = new Scanner(inputStream);
        this.printStream = printStream;
    }

    @Override
    public String read() {
        return scanner.nextLine();
    }

    @Override
    public void print(final String s) {
        printStream.print(s);
    }

    @Override
    public void println() {
        printStream.print("\n");
    }

    @Override
    public void println(final String s) {
        printStream.print(s);
        printStream.print("\n");
    }

    @Override
    public void printf(final String s, final Object... args) {
        printStream.printf(s, args);
    }
}
