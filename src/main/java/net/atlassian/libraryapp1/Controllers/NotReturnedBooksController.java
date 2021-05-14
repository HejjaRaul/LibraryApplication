package net.atlassian.libraryapp1.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.atlassian.libraryapp1.Model.Book;
import net.atlassian.libraryapp1.Model.LoggedInLibrarian;
import net.atlassian.libraryapp1.Model.User;
import net.atlassian.libraryapp1.Services.BookService;
import net.atlassian.libraryapp1.Services.UserService;
import javafx.scene.control.ListView;

public class NotReturnedBooksController {

    @FXML
    private ListView notReturnedBooksList;
    @FXML
    private ListView peopleWhoBorrowedBooksList;
    @FXML
    private Text notReturnedBookMessage;
    public void notReturnedBooks()  {
        String library = "";
        notReturnedBookMessage.setText("");
        int sw=0;
        for (User user : UserService.userRepository.find()) {
            if (user.getUsername().equals(LoggedInLibrarian.getUsername())) {
                library = user.getName();
            }
        }

        for (Book book : BookService.bookRepository.find()) {
            if (library.equals(book.getLibraryName()) && !book.getBorrowedDate().equals("")) {
                if(BookService.getTimeLeft(book.getBorrowedDate())<0) {
                    notReturnedBooksList.getItems().add(book.getName());
                    peopleWhoBorrowedBooksList.getItems().add(book.getUserName());
                    sw = 1;
                }
            }
        }
        if(sw==0) {
            notReturnedBookMessage.setText("All the books were returned on time");
        }
}
    public void goBackToLibrarianView(ActionEvent goBackToLibrarianView) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("LibrarianView.fxml"));
        Stage window = (Stage) ((Node) goBackToLibrarianView.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 600, 420));
        window.show();
    }
}
