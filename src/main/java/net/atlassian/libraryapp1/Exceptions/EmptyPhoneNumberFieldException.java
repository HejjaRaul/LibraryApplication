package net.atlassian.libraryapp1.Exceptions;

public class EmptyPhoneNumberFieldException extends Exception{

    public EmptyPhoneNumberFieldException()
    {
        super(String.format("The phone number field is empty. You need to complete it"));
    }
}
