package net.atlassian.libraryapp1.Exceptions;

public class EmptyAuthorFieldException extends Exception{

    public EmptyAuthorFieldException() {

        super(String.format("The author field is empty. You need to complete it"));
    }
}
