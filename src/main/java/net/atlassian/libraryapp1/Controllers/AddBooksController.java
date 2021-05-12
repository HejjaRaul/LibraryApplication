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

public class AddBooksController {

    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField genreField;
    @FXML
    private TextField libraryNameField;
    @FXML
    private Button addButton;
    @FXML
    private Text addBookMessage;

    public void handleAddBookAction()
    {
        try{
            BookService.addBook(titleField.getText(), authorField.getText(), genreField.getText(), libraryNameField.getText());
            addBookMessage.setText("Book added successfully!");
        } catch (EmptyTitleFieldException e1) {
            addBookMessage.setText(e1.getMessage());
        } catch (EmptyAuthorFieldException e2) {
            addBookMessage.setText(e2.getMessage());
        } catch (EmptyGenreFieldException e3) {
            addBookMessage.setText(e3.getMessage());
        } catch (EmptyLibraryNameFieldException e4) {
            addBookMessage.setText(e4.getMessage());
        }
    }
    public void goBackToLibrarianView(ActionEvent goBackToLibrarianView) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("LibrarianView.fxml"));
        Stage window = (Stage) ((Node) goBackToLibrarianView.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 600, 350));
        window.show();
    }

}
