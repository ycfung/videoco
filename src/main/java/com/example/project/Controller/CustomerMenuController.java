package com.example.project.Controller;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import com.example.project.Model.Order;
import com.example.project.Repo.OrderConnector;
import com.example.project.Utils.CustomDialog;
import com.example.project.Utils.Global;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CustomerMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button editProfileBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button searchBtn;

    @FXML
    private Button viewCartBtn;

    @FXML
    private Text msg;

    @FXML
    void goToOrderHistory() {
        try {
            Stage stage = (Stage) searchBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/OrderHistory.fxml")));
            stage.setScene(new Scene(root));
            stage.setTitle("Order History");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button historyBtn;

    @FXML
    void initialize() {
        msg.setText("Welcome back, " + Global.user.getName() + ". Your loyalty score is " + Global.user.getPoints());


        searchBtn.setOnAction(action -> {
            try {
                Stage stage = (Stage) searchBtn.getScene().getWindow();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/SearchMovieView.fxml")));
                stage.setScene(new Scene(root));
                stage.setTitle("Search movie");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        viewCartBtn.setOnAction(action -> {
            try {
                Stage stage = (Stage) searchBtn.getScene().getWindow();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/CartView.fxml")));
                stage.setScene(new Scene(root));
                stage.setTitle("Cart");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        logoutBtn.setOnAction(action -> {
            try {
                Stage stage = (Stage) searchBtn.getScene().getWindow();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/login.fxml")));
                stage.setScene(new Scene(root));
                stage.setTitle("Login");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        editProfileBtn.setOnAction(action -> {
            try {
                Stage stage = (Stage) searchBtn.getScene().getWindow();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/EditProfile.fxml")));
                stage.setScene(new Scene(root));
                stage.setTitle("Edit Profile");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


}
