package net.atlassian.libraryapp1.Services;

import net.atlassian.libraryapp1.Exceptions.*;
import net.atlassian.libraryapp1.Model.Book;
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

    public static void addBook(String title, String author, String genre, String libraryName) throws EmptyTitleFieldException, EmptyAuthorFieldException, EmptyGenreFieldException, EmptyLibraryNameFieldException {
        checkEmptyFields(title, author, genre, libraryName);
        String borrowedDate="";
        String returnedDate="";
        String username="";
        bookRepository.insert(new Book(title, author, genre, libraryName, borrowedDate, returnedDate, username));
    }
        private static void checkEmptyFields(String title, String author, String genre, String libraryName) throws EmptyTitleFieldException, EmptyAuthorFieldException, EmptyGenreFieldException, EmptyLibraryNameFieldException {
            if (title == "") {
                throw new EmptyTitleFieldException();
            } else {
                if (author == "") {
                    throw new EmptyAuthorFieldException();
                } else {
                    if (genre == "") {
                        throw new EmptyGenreFieldException();
                    } else {
                        if (libraryName == "") {
                            throw new EmptyLibraryNameFieldException();
                        }
                    }
                }
            }
        }
        public static void deleteBook(String title)
        {
            Book aux=new Book();
            for(Book book : bookRepository.find())
            {
                if(title.equals(book.getName()))
                   aux=book;
            }
            bookRepository.remove(aux);
        }
    }
