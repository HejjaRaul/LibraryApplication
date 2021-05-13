package net.atlassian.libraryapp1.Exceptions;

public class CustomerHasThreeBooksBorrowedException extends Exception {

    public CustomerHasThreeBooksBorrowedException() {
        super(String.format("You already have 3 books borrowed."));
    }
}
