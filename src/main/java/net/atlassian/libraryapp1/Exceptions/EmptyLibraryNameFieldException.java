package net.atlassian.libraryapp1.Exceptions;

public class EmptyLibraryNameFieldException extends Exception {

    public EmptyLibraryNameFieldException() {
        super(String.format("The library name field is empty. You need to complete it!"));
    }
}
