package net.atlassian.libraryapp1.Exceptions;

public class BookDoesNotExistInLibrary extends Exception {

    public BookDoesNotExistInLibrary() {
        super(String.format("The selected book does not exits in this library"));
    }
}
