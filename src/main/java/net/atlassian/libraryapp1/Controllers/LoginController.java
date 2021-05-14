package net.atlassian.libraryapp1.Controllers;

import net.atlassian.libraryapp1.Model.LoggedInCustomer;
import net.atlassian.libraryapp1.Model.LoggedInLibrarian;
import net.atlassian.libraryapp1.Model.User;
import net.atlassian.libraryapp1.Services.UserService;
import net.atlassian.libraryapp1.Exceptions.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    @FXML
    PasswordField passwordField;
    @FXML
    TextField usernameField;
    @FXML
    Button loginButton;
    @FXML
    Button registerButton;
    @FXML
    Button cancelButton;
    @FXML
    private Text loginMessage;
    @FXML
    private ChoiceBox role;

    private String auxRole;

    @FXML
    public void initialize() {
        role.getItems().addAll("Customer", "Librarian");
    }

    @FXML
    public void handleLoginAction(ActionEvent login) throws Exception {
        try {
            UserService.checkUserCredentials(usernameField.getText(), passwordField.getText(), (String) role.getValue());
            auxRole = (String) role.getValue();
            if (auxRole.compareTo("Customer") == 0) {
                LoggedInCustomer.setUsername(usernameField.getText());
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("CustomerView.fxml"));
                Stage window = (Stage) ((Node) login.getSource()).getScene().getWindow();
                window.setScene(new Scene(root, 600, 310));
                window.show();
            } else {
                LoggedInLibrarian.setUsername(usernameField.getText());
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("LibrarianView.fxml"));
                Stage window = (Stage) ((Node) login.getSource()).getScene().getWindow();
                window.setScene(new Scene(root, 600, 420));
                window.show();
            }

        } catch (WrongUsernameException e1) {
            loginMessage.setText(e1.getMessage());
        } catch (WrongPasswordException e2) {
            loginMessage.setText(e2.getMessage());
        } catch (WrongRoleException e3) {
            loginMessage.setText(e3.getMessage());
        } catch (EmptyUsernameFieldException e4) {
            loginMessage.setText(e4.getMessage());
        } catch (EmptyPasswordFieldException e5) {
            loginMessage.setText(e5.getMessage());
        }
    }

    @FXML
    public void handleRegisterAction(ActionEvent goToRegister) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Register.fxml"));
        Stage window = (Stage) ((Node) goToRegister.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 600, 350));
        window.show();

    }

    @FXML
    public void handleCancelAction() {
        System.exit(0);
    }
}
