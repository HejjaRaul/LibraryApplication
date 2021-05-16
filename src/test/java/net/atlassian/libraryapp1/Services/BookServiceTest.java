package net.atlassian.libraryapp1.Services;


import net.atlassian.libraryapp1.Exceptions.*;
import net.atlassian.libraryapp1.Model.*;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

class BookServiceTest {

    @BeforeAll
    static void beforeAll()
    {
        FileSystemService.APPLICATION_FOLDER=".Test-LIB-Database";
        FileSystemService.initDirectory();
        System.out.println("Before class");
    }
    @AfterAll
    static void afterAll()
    {
        System.out.println("AfterAll");
    }
    @BeforeEach
    void setUp() throws Exception
    {
        FileSystemService.APPLICATION_FOLDER = ".test-LIB-Database";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
        BookService.initDatabase();
    }
    @AfterEach
    void tearDown()
    {
        BookService.closeDataBase();
        UserService.closeDataBase();
    }

    @Test
    @DisplayName("Database is initialized, and there are no users")
    void testDatabaseInitializationAndNoUserIsPersisted()
    {
        assertThat(BookService.getAllBooks()).isNotNull();
        assertThat(BookService.getAllBooks()).isEmpty();
    }
    @Test
    @DisplayName("Book is successfully persisted to DataBase")
    void testBookIsAddedToDatabase() throws EmptyTitleFieldException,EmptyAuthorFieldException,EmptyGenreFieldException,BookAlreadyExistsInLibraryException {
        BookService.addBook("admin", "admin", "admin");
        assertThat(BookService.getAllBooks()).isNotEmpty();
        assertThat(BookService.getAllBooks()).size().isEqualTo(1);
        Book book = BookService.getAllBooks().get(0);
        assertThat(book).isNotNull();
        assertThat(book.getName()).isEqualTo("admin");
        assertThat(book.getAuthor()).isEqualTo("admin");
        assertThat(book.getGenre()).isEqualTo("admin");
    }
    @Test
    @DisplayName("Books can not be added twice to the database")
    void testBooksCanNotBeAddedTwice() {
        assertDoesNotThrow(() -> {
            // UserService.addUser("test","test","Customer","test","test@yahoo.com","0735234133");
            UserService.addUser("testl","test","Librarian","library","test@library.com","0735234133");
            LoggedInLibrarian.setUsername("testl");
            BookService.addBook("test", "test", "test");
            BooksOfLibrary.setLibraryName("library");


        });
        assertThrows(BookAlreadyExistsInLibraryException.class, () -> {

            BookService.addBook("test","test","test");

        });

    }
    @Test
    @DisplayName("The title field can't be empty")
    void testTheTitleIsNotEmpty() {
        assertThrows(EmptyTitleFieldException.class, () -> {
            BookService.addBook("", "test", "test");
        });
    }
    @Test
    @DisplayName("The author field can't be empty")
    void testTheAuthorFieldIsNotEmtpy()
    {
        assertThrows(EmptyAuthorFieldException.class, () -> {
            BookService.addBook("test","", "test");
        });
    }
    @Test
    @DisplayName("The author field can't be empty")
    void testTheGenreFieldIsNotEmpty()
    {
        assertThrows(EmptyGenreFieldException.class, () -> {
            BookService.addBook("test","test", "");
        });
    }
    @Test
    @DisplayName("When you want to delete a book, you should write a title that exists in your database")
    void testTheTitleToBeCorrect()
    {
        assertDoesNotThrow(() -> {
            BookService.addBook("test", "test", "test");
        });

        assertThrows(WrongTitleException.class, () -> {
            BookService.deleteBook("wrong");
        });
    }
    @Test
    @DisplayName("The title field can't be empty")
    void testTheTitleIsNotEmptyInDelete() {
        assertThrows(EmptyTitleFieldException.class, () -> {
            BookService.deleteBook("");
        });
    }

