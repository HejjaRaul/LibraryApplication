package net.atlassian.libraryapp1.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.atlassian.libraryapp1.Exceptions.*;
import net.atlassian.libraryapp1.Services.BookService;
import net.atlassian.libraryapp1.Services.UserService;
import net.atlassian.libraryapp1.Model.*;
import javafx.scene.control.ListView;

public class DeleteBooksController {

    @FXML
    private TextField deleteBooksField;
    @FXML
    private Text deleteBookMessage;
    @FXML
    private ListView listOfBooks;

    @FXML
    public void handleDeleteBookAction() {
        try {
            BookService.deleteBook(deleteBooksField.getText());
            deleteBookMessage.setText("Book deleted successfully!");
            listOfBooks.getItems().clear();
            setTheListOfBooks();
        } catch (EmptyTitleFieldException e2) {
            deleteBookMessage.setText(e2.getMessage());
        } catch (WrongTitleException e1) {
            deleteBookMessage.setText(e1.getMessage());
        }

    }

    @FXML
    public void goBackToLibrarianView(ActionEvent goBackToLibrarianView) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("LibrarianView.fxml"));
        Stage window = (Stage) ((Node) goBackToLibrarianView.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 600, 420));
        window.show();
    }

    public void setTheListOfBooks() {

        String library = "";

        for (User user : UserService.userRepository.find()) {
            if (user.getUsername().equals(LoggedInLibrarian.getUsername())) {
                library = user.getName();
            }
        }

        for (Book book : BookService.bookRepository.find()) {
            if (library.equals(book.getLibraryName())) {
                listOfBooks.getItems().add(book.getName());
            }
        }


    }
}