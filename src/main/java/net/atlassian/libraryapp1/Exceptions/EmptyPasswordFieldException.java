package net.atlassian.libraryapp1.Exceptions;

public class EmptyPasswordFieldException extends Exception {

    public EmptyPasswordFieldException() {
        super(String.format("The password filed is empty. You need to complete it!"));
    }
}
