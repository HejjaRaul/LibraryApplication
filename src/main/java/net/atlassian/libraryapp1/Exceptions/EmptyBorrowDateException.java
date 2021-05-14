package net.atlassian.libraryapp1.Exceptions;

public class EmptyBorrowDateException extends Exception {
    public EmptyBorrowDateException() {

        super(String.format("The borrow date field is empty. You need to complete it"));
    }
}
