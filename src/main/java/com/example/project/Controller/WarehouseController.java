package com.example.project.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.project.Model.Order;
import com.example.project.Model.User;
import com.example.project.Repo.OrderConnector;
import com.example.project.Repo.UserConnector;
import com.example.project.Utils.BackendSystem;
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
import javafx.stage.Stage;

public class WarehouseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Order, String> colAddress;

    @FXML
    private TableColumn<Order, Integer> colOrderID;

    @FXML
    private TableColumn<Order, Double> colPrice;

    @FXML
    private TableColumn<Order, String> colStatus;

    @FXML
    private TableColumn<Order, Integer> colWarehouseID;

    @FXML
    private Button mainMenuBtn;

    @FXML
    private Button shipBtn;

    @FXML
    private TableView<Order> tableView;

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
    void ship(ActionEvent event) throws SQLException {
        Order order = tableView.getSelectionModel().getSelectedItem();
        if (order == null) {
            CustomDialog.show("Error", "No selected order", null);
            return;
        } else {
            User user = UserConnector.getInstance().getUserByUid(order.getUid());
            user.setPoints(user.getPoints() + 1);
            UserConnector.getInstance().updateUser(user);
            order.setStatus("Delivered");
            OrderConnector.getInstance().updateOrder(order);
            ObservableList<Order> orders = FXCollections.observableArrayList();
            var result = OrderConnector.getInstance().getAllOrders();
            result.forEach(o -> o.setWarehouseID(BackendSystem.getStoreByAddress(order.getAddress())));
            result.removeIf(o -> !o.getStatus().equals("Transit"));
            orders.setAll(result);
            tableView.setItems(orders);

        }
    }

    @FXML
    void initialize() throws SQLException {
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colOrderID.setCellValueFactory(new PropertyValueFactory<>("oid"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colWarehouseID.setCellValueFactory(new PropertyValueFactory<>("warehouseID"));

        ObservableList<Order> orders = FXCollections.observableArrayList();
        var result = OrderConnector.getInstance().getAllOrders();
        result.forEach(order -> order.setWarehouseID(BackendSystem.getStoreByAddress(order.getAddress())));
        result.removeIf(order -> !order.getStatus().equals("Transit"));
        orders.setAll(result);
        tableView.setItems(orders);


    }

}
