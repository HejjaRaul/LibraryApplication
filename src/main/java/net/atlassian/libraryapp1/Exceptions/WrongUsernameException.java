package net.atlassian.libraryapp1.Exceptions;

public class WrongUsernameException extends Exception {

    public WrongUsernameException() {
        super(String.format("The username does not exist in the database!"));
    }
}
