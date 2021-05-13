package net.atlassian.libraryapp1.Exceptions;

public class LibraryDoesNotExistException extends Exception {

    public LibraryDoesNotExistException() {
        super(String.format("The selected library does not exist in the database!"));
    }

}
