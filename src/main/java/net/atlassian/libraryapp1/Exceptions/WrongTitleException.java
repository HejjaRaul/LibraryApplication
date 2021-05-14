package net.atlassian.libraryapp1.Exceptions;

public class WrongTitleException extends Exception {
    public WrongTitleException() {
        super(String.format("The book does not exist in the database!"));

    }
}
