package com.example.project.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.project.Model.CartItem;
import com.example.project.Model.Movie;
import com.example.project.Model.Order;
import com.example.project.Repo.CartItemConnector;
import com.example.project.Repo.MovieConnector;
import com.example.project.Repo.OrderConnector;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class OrderHistoryController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button cancelBtn;

    @FXML
    private TableColumn<CartItem, Integer> cartColQuantity;

    @FXML
    private TableColumn<CartItem, String> cartColTitle;

    @FXML
    private TableView<CartItem> cartTableView;

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
    void backToMainMenu(ActionEvent event) {
        try {
            Stage stage = (Stage) mainMenuBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/CustomerMenu.fxml")));
            stage.setScene(new Scene(root));
            stage.setTitle("Customer Menu");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() throws SQLException {
        ObservableList<Order> orders = FXCollections.observableArrayList();
        orders.setAll(OrderConnector.getInstance().getOrdersByUid(Global.user.getUid()));
        orderTableView.setItems(orders);

        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colOid.setCellValueFactory(new PropertyValueFactory<>("oid"));
        colPayment.setCellValueFactory(new PropertyValueFactory<>("payment"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colUid.setCellValueFactory(new PropertyValueFactory<>("uid"));

        cartColQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        cartColTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        orderTableView.setOnMouseClicked(action -> {
            if (orderTableView.getSelectionModel().getSelectedItem() != null) {
                // load cart items
                try {
                    Order order = orderTableView.getSelectionModel().getSelectedItem();
                    ObservableList<CartItem> cartItems = FXCollections.observableArrayList();
                    var a = CartItemConnector.getInstance().getCartItemsByOid(order.getOid());
                    cartItems.setAll(a);
                    cartTableView.setItems(cartItems);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        cancelBtn.setOnAction(action -> {
            Order order = orderTableView.getSelectionModel().getSelectedItem();
            if (order.getStatus().equals("Delivered"))
                CustomDialog.show("Error", "This order is delivered", null);
            else {

                try {
                    order.setStatus("Cancelled");
                    OrderConnector.getInstance().updateOrder(order);
                    // update stock
                    var cartItems = CartItemConnector.getInstance().getCartItemsByOid(order.getOid());
                    for (var item : cartItems) {
                        Movie movie = MovieConnector.getInstance().getMovieByID(item.getMid());
                        movie.setQuantity(movie.getQuantity() + item.getQuantity());
                    }
                    // refund points
                    if (order.getPayment().equals("Loyalty Points")) {
                        int pointsToRefund = 0;
                        for (var item : cartItems)
                            pointsToRefund += item.getQuantity() * 10;
                        Global.user.setPoints(Global.user.getPoints() + pointsToRefund);
                        UserConnector.getInstance().updateUser(Global.user);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
