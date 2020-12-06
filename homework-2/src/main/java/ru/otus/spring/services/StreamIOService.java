package ru.otus.spring.services;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class StreamIOService implements IOService {

    final Scanner scanner;

    final PrintStream printStream;

    public StreamIOService(final InputStream inputStream, final PrintStream printStream) {
        this.scanner = new Scanner(inputStream);
        this.printStream = printStream;
    }

    @Override
    public String readString() {
        return scanner.nextLine();
    }

    @Override
    public void writeString(final String s) {
        printStream.print(s);
    }
}
