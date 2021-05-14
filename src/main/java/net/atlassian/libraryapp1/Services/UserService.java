package net.atlassian.libraryapp1.Services;

import net.atlassian.libraryapp1.Exceptions.*;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import net.atlassian.libraryapp1.Model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static net.atlassian.libraryapp1.Services.FileSystemService.getPathToFile;

public class UserService {

    public static ObjectRepository<User> userRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("UserDataBase.db").toFile())
                .openOrCreate("test", "test");

        userRepository = database.getRepository(User.class);
    }

    public static void addUser(String username, String password, String role, String name, String email, String phoneNumber) throws UsernameAlreadyExistsException, InvalidEmailLibrarianException, InvalidEmailCustomerException, EmptyUsernameFieldException, EmptyPasswordFieldException, EmptyNameFieldException, EmptyEmailFieldException, EmptyPhoneNumberFieldException {

        checkEmptyFields(username, password, name, email, phoneNumber);
        checkEmailAddress(role, email);
        checkUserDoesNotAlreadyExist(username);

        userRepository.insert(new User(username, encodePassword(username, password), role, name, email, phoneNumber));
    }

    private static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {

        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername())) {
                throw new UsernameAlreadyExistsException(username);
            }
        }
    }

    private static void checkEmailAddress(String role, String email) throws InvalidEmailLibrarianException, InvalidEmailCustomerException {

        String extensionLibrarian = "@library.com";
        String extensionCustomer1 = "@yahoo.com";
        String extensionCustomer2 = "@gmail.com";
        int i, j, k, sw = 0, sw1 = 0, sw2 = 0;

        if (Objects.equals(role, "Librarian")) {
            for (i = 0; i < email.length(); i++) {
                if (email.charAt(i) == '@') {
                    k = 0;
                    for (j = i; j < email.length(); j++) {
                        if (extensionLibrarian.charAt(k) != email.charAt(j)) {
                            sw = 1;
                            break;
                        }
                        k++;
                    }
                    break;
                }
            }

            if (sw == 1) {
                throw new InvalidEmailLibrarianException();
            }
        } else {
            for (i = 0; i < email.length(); i++) {
                if (email.charAt(i) == '@') {
                    k = 0;
                    for (j = i; j < email.length(); j++) {
                        if (extensionCustomer1.charAt(k) != email.charAt(j)) {
                            sw1 = 1;
                            break;
                        }
                        k++;
                    }
                    break;
                }
            }

            for (i = 0; i < email.length(); i++) {
                if (email.charAt(i) == '@') {
                    k = 0;
                    for (j = i; j < email.length(); j++) {
                        if (extensionCustomer2.charAt(k) != email.charAt(j)) {
                            sw2 = 1;
                            break;
                        }
                        k++;
                    }
                    break;
                }
            }

            if (sw1 == 1 && sw2 == 1) {
                throw new InvalidEmailCustomerException();
            }
        }
    }

    private static void checkEmptyFields(String username, String password, String name, String email, String phone) throws EmptyUsernameFieldException, EmptyPasswordFieldException, EmptyNameFieldException, EmptyEmailFieldException, EmptyPhoneNumberFieldException {

        if (username == "") {
            throw new EmptyUsernameFieldException();
        } else {
            if (password == "") {
                throw new EmptyPasswordFieldException();
            } else {
                if (name == "") {
                    throw new EmptyNameFieldException();
                } else {
                    if (email == "") {
                        throw new EmptyEmailFieldException();
                    } else {
                        if (phone == "") {
                            throw new EmptyPhoneNumberFieldException();
                        }
                    }
                }
            }
        }
    }

    private static void checkEmptyFieldsLogIn(String username, String password) throws EmptyUsernameFieldException, EmptyPasswordFieldException {

        if (username == "") {
            throw new EmptyUsernameFieldException();
        } else if (password == "") {
            throw new EmptyPasswordFieldException();
        }
    }

    public static void checkUserCredentials(String username, String password, String role) throws EmptyUsernameFieldException, EmptyPasswordFieldException, WrongUsernameException, WrongPasswordException, WrongRoleException {

        int sw1 = 1, sw2 = 1, sw3 = 1;
        String encryptedPassword = UserService.encodePassword(username, password);

        checkEmptyFieldsLogIn(username, password);

        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername())) {
                sw1 = 0;
                if (Objects.equals(encryptedPassword, user.getPassword())) {
                    sw2 = 0;
                    if (Objects.equals(role, user.getRole())) {
                        sw3 = 0;
                    }
                }
            }
        }

        if (sw1 == 1) {
            throw new WrongUsernameException();
        }
        if (sw2 == 1) {
            throw new WrongPasswordException();
        }
        if (sw3 == 1) {
            throw new WrongRoleException();
        }
    }

    private static String encodePassword(String salt, String password) {

        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }

    private static MessageDigest getMessageDigest() {

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }

}
