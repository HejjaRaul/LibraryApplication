package net.atlassian.libraryapp1.Exceptions;

public class InvalidEmailCustomerException extends Exception{

    public InvalidEmailCustomerException()
    {
        super(String.format("Invalid email address. Try using another email"));
    }
}
