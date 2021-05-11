package net.atlassian.libraryapp1.Controllers;

import javafx.scene.control.ListView;
import net.atlassian.libraryapp1.Model.User;
import net.atlassian.libraryapp1.Services.UserService;
import org.dizitart.no2.objects.ObjectRepository;

import java.util.Objects;

public class LibraryListController {

    public static ObjectRepository<User> userRepository;
    public ListView LibraryList;

    public void Set() {
        for (User user : UserService.userRepository.find()) {
            if (Objects.equals(user.getRole(), "Librarian")) {
                LibraryList.getItems().add(user.getName());
            }
        }
    }

}
