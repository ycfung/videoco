package com.example.project.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.project.Model.Order;
import com.example.project.Repo.OrderConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class ManageOrderController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button deleteBtn;

    @FXML
    private TableColumn<Order, String> colAddress;

    @FXML
    private TableColumn<Order, Integer> colOid;

    @FXML
    private TableColumn<Order, String> colPayment;

    @FXML
    private TableColumn<Order, Double> colPrice;

    @FXML
    private TableColumn<Order, String> colStatus;

    @FXML
    private TableColumn<Order, String> colTime;

    @FXML
    private TableColumn<Order, Integer> colUid;


    @FXML
    private Button mainMenuBtn;

    @FXML
    private TableView<Order> orderTableView;

    @FXML
    void initialize() throws SQLException {
        orderTableView.setEditable(true);
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colAddress.setCellFactory(TextFieldTableCell.<Order>forTableColumn());
        colOid.setCellValueFactory(new PropertyValueFactory<>("oid"));

        colPayment.setCellValueFactory(new PropertyValueFactory<>("payment"));
        colPayment.setCellFactory(TextFieldTableCell.<Order>forTableColumn());
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        colStatus.setCellFactory(TextFieldTableCell.<Order>forTableColumn());
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colTime.setCellFactory(TextFieldTableCell.<Order>forTableColumn());
        colUid.setCellValueFactory(new PropertyValueFactory<>("uid"));

        ObservableList<Order> orders = FXCollections.observableArrayList();
        orders.setAll(OrderConnector.getInstance().getAllOrders());
        orderTableView.setItems(orders);

        mainMenuBtn.setOnAction(action -> {
            try {
                Stage stage = (Stage) mainMenuBtn.getScene().getWindow();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AdminMenu.fxml")));
                stage.setScene(new Scene(root));
                stage.setTitle("Admin Menu");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        deleteBtn.setOnAction(action -> {
            if (orderTableView.getSelectionModel().getSelectedItem() != null) {
                try {
                    Order order = orderTableView.getSelectionModel().getSelectedItem();
                    OrderConnector.getInstance().deleteOrder(order);

                    ObservableList<Order> temp = FXCollections.observableArrayList();
                    temp.setAll(OrderConnector.getInstance().getAllOrders());
                    orderTableView.setItems(temp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
