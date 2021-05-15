package net.atlassian.libraryapp1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.atlassian.libraryapp1.Services.BookService;
import net.atlassian.libraryapp1.Services.FileSystemService;
import net.atlassian.libraryapp1.Services.UserService;

import java.nio.file.Files;
import java.nio.file.Path;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        UserService.initDatabase();
        BookService.initDatabase();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
        primaryStage.setTitle("Library Application");
        primaryStage.setScene(new Scene(root, 586, 400));
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}

