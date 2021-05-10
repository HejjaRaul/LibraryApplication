package net.atlassian.libraryapp1.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class LoginController {
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Button cancelButton;
    @FXML
    public void handleLoginAction()
    {

    }
    @FXML
    public void handleRegisterAction() throws Exception
    {
        Parent root= FXMLLoader.load(getClass().getClassLoader().getResource("Register.fxml"));
        Scene loginScene=new Scene(root);
        Stage window=new Stage();
        window.setScene(loginScene);
        hideLogin();
        window.show();

    }
    @FXML
    public void handleCancelAction()
    {
        System.exit(0);
    }
    public void hideLogin()
    {
        registerButton.getScene().getWindow().hide();
    }


}
