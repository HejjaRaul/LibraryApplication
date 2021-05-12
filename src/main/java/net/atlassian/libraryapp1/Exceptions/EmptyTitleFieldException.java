package net.atlassian.libraryapp1.Exceptions;

public class EmptyTitleFieldException extends Exception {

    public EmptyTitleFieldException() {
        super(String.format("The title field is empty. You need to complete it"));
    }
}
