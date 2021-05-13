package net.atlassian.libraryapp1.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class BorrowedBooksListController {
    @FXML
    public TextField bookName;
    @FXML
    public Button returnBook;
    @FXML
    public ListView borrowedBooksList;

    public void handleGoBackToCustomerView(ActionEvent goBackToCustomerView) {

    }

    public void handleReturnBook(ActionEvent returnBook) {

    }
}
