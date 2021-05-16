package net.atlassian.libraryapp1.Exceptions;

public class InvalidPhoneNumberException extends Exception{
    public InvalidPhoneNumberException() {
        super(String.format("The phone number should have 10 digits!"));
    }

}
