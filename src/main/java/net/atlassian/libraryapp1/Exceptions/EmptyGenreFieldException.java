package net.atlassian.libraryapp1.Exceptions;

public class EmptyGenreFieldException extends Exception {

    public EmptyGenreFieldException() {

        super(String.format("The genre field is empty. You need to complete it!"));
    }
}
