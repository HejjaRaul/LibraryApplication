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
import net.atlassian.libraryapp1.Exceptions.EmptyBookNameFieldException;
import net.atlassian.libraryapp1.Exceptions.WrongBookNameException;
import net.atlassian.libraryapp1.Model.*;
import net.atlassian.libraryapp1.Services.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class SeeTheBooksThatNeedToBeReturnedTodayController {

    @FXML
    public Text returnTodayBookMessage;
    @FXML
    public TextField bookName;
    @FXML
    public Button bookReturned;
    @FXML
    private ListView todayBooksList;


    public void returnToday() {

        String library = "";

        for (User user : UserService.userRepository.find()) {
            if (user.getUsername().equals(LoggedInLibrarian.getUsername())) {
                library = user.getName();
            }
        }

        for (Book book : BookService.bookRepository.find()) {
            if (library.equals(book.getLibraryName())) {
                if (book.getReturnedDate().equals(getCurrentDate()))
                    todayBooksList.getItems().add(book.getName());
            }
        }
    }

    private String getCurrentDate() {
        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime today = LocalDateTime.now();
        return date.format(today);
    }

    @FXML
    public void goBackToLibrarianView(ActionEvent goBackToLibrarianView) throws Exception {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("LibrarianView.fxml"));
        Stage window = (Stage) ((Node) goBackToLibrarianView.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 600, 420));
        window.setTitle("");
        window.show();
    }

    @FXML
    public void handleTheBookHasBeenReturned(ActionEvent theBookHasBeenReturned) {

        try {
            BookService.theBookHasBeenReturned(bookName.getText());
            returnTodayBookMessage.setText("The book has been returned successfully");
            todayBooksList.getItems().clear();
            returnToday();
        } catch (EmptyBookNameFieldException e1) {
            returnTodayBookMessage.setText(e1.getMessage());
        } catch (WrongBookNameException e2) {
            returnTodayBookMessage.setText(e2.getMessage());
        }

    }
}
