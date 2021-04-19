package net.atlassian.libraryapp1.Exceptions;

public class EmptyUsernameFieldException extends  Exception{

    public EmptyUsernameFieldException()
    {
        super(String.format("The username filed is empty. You need to complete it"));
    }
}
