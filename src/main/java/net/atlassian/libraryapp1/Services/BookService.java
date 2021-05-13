package net.atlassian.libraryapp1.Services;

import net.atlassian.libraryapp1.Exceptions.*;
import net.atlassian.libraryapp1.Model.Book;
import net.atlassian.libraryapp1.Model.LoggedInLibrarian;
import net.atlassian.libraryapp1.Model.User;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import static net.atlassian.libraryapp1.Services.FileSystemService.getPathToFile;

public class BookService {

    public static ObjectRepository<Book> bookRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("BooksDataBase.db").toFile())
                .openOrCreate("test", "test");

        bookRepository = database.getRepository(Book.class);
    }

    public static void addBook(String title, String author, String genre) throws EmptyTitleFieldException, EmptyAuthorFieldException, EmptyGenreFieldException {
        checkEmptyFields(title, author, genre);
        String borrowedDate="";
        String returnedDate="";
        String username="";
        String libraryName="";
        for(User user : UserService.userRepository.find())
        {
            if(user.getUsername().equals(LoggedInLibrarian.getUsername()))
            {
                libraryName=user.getName();
            }
        }

        bookRepository.insert(new Book(title, author, genre, libraryName, borrowedDate, returnedDate, username));
    }
        private static void checkEmptyFields(String title, String author, String genre) throws EmptyTitleFieldException, EmptyAuthorFieldException, EmptyGenreFieldException{
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
        public static void deleteBook(String title) throws WrongTitleException, EmptyTitleFieldException
        {
            int sw=0;
            Book aux=new Book();
            if(title=="")
                throw new EmptyTitleFieldException();
            for(Book book : bookRepository.find())
            {
                if(title.equals(book.getName())) {
                    aux = book;
                    sw=1;
                }
            }
            if(sw==0) {
                throw new WrongTitleException();
            }
            bookRepository.remove(aux);
        }

}
