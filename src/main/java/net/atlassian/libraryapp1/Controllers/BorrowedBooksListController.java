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
import net.atlassian.libraryapp1.Exceptions.CustomerDoesNotHaveTheBookBorrowedException;
import net.atlassian.libraryapp1.Exceptions.EmptyBookNameFieldException;
import net.atlassian.libraryapp1.Model.Book;
import net.atlassian.libraryapp1.Model.LoggedInCustomer;
import net.atlassian.libraryapp1.Services.BookService;
import net.atlassian.libraryapp1.Services.UserService;

import java.io.IOException;
import java.util.Objects;

public class BorrowedBooksListController {

    @FXML
    public TextField bookName;
    @FXML
    public Button returnBook;
    @FXML
    public ListView borrowedBooksList;
    @FXML
    public Text errorMessage;

    @FXML
    public void setBorrowedBooksList() {

        String aux;

        for (Book book : BookService.bookRepository.find()) {
            if (Objects.equals(LoggedInCustomer.getUsername(), book.getUserName()) && !Objects.equals(book.getBorrowedDate(), "")) {
                aux = book.getName() + ", library name: " + book.getLibraryName() + ", time left:" + BookService.getTimeLeft(book.getBorrowedDate()) + " days";
                borrowedBooksList.getItems().add(aux);
            }
        }
    }

    @FXML
    public void handleGoBackToCustomerView(ActionEvent goBackToCustomerView) throws IOException {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("CustomerView.fxml"));
        Stage window = (Stage) ((Node) goBackToCustomerView.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 600, 310));
        window.show();
    }

    @FXML
    public void handleReturnBook() {

        try {
            BookService.returnBook(bookName.getText(), LoggedInCustomer.getUsername());
            errorMessage.setText("Book ready to be returned");
            borrowedBooksList.getItems().clear();
            setBorrowedBooksList();
        } catch (EmptyBookNameFieldException e1) {
            errorMessage.setText(e1.getMessage());
        } catch (CustomerDoesNotHaveTheBookBorrowedException e2) {
            errorMessage.setText(e2.getMessage());
        }
    }
}
