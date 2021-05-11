package net.atlassian.libraryapp1.Services;

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
}
