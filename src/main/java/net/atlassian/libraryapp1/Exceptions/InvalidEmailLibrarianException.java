package net.atlassian.libraryapp1.Exceptions;

public class InvalidEmailLibrarianException extends Exception {

    public InvalidEmailLibrarianException() {
        super(String.format("You can't create a librarian account without a librarian email address"));
    }
}
