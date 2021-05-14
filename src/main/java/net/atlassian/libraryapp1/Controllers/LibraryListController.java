package net.atlassian.libraryapp1.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.atlassian.libraryapp1.Exceptions.BooksDoesNotExistInLibraryException;
import net.atlassian.libraryapp1.Exceptions.EmptyLibraryNameFieldException;
import net.atlassian.libraryapp1.Exceptions.LibraryDoesNotExistException;
import net.atlassian.libraryapp1.Model.Book;
import net.atlassian.libraryapp1.Model.BooksOfLibrary;
import net.atlassian.libraryapp1.Model.User;
import net.atlassian.libraryapp1.Services.BookService;
import net.atlassian.libraryapp1.Services.UserService;
import org.dizitart.no2.objects.ObjectRepository;

import java.io.IOException;
import java.util.Objects;

public class LibraryListController {

    public static ObjectRepository<User> userRepository;
    public static ObjectRepository<Book> bookRepository;

    @FXML
    public ListView LibraryList;
    @FXML
    public TextField libraryName;
    @FXML
    public Button libraryList;
    @FXML
    public Text showBooksOfLibraryMessage;

    public void Set() {
        for (User user : UserService.userRepository.find()) {
            if (Objects.equals(user.getRole(), "Librarian")) {
                LibraryList.getItems().add(user.getName());
            }
        }
    }

    @FXML
    public void handleShowBooksOfLibrary(ActionEvent showBooksOfLibrary) throws IOException {

        BooksOfLibrary.setLibraryName(libraryName.getText());

        try {
            BookService.checkLibrary(libraryName.getText());
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("BooksOfLibraryList.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            BooksOfLibraryListController controller = loader.getController();
            controller.Set();
            Stage stage = (Stage) (libraryList.getScene().getWindow());
            stage.setTitle("Book list");
            stage.setScene(scene);
            stage.show();
        } catch (EmptyLibraryNameFieldException e1) {
            showBooksOfLibraryMessage.setText(e1.getMessage());
        } catch (LibraryDoesNotExistException e2) {
            showBooksOfLibraryMessage.setText(e2.getMessage());
        } catch (BooksDoesNotExistInLibraryException e3) {
            showBooksOfLibraryMessage.setText(e3.getMessage());
        }
    }

    @FXML
    public void handleGoBackToCustomerView(ActionEvent goBackToCustomerView) throws IOException {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("CustomerView.fxml"));
        Stage window = (Stage) ((Node) goBackToCustomerView.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 519, 398));
        window.show();
    }
}
