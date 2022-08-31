package com.example.project;

import com.example.project.Repo.CartItemConnector;
import com.example.project.Repo.MovieConnector;
import com.example.project.Repo.OrderConnector;
import com.example.project.Repo.UserConnector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        CartItemConnector.getInstance();
        MovieConnector.getInstance();
        OrderConnector.getInstance();
        UserConnector.getInstance();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}