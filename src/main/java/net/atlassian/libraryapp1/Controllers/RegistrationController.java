package net.atlassian.libraryapp1.Controllers;


import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import net.atlassian.libraryapp1.Exceptions.*;
import net.atlassian.libraryapp1.Services.UserService;

public class RegistrationController {

    @FXML
    private Text registrationMessage;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private ChoiceBox role;

    @FXML
    public void initialize() {
        role.getItems().addAll("Customer", "Librarian");
    }

    @FXML
    public void handleRegisterAction() {
        try {
            UserService.addUser(usernameField.getText(), passwordField.getText(), (String) role.getValue(), nameField.getText(), emailField.getText(), phoneNumberField.getText());
            registrationMessage.setText("Account created successfully!");
        } catch (UsernameAlreadyExistsException e1) {
            registrationMessage.setText(e1.getMessage());
        } catch(InvalidEmailLibrarianException e2){
            registrationMessage.setText(e2.getMessage());
        } catch (InvalidEmailCustomerException e3) {
            registrationMessage.setText(e3.getMessage());
        } catch (EmptyUsernameFieldException e4) {
            registrationMessage.setText(e4.getMessage());
        } catch (EmptyPasswordFieldException e5) {
            registrationMessage.setText(e5.getMessage());
        } catch (EmptyNameFieldException e6) {
            registrationMessage.setText(e6.getMessage());
        } catch (EmptyEmailFieldException e7) {
            registrationMessage.setText(e7.getMessage());
        } catch (EmptyPhoneNumberFieldException e8) {
            registrationMessage.setText(e8.getMessage());
        }
    }
}