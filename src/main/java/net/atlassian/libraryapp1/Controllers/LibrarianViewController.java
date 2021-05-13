package net.atlassian.libraryapp1.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LibrarianViewController {

    @FXML
    private Button deleteBooksButton;
    @FXML
    private Button booksThatNeedToBeReturnedTodayButton;
    public void handleGoBackToLogIn(ActionEvent goBackToLogIn) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
        Stage window = (Stage) ((Node) goBackToLogIn.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 600, 350));
        window.show();
    }
    @FXML
    public void handleAddBooksAction(ActionEvent handleAddBooksAction) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("AddBooks.fxml"));
        Stage window = (Stage) ((Node) handleAddBooksAction.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 600, 450));
        window.show();
    }
    @FXML
    public void handleDeleteBooksAction(ActionEvent handleDeleteBooksAction) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("DeleteBooks.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        DeleteBooksController controller = loader.getController();
        controller.setTheListOfBooks();
        Stage stage = (Stage) (deleteBooksButton.getScene().getWindow());
        stage.setTitle("Here you can delete books from your library");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void handleBooksThatNeedToBeReturnedTodayAction(ActionEvent handleBooksThatNeedToBeReturnedAction) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("SeeTheBooksThatNeedToBeReturnedToday.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        SeeTheBooksThatNeedToBeReturnedTodayController controller = loader.getController();
        controller.returnToday();
        Stage stage = (Stage) (booksThatNeedToBeReturnedTodayButton.getScene().getWindow());
        stage.setTitle("List of books that need to be returned today to you Library");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void handleNotReturnedBooksAction(ActionEvent handleNotReturnedBooksAction) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("NotReturnedBooks.fxml"));
        Stage window = (Stage) ((Node) handleNotReturnedBooksAction.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 600, 450));
        window.show();
    }



}
