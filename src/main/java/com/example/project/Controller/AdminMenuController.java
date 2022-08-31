package com.example.project.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.project.Utils.Global;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button manageCustomerBtn;

    @FXML
    private Button manageMoviesBtn;

    @FXML
    private Button manageOrderBtn;

    @FXML
    private Button warehouseBtn;

    @FXML
    void goToWarehouse(ActionEvent event) {
        try {
            Stage stage = (Stage) warehouseBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Warehouse.fxml")));
            stage.setScene(new Scene(root));
            stage.setTitle("Warehouse");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void logout(ActionEvent event) {
        try {
            Global.user = null;
            Global.cartItems.clear();
            Stage stage = (Stage) warehouseBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/login.fxml")));
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void manageCustomers(ActionEvent event) {
        try {
            Stage stage = (Stage) warehouseBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ManageCustomer.fxml")));
            stage.setScene(new Scene(root));
            stage.setTitle("Manage Customer");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void manageMovies(ActionEvent event) {
        try {
            Stage stage = (Stage) warehouseBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ManageMovie.fxml")));
            stage.setScene(new Scene(root));
            stage.setTitle("Mange Movie");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void manageOrders(ActionEvent event) {
        try {
            Stage stage = (Stage) warehouseBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ManageOrder.fxml")));
            stage.setScene(new Scene(root));
            stage.setTitle("Manage Order");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {

    }

}
