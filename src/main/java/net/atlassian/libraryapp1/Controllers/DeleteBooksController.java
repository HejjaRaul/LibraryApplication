package net.atlassian.libraryapp1.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.atlassian.libraryapp1.Exceptions.*;
import net.atlassian.libraryapp1.Services.BookService;
import net.atlassian.libraryapp1.Services.UserService;


public class DeleteBooksController {

    @FXML
    private TextField deleteBooksField;
    @FXML
    private Text deleteBookMessage;
    public void handleDeleteBookAction()
    {
            try {
                BookService.deleteBook(deleteBooksField.getText());
                deleteBookMessage.setText("Book deleted successfully!");
            }  catch (EmptyTitleFieldException e2)
            {
                deleteBookMessage.setText(e2.getMessage());
            }
            catch (WrongTitleException e1)
            {
                deleteBookMessage.setText(e1.getMessage());
            }

    }
    public void goBackToLibrarianView(ActionEvent goBackToLibrarianView) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("LibrarianView.fxml"));
        Stage window = (Stage) ((Node) goBackToLibrarianView.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 600, 350));
        window.show();
    }
}