    @Test
    @DisplayName("The library's name field should not be empty")
    void testTheLibraryNameIsNotEmpty()
    {
        assertDoesNotThrow(() -> {
            UserService.addUser("test","test","Librarian","test","test@library.com","0735234133");
        });
        assertThrows(EmptyLibraryNameFieldException.class, () -> {
            BookService.checkLibraryNameFieldEmpty("");
        });
    }

    @Test
    @DisplayName("As a customer, if you want to see the information about the book, it must exist in the library")
    void testTheBookExistInTheLibrary()
    {
        assertDoesNotThrow(() -> {
            UserService.addUser("test","test","Librarian","test","test@library.com","0735234133");
        });
        assertThrows(BooksDoesNotExistInLibraryException.class, () -> {
            BookService.checkLibrary("test");
        });
    }
    @Test
    @DisplayName("If a customer want to see the list of books in a library he must enter a name")
    void testTheLibraryNameFieldIsEmpty()
    {
        assertDoesNotThrow(() -> {
            UserService.addUser("test","test","Librarian","test","test@library.com","0735234133");
        });
        assertThrows(EmptyLibraryNameFieldException.class, () -> {
            BookService.checkLibrary("");
        });
    }
    @Test
    @DisplayName("If a customer wants to see the list of books in a library, he must enter a valid name")
    void testIfTheLibraryExistWhenACustomerWantsToSeeItsBooks()
    {
        assertDoesNotThrow(() -> {
            UserService.addUser("test","test","Librarian","library","test@library.com","0735234133");
        });
        assertThrows(LibraryDoesNotExistException.class, () -> {
            BookService.checkLibrary("wrong");
        });
    }
    @Test
    void testCheckBookExist()
    {
        assertDoesNotThrow(() -> {
            UserService.addUser("testl","test","Librarian","library","test@library.com","0735234133");
            LoggedInLibrarian.setUsername("testl");
            BookService.addBook("test","test","test");
            BooksOfLibrary.setLibraryName("library");

        });
        assertThrows(BookDoesNotExistInLibraryException.class, () -> {
            BookService.checkBookExistInLibrary("test1");
        });
    }
    @Test
    @DisplayName("The library must exist in the data base in order to see its books")
    void testIfTheLibraryExist()
    {
        assertDoesNotThrow(() -> {
            UserService.addUser("test","test","Librarian","library","test@library.com","0735234133");
        });
        assertThrows(LibraryDoesNotExistException.class, () -> {
            BookService.checkLibraryExist("wrong");
        });
    }




