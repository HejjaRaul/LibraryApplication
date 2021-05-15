package net.atlassian.libraryapp1.Services;

import net.atlassian.libraryapp1.Exceptions.*;
import net.atlassian.libraryapp1.Model.User;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

class UserServiceTest {

    public static final String TEST = "test";

    @BeforeAll
    static void beforeAll() {

        System.out.println("Before Class");
    }

    @AfterAll
    static void afterAll() {

        System.out.println("After Class");
    }

    @BeforeEach
    void setUp() throws IOException {

        FileSystemService.APPLICATION_FOLDER = ".Test-LIB-Database";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
    }

    @AfterEach
    void tearDown() {

        UserService.closeDataBase();
    }

    @Test
    @DisplayName("Database is initialized and there are no users")
    void testDatabaseIsInitializedAndNoUserIsPersisted() {

        assertThat(UserService.getAllUsers()).isNotNull();
        assertThat(UserService.getAllUsers()).isEmpty();
    }

    @Test
    @DisplayName("User is successfully added to the database")
    void testUserIsAddedToDataBase() throws UsernameAlreadyExistsException, EmptyPasswordFieldException, EmptyPhoneNumberFieldException, EmptyUsernameFieldException, EmptyNameFieldException, InvalidEmailLibrarianException, InvalidEmailCustomerException, EmptyEmailFieldException {

        UserService.addUser(TEST, TEST, TEST, TEST, TEST, TEST);
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);

        User user = UserService.getAllUsers().get(0);
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo(TEST);
        assertThat(user.getPassword()).isEqualTo(UserService.encodePassword(TEST, TEST));
        assertThat(user.getRole()).isEqualTo(TEST);
        assertThat(user.getName()).isEqualTo(TEST);
        assertThat(user.getEmail()).isEqualTo(TEST);
        assertThat(user.getPhoneNumber()).isEqualTo(TEST);
    }

    @Test
    @DisplayName("User can not be added twice to the database")
    void testUserCanNotBeAddedTwice() {

        assertThrows(UsernameAlreadyExistsException.class, () -> {
            UserService.addUser(TEST, TEST, TEST, TEST, TEST, TEST);
            UserService.addUser(TEST, TEST, TEST, TEST, TEST, TEST);
        });
    }

    @Test
    @DisplayName("The customer email address should have the @yahoo.com or @gmail.com extension")
    void testWrongEmailAddressCustomer() {

        assertThrows(InvalidEmailCustomerException.class, () -> {
            UserService.addUser(TEST, TEST, "Customer", TEST, "abc@student.ro", TEST);
        });
    }

    @Test
    @DisplayName("The librarian email address should have the @library.com extension")
    void testWrongEmailAddressLibrarian() {

        assertThrows(InvalidEmailLibrarianException.class, () -> {
            UserService.addUser(TEST, TEST, "Librarian", TEST, "abc@yahoo.com", TEST);
        });
    }

    @Test
    @DisplayName("When you register the username filed can't be empty")
    void testTheUsernameFieldShouldNotBeEmpty() {
        assertThrows(EmptyUsernameFieldException.class, () -> {
            UserService.addUser("", TEST, TEST, TEST, TEST, TEST);
        });
    }

    @Test
    @DisplayName("When you register the password filed can't be empty")
    void testThePasswordFieldShouldNotBeEmpty() {
        assertThrows(EmptyPasswordFieldException.class, () -> {
            UserService.addUser(TEST, "", TEST, TEST, TEST, TEST);
        });
    }

    @Test
    @DisplayName("When you register the full name/library name filed can't be empty")
    void testTheNameFieldShouldNotBeEmpty() {
        assertThrows(EmptyNameFieldException.class, () -> {
            UserService.addUser(TEST, TEST, TEST, "", TEST, TEST);
        });
    }

    @Test
    @DisplayName("When you register the email filed can't be empty")
    void testTheEmailFieldShouldNotBeEmpty() {
        assertThrows(EmptyEmailFieldException.class, () -> {
            UserService.addUser(TEST, TEST, TEST, TEST, "", TEST);
        });
    }

    @Test
    @DisplayName("When you register the phone number filed can't be empty")
    void testThePhoneNumberFieldShouldNotBeEmpty() {
        assertThrows(EmptyPhoneNumberFieldException.class, () -> {
            UserService.addUser(TEST, TEST, TEST, TEST, TEST, "");
        });
    }

    @Test
    @DisplayName("Test a failed log in due to writing the wrong username")
    void testLoginFailedBecauseOfWrongUsername() {

        assertDoesNotThrow(() -> {
            UserService.addUser(TEST, TEST, TEST, TEST, TEST, TEST);
        });

        assertThrows(WrongUsernameException.class, () -> {
            UserService.checkUserCredentials("wrong", TEST, TEST);
        });
    }

    @Test
    @DisplayName("Test a failed log in due to writing the wrong password")
    void testLoginFailedBecauseOfWrongPassword() {

        assertDoesNotThrow(()->{
            UserService.addUser(TEST, TEST, TEST, TEST, TEST, TEST);
        });

        assertThrows(WrongPasswordException.class, ()->{
           UserService.checkUserCredentials(TEST, "wrong", TEST);
        });
    }

    @Test
    void testLoginFailedBecauseOfWrongRole() {

        assertDoesNotThrow(()->{
            UserService.addUser(TEST,TEST,TEST,TEST,TEST,TEST);
        });

        assertThrows(WrongRoleException.class, ()->{
           UserService.checkUserCredentials(TEST,TEST, "wrong");
        });
    }
}