package com.example.project.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.project.Model.User;
import com.example.project.Repo.UserConnector;
import com.example.project.Utils.CustomDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

public class ManageCustomerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<User, String> colEmail;

    @FXML
    private TableColumn<User, String> colName;

    @FXML
    private TableColumn<User, String> colPassword;

    @FXML
    private TableColumn<User, Integer> colPoints;

    @FXML
    private TableColumn<User, Integer> colUid;

    @FXML
    private TableColumn<User, String> colUsername;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button mainMenuBtn;

    @FXML
    private TableView<User> tableView;

    @FXML
    void backToMainMenu(ActionEvent event) {
        try {
            Stage stage = (Stage) mainMenuBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AdminMenu.fxml")));
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Menu");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void delete(ActionEvent event) throws SQLException {
        if (tableView.getSelectionModel().getSelectedItem() == null) {
            CustomDialog.show("Error", "No selected customer", null);
            return;
        } else {
            User user = tableView.getSelectionModel().getSelectedItem();
            UserConnector.getInstance().deleteUser(user);
            ObservableList<User> users = FXCollections.observableArrayList();
            users.addAll(UserConnector.getInstance().getAllCustomers());
            tableView.setItems(users);
            CustomDialog.show("Success", "Deleted user: " + user.getName(), null);
        }
    }

    @FXML
    void initialize() throws SQLException {
        tableView.setEditable(true);
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colPassword.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        colPoints.setCellValueFactory(new PropertyValueFactory<>("points"));

        colUid.setCellValueFactory(new PropertyValueFactory<>("uid"));

        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colUsername.setCellFactory(TextFieldTableCell.<User>forTableColumn());


        ObservableList<User> users = FXCollections.observableArrayList();
        users.addAll(UserConnector.getInstance().getAllCustomers());
        tableView.setItems(users);
    }

}
