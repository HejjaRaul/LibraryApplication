package net.atlassian.libraryapp1.Exceptions;

public class BookDoesNotExistInLibraryException extends Exception {

    public BookDoesNotExistInLibraryException() {
        super(String.format("The selected book does not exits in this library or is not available!"));
    }
}
