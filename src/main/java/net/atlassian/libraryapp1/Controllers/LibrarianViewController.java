package net.atlassian.libraryapp1.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LibrarianViewController {

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
}
