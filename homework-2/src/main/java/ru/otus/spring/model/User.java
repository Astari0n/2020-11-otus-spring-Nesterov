package ru.otus.spring.model;

public class User {

    private final String FirstName;

    private final String LastName;

    public User(final String firstName, final String lastName) {
        FirstName = firstName;
        LastName = lastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    @Override
    public String toString() {
        return "User{" +
            "FirstName='" + FirstName + '\'' +
            ", LastName='" + LastName + '\'' +
            '}';
    }
}
