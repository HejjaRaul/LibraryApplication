package net.atlassian.libraryapp1.Exceptions;

public class WrongBookNameException extends Exception {

    public WrongBookNameException() {
        super(String.format("The selected book does not exist in the database!"));
    }
}
