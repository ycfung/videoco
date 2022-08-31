package com.example.project.Controller;


import com.example.project.Model.CartItem;
import com.example.project.Model.Movie;
import com.example.project.Model.Order;
import com.example.project.Repo.CartItemConnector;
import com.example.project.Repo.MovieConnector;
import com.example.project.Repo.OrderConnector;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class OperatorViewController {

    @FXML
    private Button cancelBtn;

    @FXML
    private TableColumn<CartItem, String> cartColTitle;

    @FXML
    private TableColumn<CartItem, Integer> cartItemColQuantity;

    @FXML
    private TableView<CartItem> cartItemTableView;

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
    private Button logoutBtn;

    @FXML
    private TableView<Order> orderTableView;

    @FXML
    private Button searchBtn;

    @FXML
    private TextField textField;

    @FXML
    void cancel(ActionEvent event) throws SQLException {
        if (!Global.order.getStatus().equals("Delivered")) {
            // can be cancelled, update stock
            var cartItems = CartItemConnector.getInstance().getCartItemsByOid(Global.order.getOid());
            for (var item : cartItems) {
                Movie movie = MovieConnector.getInstance().getMovieByID(item.getMid());
                movie.setQuantity(movie.getQuantity() + item.getQuantity());
            }
            CustomDialog.show("Success", "The order ID: " + Global.order.getOid() + " is cancelled", null);
        }
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
    void search() throws SQLException {
        Integer oid = Integer.parseInt(textField.getText());
        ObservableList<Order> orders = FXCollections.observableArrayList();
        Global.order = OrderConnector.getInstance().getOrderByOid(oid);
        orders.setAll(Global.order);
        orderTableView.setItems(orders);

        ObservableList<CartItem> cartItems = FXCollections.observableArrayList();
        cartItems.addAll(CartItemConnector.getInstance().getCartItemsByOid(oid));
        cartItemTableView.setItems(cartItems);

    }

    @FXML
    void initialize() throws SQLException {
        orderTableView.setEditable(false);
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

        cartItemColQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        cartColTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        cartItemTableView.setEditable(false);
    }
}
