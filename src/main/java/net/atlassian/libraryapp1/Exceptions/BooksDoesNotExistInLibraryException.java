package net.atlassian.libraryapp1.Exceptions;

public class BooksDoesNotExistInLibraryException extends Exception {

    public BooksDoesNotExistInLibraryException() {
        super(String.format("The selected library does not have any books available!"));
    }
}
