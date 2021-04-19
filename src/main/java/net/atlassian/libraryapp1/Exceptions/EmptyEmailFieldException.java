package net.atlassian.libraryapp1.Exceptions;

public class EmptyEmailFieldException extends Exception{

    public EmptyEmailFieldException()
    {
        super(String.format("The email field is empty. You need to complete it"));
    }
}
