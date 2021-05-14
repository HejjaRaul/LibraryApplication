package net.atlassian.libraryapp1.Exceptions;

public class CustomerDoesNotHaveTheBookBorrowedException extends Exception {

    public CustomerDoesNotHaveTheBookBorrowedException() {
        super(String.format("You don't have this book borrowed!"));
    }
}
