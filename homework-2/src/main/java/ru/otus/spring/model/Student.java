package ru.otus.spring.model;

public class Student {

    private final String firstName;

    private final String lastName;

    public Student(final String firstName, final String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "User{" +
            "FirstName='" + firstName + '\'' +
            ", LastName='" + lastName + '\'' +
            '}';
    }
}
