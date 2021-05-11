package net.atlassian.libraryapp1.Exceptions;

public class EmptyNameFieldException extends Exception {

    public EmptyNameFieldException() {
        super(String.format("The name field is empty. You need to complete it"));
    }
}
