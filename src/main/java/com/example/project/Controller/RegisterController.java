package com.example.project.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.project.Model.User;
import com.example.project.Repo.UserConnector;
import com.example.project.Utils.CustomDialog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitBtn;

    @FXML
    private TextField usernameTextField;

    @FXML
    void initialize() {

        submitBtn.setOnAction(action -> {
            try {
                // check not null
                User user = new User(usernameTextField.getText(), nameTextField.getText(), emailTextField.getText(),
                        passwordField.getText(), "customer", 0);
                int id = UserConnector.getInstance().addUser(user);
                if (id != -1) {
                    CustomDialog.show("Success", "Your UID is: " + id, null);
                    backToLoginPage();
                }else throw new SQLException();
            } catch (Exception e) {
                CustomDialog.show("Error", "Register failed, please check the information", null);
            }
        });

        cancelBtn.setOnAction(action -> {
            backToLoginPage();
        });
    }

    void backToLoginPage() {
        try {
            Stage stage = (Stage) submitBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/login.fxml")));
            stage.setScene(new Scene(root));
            stage.setTitle("Register");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
