package net.atlassian.libraryapp1.Services;

import net.atlassian.libraryapp1.Exceptions.*;
import net.atlassian.libraryapp1.Model.Book;

import net.atlassian.libraryapp1.Model.BooksOfLibrary;
import net.atlassian.libraryapp1.Model.LoggedInLibrarian;
import net.atlassian.libraryapp1.Model.User;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;

import static org.dizitart.no2.objects.filters.ObjectFilters.eq;
import static org.dizitart.no2.objects.filters.ObjectFilters.and;
import static org.dizitart.no2.objects.filters.ObjectFilters.not;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static net.atlassian.libraryapp1.Services.FileSystemService.getPathToFile;

public class BookService {

    public static ObjectRepository<Book> bookRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("BooksDataBase.db").toFile())
                .openOrCreate("test", "test");

        bookRepository = database.getRepository(Book.class);
    }

    private static void checkBookAlreadyExistInLibrary(String bookName) throws BookAlreadyExistsInLibraryException {

        int sw = 0;

        for (Book book : bookRepository.find()) {
            if (Objects.equals(book.getName(), bookName) && Objects.equals(book.getLibraryName(), BooksOfLibrary.getLibraryName())) {
                sw = 1;
            }
        }

        if (sw == 1) {
            throw new BookAlreadyExistsInLibraryException();
        }
    }

    public static void addBook(String title, String author, String genre) throws EmptyTitleFieldException, EmptyAuthorFieldException, EmptyGenreFieldException, BookAlreadyExistsInLibraryException {

        checkEmptyFields(title, author, genre);
        checkBookAlreadyExistInLibrary(title);

        String borrowedDate = "";
        String returnedDate = "";
        String username = "";
        String libraryName = "";

        for (User user : UserService.userRepository.find()) {
            if (user.getUsername().equals(LoggedInLibrarian.getUsername())) {
                libraryName = user.getName();
            }
        }

        bookRepository.insert(new Book(title, author, genre, libraryName, borrowedDate, returnedDate, username));
    }


    private static void checkEmptyFields(String title, String author, String genre) throws EmptyTitleFieldException, EmptyAuthorFieldException, EmptyGenreFieldException {

        if (title == "") {
            throw new EmptyTitleFieldException();
        } else {
            if (author == "") {
                throw new EmptyAuthorFieldException();
            } else {
                if (genre == "") {
                    throw new EmptyGenreFieldException();
                }
            }
        }
    }

    public static void deleteBook(String title) throws WrongTitleException, EmptyTitleFieldException {

        int sw = 0;
        Book aux = new Book();

        if (title == "") {
            throw new EmptyTitleFieldException();
        }

        for (Book book : bookRepository.find()) {

            if (title.equals(book.getName()) && book.getLibraryName().equals(BooksOfLibrary.getLibraryName())) {
                aux = book;
                sw = 1;
            }
        }

        if (sw == 0) {
            throw new WrongTitleException();
        }

        bookRepository.remove(and(eq("name", title), eq("libraryName", BooksOfLibrary.getLibraryName())), aux);
    }

    private static void checkLibraryNameFieldEmpty(String libraryName) throws EmptyLibraryNameFieldException {

        if (libraryName == "") {
            throw new EmptyLibraryNameFieldException();
        }
    }

    public static void checkLibrary(String libraryName) throws LibraryDoesNotExistException, BooksDoesNotExistInLibraryException, EmptyLibraryNameFieldException {

        checkLibraryNameFieldEmpty(libraryName);
        checkLibraryExist(libraryName);
        checkBooksExistInLibrary(libraryName);
    }

    public static void checkBookExistInLibrary(String bookName) throws BookDoesNotExistInLibraryException, EmptyBookNameFieldException {

        checkBookNameField(bookName);

        int sw = 0;

        for (Book book : bookRepository.find()) {
            if (Objects.equals(book.getLibraryName(), BooksOfLibrary.getLibraryName()) && Objects.equals(book.getName(), bookName) && Objects.equals(book.getUserName(), "")) {
                sw = 1;
            }
        }

        if (sw == 0) {
            throw new BookDoesNotExistInLibraryException();
        }
    }

    private static void checkBooksExistInLibrary(String libraryName) throws BooksDoesNotExistInLibraryException {

        int sw = 0;

        for (Book book : bookRepository.find()) {
            if (Objects.equals(libraryName, book.getLibraryName()) && Objects.equals(book.getUserName(), "")) {
                sw = 1;
            }
        }

        if (sw == 0) {
            throw new BooksDoesNotExistInLibraryException();
        }
    }

    private static void checkLibraryExist(String libraryName) throws LibraryDoesNotExistException {

        int sw = 0;

        for (User user : UserService.userRepository.find()) {
            if (Objects.equals(libraryName, user.getName()) && Objects.equals(user.getRole(), "Librarian")) {
                sw = 1;
            }
        }

        if (sw == 0) {
            throw new LibraryDoesNotExistException();
        }
    }

    private static void checkCustomerAlreadyHasThreeBooksBorrowed(String username) throws CustomerHasThreeBooksBorrowedException {

        int count = 0;

        for (Book book : bookRepository.find()) {
            if (Objects.equals(username, book.getUserName())) {
                count++;
            }
        }

        if (count == 3) {
            throw new CustomerHasThreeBooksBorrowedException();
        }
    }

    private static String getTodayDate() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static int getTimeLeft(String borrowedBookTime) {

        String aux[] = borrowedBookTime.split("/");

        int d = Integer.parseInt(aux[0]);
        int m = Integer.parseInt(aux[1]);
        int y = Integer.parseInt(aux[2]);

        LocalDateTime fromDateTime = LocalDateTime.of(y, m, d, 0, 0, 0);
        LocalDateTime toDateTime = LocalDateTime.now();

        LocalDateTime tempDateTime = LocalDateTime.from(fromDateTime);

        int days = (int) tempDateTime.until(toDateTime, ChronoUnit.DAYS);
        tempDateTime = tempDateTime.plusDays(days);

        return 14 - days;
    }


    public static void borrowBook(String bookName, String username) throws CustomerHasThreeBooksBorrowedException, BookDoesNotExistInLibraryException, EmptyBookNameFieldException {

        checkBookNameField(bookName);
        checkBookExistInLibrary(bookName);
        checkCustomerAlreadyHasThreeBooksBorrowed(username);

        Book newBook = new Book();

        for (Book book : bookRepository.find()) {
            if (Objects.equals(bookName, book.getName()) && Objects.equals(book.getLibraryName(), BooksOfLibrary.getLibraryName())) {
                newBook = book;
            }
        }

        newBook.setUserName(username);
        newBook.setBorrowedDate(getTodayDate());

        bookRepository.update(and(eq("name", bookName), eq("libraryName", BooksOfLibrary.getLibraryName())), newBook);
    }

    private static void checkCustomerHasTheBookBorrowed(String bookName) throws CustomerDoesNotHaveTheBookBorrowedException {

        int sw = 0;

        for (Book book : bookRepository.find()) {
            if (Objects.equals(bookName, book.getName()) && !Objects.equals(book.getBorrowedDate(), "")) {
                sw = 1;
            }
        }

        if (sw == 0) {
            throw new CustomerDoesNotHaveTheBookBorrowedException();
        }
    }

    public static void returnBook(String bookName, String username) throws CustomerDoesNotHaveTheBookBorrowedException, EmptyBookNameFieldException {

        checkBookNameField(bookName);
        checkCustomerHasTheBookBorrowed(bookName);

        Book newBook = new Book();

        for (Book book : bookRepository.find()) {
            if (Objects.equals(bookName, book.getName()) && Objects.equals(username, book.getUserName())) {
                newBook = book;
            }
        }

        newBook.setReturnedDate(getTodayDate());
        newBook.setBorrowedDate("");

        bookRepository.update(and(eq("name", bookName), eq("userName", username)), newBook);

    }

    private static void checkBookNameField(String bookName) throws EmptyBookNameFieldException {

        if (bookName == "") {
            throw new EmptyBookNameFieldException();
        }

    }

    private static void checkBookNeedToBeReturned(String bookName) throws WrongBookNameException {

        int sw = 0;

        for (Book book : bookRepository.find()) {
            if (Objects.equals(book.getName(), bookName) && Objects.equals(book.getReturnedDate(), getTodayDate()) && Objects.equals(book.getLibraryName(), BooksOfLibrary.getLibraryName())) {

                sw = 1;
            }
        }

        if (sw == 0) {
            throw new WrongBookNameException();
        }
    }

    public static void theBookHasBeenReturned(String bookName) throws EmptyBookNameFieldException, WrongBookNameException {

        checkBookNameField(bookName);
        checkBookNeedToBeReturned(bookName);

        Book newBook = new Book();

        for (Book book : bookRepository.find()) {
            if (Objects.equals(book.getName(), bookName) && Objects.equals(book.getReturnedDate(), getTodayDate()) && Objects.equals(book.getLibraryName(), BooksOfLibrary.getLibraryName())) {

                newBook = book;
            }
        }

        newBook.setReturnedDate("");
        newBook.setUserName("");

        bookRepository.update(and(eq("name", bookName), eq("libraryName", BooksOfLibrary.getLibraryName())), newBook);
    }

    private static void checkBookThatShouldHaveBeenReturned(String bookName) throws WrongBookNameException {

        int sw = 0;

        for (Book book : bookRepository.find()) {
            if (Objects.equals(book.getName(), bookName) && !Objects.equals(book.getBorrowedDate(), "") && Objects.equals(book.getLibraryName(), BooksOfLibrary.getLibraryName())) {

                if (BookService.getTimeLeft(book.getBorrowedDate()) < 0) {
                    sw = 1;
                }
            }
        }

        if (sw == 0) {
            throw new WrongBookNameException();
        }
    }


    public static void theBookHasBeenReturned1(String bookName) throws EmptyBookNameFieldException, WrongBookNameException {

        checkBookNameField(bookName);
        checkBookThatShouldHaveBeenReturned(bookName);

        Book newBook = new Book();

        for (Book book : bookRepository.find()) {
            if (Objects.equals(book.getName(), bookName) && !Objects.equals(book.getBorrowedDate(), "") && Objects.equals(book.getLibraryName(), BooksOfLibrary.getLibraryName())) {

                if (BookService.getTimeLeft(book.getBorrowedDate()) < 0) {
                    newBook = book;
                }
            }
        }

        newBook.setBorrowedDate("");
        newBook.setUserName("");

        bookRepository.update(and(eq("name", bookName), eq("libraryName", BooksOfLibrary.getLibraryName())), newBook);
    }

}
