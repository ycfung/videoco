package com.example.project.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.project.Model.Movie;
import com.example.project.Model.User;
import com.example.project.Repo.MovieConnector;
import com.example.project.Repo.UserConnector;
import com.example.project.Utils.CustomDialog;
import com.example.project.Utils.Global;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SuperUserController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addBtn;

    @FXML
    private TableColumn<User, String> colEmail;

    @FXML
    private TableColumn<User, String> colName;

    @FXML
    private TextField emailTextField;

    @FXML
    private Button logoutBtn;

    @FXML
    private TextField nameTextField;

    @FXML
    private TableView<User> tableView;

    @FXML
    void addAdmin(ActionEvent event) throws SQLException {
        // better check null
        User admin = new User(nameTextField.getText(), emailTextField.getText());
        UserConnector.getInstance().addUser(admin);
        CustomDialog.show("Success", "Added admin " + admin.getName(), null);
        nameTextField.clear();
        emailTextField.clear();
        ObservableList<User> admins = FXCollections.observableArrayList();
        admins.addAll(UserConnector.getInstance().getAllAdmins());
        tableView.setItems(admins);
    }

    @FXML
    void logout(ActionEvent event) {
        try {
            Global.user = null;
            Global.cartItems.clear();
            Stage stage = (Stage) logoutBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/login.fxml")));
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() throws SQLException {
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));


        ObservableList<User> admins = FXCollections.observableArrayList();
        admins.addAll(UserConnector.getInstance().getAllAdmins());
        tableView.setItems(admins);
        // admin
        TableColumn delete = new TableColumn("");
        delete.setCellFactory(deleteBtnFactory);
        tableView.getColumns().add(delete);


    }


    Callback<TableColumn<User, String>, TableCell<User, String>> deleteBtnFactory = new Callback<>() {
        @Override
        public TableCell call(final TableColumn<User, String> param) {
            return new TableCell<User, String>() {
                final Button btn = new Button("Delete");

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        btn.setOnAction(event -> {
                            try {
                                Integer uid = getTableView().getItems().get(this.getIndex()).getUid();
                                User user = UserConnector.getInstance().getUserByUid(uid);
                                UserConnector.getInstance().deleteUser(user);
                                ObservableList<User> admins = FXCollections.observableArrayList();
                                admins.setAll(UserConnector.getInstance().getAllAdmins());
                                tableView.setItems(admins);
                                CustomDialog.show("Success", "Admin " + user.getName() + " is deleted", null);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                        setGraphic(btn);
                    }
                }
            };
        }
    };

}
