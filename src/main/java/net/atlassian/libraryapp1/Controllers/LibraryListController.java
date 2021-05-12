package net.atlassian.libraryapp1.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import net.atlassian.libraryapp1.Model.BooksOfLibrary;
import net.atlassian.libraryapp1.Model.User;
import net.atlassian.libraryapp1.Services.UserService;
import org.dizitart.no2.objects.ObjectRepository;

import java.util.Objects;

public class LibraryListController {

    public static ObjectRepository<User> userRepository;
    @FXML
    public ListView LibraryList;
    @FXML
    public TextField libraryName;

    public void Set() {
        for (User user : UserService.userRepository.find()) {
            if (Objects.equals(user.getRole(), "Librarian")) {
                LibraryList.getItems().add(user.getName());
            }
        }
    }

    public void handleShowBooksOfLibrary(ActionEvent showBooksOfLibrary) {

        BooksOfLibrary.setLibraryName(libraryName.getText());

    }
}
