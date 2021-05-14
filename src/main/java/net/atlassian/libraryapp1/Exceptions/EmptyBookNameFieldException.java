package net.atlassian.libraryapp1.Exceptions;

public class EmptyBookNameFieldException extends Exception {

    public EmptyBookNameFieldException() {
        super(String.format("You need to write a book's name"));
    }
}
