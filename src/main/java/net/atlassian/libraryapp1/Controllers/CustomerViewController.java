package net.atlassian.libraryapp1.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import net.atlassian.libraryapp1.Model.BooksOfLibrary;
import net.atlassian.libraryapp1.Model.LoggedInCustomer;
import net.atlassian.libraryapp1.Model.LoggedInLibrarian;
import net.atlassian.libraryapp1.Model.User;
import net.atlassian.libraryapp1.Services.UserService;
import org.dizitart.no2.objects.ObjectRepository;

import java.io.IOException;

public class CustomerViewController {

    @FXML
    public Button listLibraries;
    @FXML
    public Button borrowedBooksList;

    @FXML
    public void handleGoBackToLogIn(ActionEvent goBackToLogIn) throws IOException {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
        Stage window = (Stage) ((Node) goBackToLogIn.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 600, 350));
        window.show();
    }

    @FXML
    public void handleSeeListOfLibraries(ActionEvent seeListOfLibraries) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("LibraryList.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        LibraryListController controller = loader.getController();
        controller.Set();
        Stage stage = (Stage) (listLibraries.getScene().getWindow());
        stage.setTitle("Library list");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleSeeListOfBorrowedBooks(ActionEvent seeListOfBorrowedBooks) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("BorrowedBooksList.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        BorrowedBooksListController controller = loader.getController();
        controller.setBorrowedBooksList();
        Stage stage = (Stage) (borrowedBooksList.getScene().getWindow());
        stage.setTitle("Borrowed Book List");
        stage.setScene(scene);
        stage.show();
    }
}
