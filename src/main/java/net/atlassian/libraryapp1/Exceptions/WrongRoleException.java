package net.atlassian.libraryapp1.Exceptions;

public class WrongRoleException extends Exception {

    public WrongRoleException() {
        super(String.format("The selected role is incorrect"));
    }
}
