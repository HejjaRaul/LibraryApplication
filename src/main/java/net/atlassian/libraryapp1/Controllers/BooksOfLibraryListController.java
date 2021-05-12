package net.atlassian.libraryapp1.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.atlassian.libraryapp1.Exceptions.BookDoesNotExistInLibrary;
import net.atlassian.libraryapp1.Model.Book;
import net.atlassian.libraryapp1.Model.BooksOfLibrary;
import net.atlassian.libraryapp1.Services.BookService;
import org.dizitart.no2.objects.ObjectRepository;

import java.io.IOException;
import java.util.Objects;

public class BooksOfLibraryListController {

    public static ObjectRepository<Book> bookRepository;

    @FXML
    public TextField bookName;
    @FXML
    public ListView bookList;
    @FXML
    public Hyperlink libraryList;
    @FXML
    public Text authorMessage;
    @FXML
    public Text genreMessage;
    @FXML
    public Text errorMessage;

    public void handleShowInformationOfBook(ActionEvent showInformationOfBook) {

        authorMessage.setText("");
        genreMessage.setText("");
        errorMessage.setText("");

        try {
            BookService.checkBookExistInLibrary(bookName.getText());
            for (Book book : BookService.bookRepository.find()) {
                if (Objects.equals(book.getName(), bookName.getText())) {
                    authorMessage.setText("The author of the book is " + book.getAuthor());
                    genreMessage.setText("The genre of the book is " + book.getGenre());
                }
            }
        } catch (BookDoesNotExistInLibrary e1) {
            errorMessage.setText(e1.getMessage());
        }

    }

    public void Set() throws IOException {

        for (Book book : BookService.bookRepository.find()) {
            if (Objects.equals(book.getLibraryName(), BooksOfLibrary.getLibraryName()) && Objects.equals(book.getUserName(), "")) {
                bookList.getItems().add(book.getName());
            }
        }
    }

    public void handleGoBackToLibraryList(ActionEvent goBackToLibraryList) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("LibraryList.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        LibraryListController controller = loader.getController();
        controller.Set();
        Stage stage = (Stage) (libraryList.getScene().getWindow());
        stage.setTitle("Library list");
        stage.setScene(scene);
        stage.show();
    }
}
