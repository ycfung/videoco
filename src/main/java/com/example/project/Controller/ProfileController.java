package com.example.project.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.project.Repo.UserConnector;
import com.example.project.Utils.CustomDialog;
import com.example.project.Utils.Global;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProfileController {

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
    void cancel(ActionEvent event) {
        backToCustomerMenu();
    }

    @FXML
    void submit(ActionEvent event) throws SQLException {
        Global.user.setEmail(emailTextField.getText());
        Global.user.setName(nameTextField.getText());
        Global.user.setUsername(usernameTextField.getText());
        Global.user.setPassword(passwordField.getText());
        boolean flag = UserConnector.getInstance().updateUser(Global.user);
        if (flag) {
            CustomDialog.show("Success", "Profile Updated", null);
            backToCustomerMenu();
        } else CustomDialog.show("Failed", "Please check your input", null);
    }

    @FXML
    void initialize() {

        emailTextField.setText(Global.user.getEmail());
        nameTextField.setText(Global.user.getName());
        usernameTextField.setText(Global.user.getUsername());
        passwordField.setText(Global.user.getPassword());

    }

    void backToCustomerMenu() {
        try {
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/CustomerMenu.fxml")));
            stage.setScene(new Scene(root));
            stage.setTitle("Customer Menu");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