    @Test
    @DisplayName("A customer can borrow maximum 3 books")
    void testCustomerHasThreeBooksBorrowed()
    {
        assertDoesNotThrow(() -> {
            UserService.addUser("test","test","Customer","test","test@yahoo.com","0735234133");
            UserService.addUser("testl","test","Librarian","library","test@library.com","0735234133");
            LoggedInLibrarian.setUsername("testl");
            BookService.addBook("test1", "test", "test");
            BookService.addBook("test2", "test", "test");
            BookService.addBook("test3", "test", "test");
            BookService.addBook("test4", "test", "test");
            BooksOfLibrary.setLibraryName("library");
            BookService.borrowBook("test1","test");
            BookService.borrowBook("test2","test");
            BookService.borrowBook("test3", "test");

        });
        assertThrows(CustomerHasThreeBooksBorrowedException.class, () -> {

            BookService.borrowBook("test4","test");
        });
    }
    @Test
    @DisplayName("A customer can only borrow books that exists in the library")
    void testBooksExistInTheLibrary()
    {
        assertDoesNotThrow(() -> {
            UserService.addUser("test","test","Customer","test","test@yahoo.com","0735234133");
            UserService.addUser("testl","test","Librarian","library","test@library.com","0735234133");
            LoggedInLibrarian.setUsername("testl");
            BookService.addBook("test1", "test", "test");
            BookService.addBook("test2","test", "test");
            BooksOfLibrary.setLibraryName("library");
            BookService.borrowBook("test1","test");

        });
        assertThrows(BookDoesNotExistInLibraryException.class, () -> {
            BookService.borrowBook("test", "test");
        });
    }
    @Test
    @DisplayName("A customer can only borrow books if the book-name field is not empty")
    void testBookNameIsNotEmpty()
    {
        assertDoesNotThrow(() -> {
            UserService.addUser("test","test","Customer","test","test@yahoo.com","0735234133");
            UserService.addUser("testl","test","Librarian","library","test@library.com","0735234133");
            LoggedInLibrarian.setUsername("testl");
            BookService.addBook("test1", "test", "test");
            BooksOfLibrary.setLibraryName("library");
            BookService.borrowBook("test1","test");

        });
        assertThrows(EmptyBookNameFieldException.class, () -> {
            BookService.borrowBook("", "test");
        });
    }
    @Test
    @DisplayName("Check if the customer has the books he borrowed")
    void testCheckTheBorrowedBooks()
    {
        assertDoesNotThrow(() -> {
            UserService.addUser("test","test","Customer","test","test@yahoo.com","0735234133");
            UserService.addUser("testl","test","Librarian","library","test@library.com","0735234133");
            LoggedInLibrarian.setUsername("testl");
            LoggedInCustomer.setUsername("test");
            BookService.addBook("test1", "test", "test");
            BooksOfLibrary.setLibraryName("library");
            BookService.borrowBook("test1","test");

        });

        assertThrows(CustomerDoesNotHaveTheBookBorrowedException.class, () -> {
            BookService.checkCustomerHasTheBookBorrowed("test");
        });
    }
    @Test
    @DisplayName("In order to return a book, a customer must have borrowed it")
    void testCheckTheBorrowedBooksToReturn()
    {
        assertDoesNotThrow(() -> {
            UserService.addUser("test","test","Customer","test","test@yahoo.com","0735234133");
            UserService.addUser("testl","test","Librarian","library","test@library.com","0735234133");
            LoggedInLibrarian.setUsername("testl");
            LoggedInCustomer.setUsername("test");
            BookService.addBook("test1", "test", "test");
            BooksOfLibrary.setLibraryName("library");
            BookService.borrowBook("test1","test");
            BookService.returnBook("test1","test");

        });

        assertThrows(CustomerDoesNotHaveTheBookBorrowedException.class, () -> {
            BookService.returnBook("test","test");
        });
    }
    @Test
    @DisplayName("In order to return a book, its name must not be empty")
    void testBookNameFieldNotEmptyToReturn()
    {
        assertDoesNotThrow(() -> {
            UserService.addUser("test","test","Customer","test","test@yahoo.com","0735234133");
            UserService.addUser("testl","test","Librarian","library","test@library.com","0735234133");
            LoggedInLibrarian.setUsername("testl");
            LoggedInCustomer.setUsername("test");
            BookService.addBook("test1", "test", "test");
            BooksOfLibrary.setLibraryName("library");
            BookService.borrowBook("test1","test");
            BookService.returnBook("test1", "test");

        });

        assertThrows(EmptyBookNameFieldException.class, () -> {
            BookService.returnBook("","test");
        });
    }
    @Test
    @DisplayName("After a customer returns a book that needed to be returned today, the Book's field name should not be empty")
    void testTheBookHasBeenReturned()
    {
        assertDoesNotThrow(() -> {
            UserService.addUser("test","test","Customer","test","test@yahoo.com","0735234133");
            UserService.addUser("testl","test","Librarian","library","test@library.com","0735234133");
            LoggedInLibrarian.setUsername("testl");
            LoggedInCustomer.setUsername("test");
            BookService.addBook("test1", "test", "test");
            BooksOfLibrary.setLibraryName("library");
            BookService.borrowBook("test1","test");
            BookService.returnBook("test1","test");

        });

        assertThrows(EmptyBookNameFieldException.class, () -> {
            BookService.theBookHasBeenReturned("");
        });
    }
    @Test
    @DisplayName("To confirm the return of a book that neded to be return today, its name must not be wrong")
    void testTheBookHasNotAWrongNameWhenReturned()
    {
        assertDoesNotThrow(() -> {
            UserService.addUser("test","test","Customer","test","test@yahoo.com","0735234133");
            UserService.addUser("testl","test","Librarian","library","test@library.com","0735234133");
            LoggedInLibrarian.setUsername("testl");
            LoggedInCustomer.setUsername("test");
            BookService.addBook("test1", "test", "test");
            BooksOfLibrary.setLibraryName("library");
            BookService.borrowBook("test1","test");
            BookService.returnBook("test1","test");
        });

        assertThrows(WrongBookNameException.class, () -> {
            BookService.theBookHasBeenReturned("wrong");
        });
    }
    @Test
    @DisplayName("In order to return a book, its name must not be empty")
    void testBookNameFieldNotEmptyToReturnFromNotReturnedBooks()
    {
        assertDoesNotThrow(() -> {
            UserService.addUser("test","test","Customer","test","test@yahoo.com","0735234133");
            UserService.addUser("testl","test","Librarian","library","test@library.com","0735234133");
            LoggedInLibrarian.setUsername("testl");
            LoggedInCustomer.setUsername("test");
            BookService.addBook("test1", "test", "test");
            BooksOfLibrary.setLibraryName("library");
            BookService.borrowBook("test1","test");
            BookService.returnBook("test1", "test");

        });

        assertThrows(EmptyBookNameFieldException.class, () -> {
            BookService.returnBook("","test");
        });
    }
    @Test
    @DisplayName("After a customer returns a book that was not returned on time, the book's field name should not be empty")
    void testTheBookHasBeenReturnedFromNotReturnedBooks()
    {
        assertDoesNotThrow(() -> {
            UserService.addUser("test","test","Customer","test","test@yahoo.com","0735234133");
            UserService.addUser("testl","test","Librarian","library","test@library.com","0735234133");
            LoggedInLibrarian.setUsername("testl");
            LoggedInCustomer.setUsername("test");
            BookService.addBook("test1", "test", "test");
            BooksOfLibrary.setLibraryName("library");
            BookService.borrowBook("test1","test");
            BookService.returnBook("test1","test");

        });

        assertThrows(EmptyBookNameFieldException.class, () -> {
            BookService.theBookHasBeenReturned1("");
        });
    }
    @Test
    @DisplayName("A librarian can only confirm the return of a book that was not returned on time if the book's name is not wrong ")
    void testTheBookHasNotAWrongNameWhenReturnedFromNotReturnedBooks()
    {
        assertDoesNotThrow(() -> {
            UserService.addUser("test","test","Customer","test","test@yahoo.com","0735234133");
            UserService.addUser("testl","test","Librarian","library","test@library.com","0735234133");
            LoggedInLibrarian.setUsername("testl");
            LoggedInCustomer.setUsername("test");
            BookService.addBook("test1", "test", "test");
            BooksOfLibrary.setLibraryName("library");
            BookService.borrowBook("test1","test");
            BookService.returnBook("test1","test");
        });

        assertThrows(WrongBookNameException.class, () -> {
            BookService.theBookHasBeenReturned1("wrong");
        });
    }
    @Test
    @DisplayName("Testing if the method for calculating the time left before you need to return a book is valid")
    void testTimeLeftForABorrowedBook()
    {
        assertDoesNotThrow(() -> {
            UserService.addUser("test","test","Customer","test","test@yahoo.com","0735234133");
            UserService.addUser("testl","test","Librarian","library","test@library.com","0735234133");
            LoggedInLibrarian.setUsername("testl");
            LoggedInCustomer.setUsername("test");
            BookService.addBook("test1", "test", "test");
            BooksOfLibrary.setLibraryName("library");
            BookService.borrowBook("test1","test");

        });
        Book book=BookService.getAllBooks().get(0);
        assertThat(BookService.getTimeLeft(book.getBorrowedDate())).isEqualTo(14);

    }
}

