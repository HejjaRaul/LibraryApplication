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
import javafx.stage.Stage;
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

    public void handleShowInformationOfBook(ActionEvent showInformationOfBook) {

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
