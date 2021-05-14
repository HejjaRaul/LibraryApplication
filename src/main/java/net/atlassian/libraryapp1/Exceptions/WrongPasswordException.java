package net.atlassian.libraryapp1.Exceptions;

public class WrongPasswordException extends Exception {
    public WrongPasswordException() {
        super(String.format("The username and the password does not match!"));
    }
}
