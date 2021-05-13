package net.atlassian.libraryapp1.Model;

import org.dizitart.no2.objects.Id;

public class Book {
    @Id
    private String name;
    private String author;
    private String genre;
    private String libraryName;
    private String borrowedDate;
    private String returnedDate;
    private String userName;

    public Book(String name, String author, String genre, String libraryName, String borrowedDate, String returnedDate, String userName) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.libraryName = libraryName;
        this.borrowedDate = borrowedDate;
        this.returnedDate = returnedDate;
        this.userName = userName;
    }
    public Book()
    {

    }

    public Book() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(String borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public String getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(String returnedDate) {
        this.returnedDate = returnedDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
