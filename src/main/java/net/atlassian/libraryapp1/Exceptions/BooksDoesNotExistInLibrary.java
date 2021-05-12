package net.atlassian.libraryapp1.Exceptions;

public class BooksDoesNotExistInLibrary extends Exception {

    public BooksDoesNotExistInLibrary() {
        super(String.format("The selected library does not have any books available!"));
    }
}
