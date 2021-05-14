package net.atlassian.libraryapp1.Exceptions;

public class BookAlreadyExistsInLibraryException extends Exception{

    public BookAlreadyExistsInLibraryException(){
        super(String.format("This book is already at this library!"));
    }
}
