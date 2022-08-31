package com.example.project.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.project.Model.CartItem;
import com.example.project.Model.Movie;
import com.example.project.Model.Order;
import com.example.project.Repo.CartItemConnector;
import com.example.project.Repo.OrderConnector;
import com.example.project.Repo.UserConnector;
import com.example.project.Utils.BackendSystem;
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

public class CartController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField addressTextField;

    @FXML
    private TableColumn<CartItem, Integer> cartColQuantity;

    @FXML
    private TableColumn<CartItem, String> cartColTitle;

    @FXML
    private TableView<CartItem> cartTableView;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private Button creditCardBtn;

    @FXML
    private Button mainMenuBtn;

    @FXML
    private Button pickupBtn;

    @FXML
    private Button pointsBtn;

    @FXML
    private TextField totalPriceTextField;

    ObservableList<CartItem> cartItems;

    @FXML
    void payWithCard(ActionEvent event) throws SQLException {
        Order order = new Order(Global.user.getUid(), Global.calcPrice(Global.cartItems), "Paid",
                new Date().toString(), "Card", addressTextField.getText());
        // outside Ontario charge $9.99
        if (!addressTextField.getText().endsWith("Ontario"))
            order.setPrice(order.getPrice() + 9.99);
        // insert order and items
        Integer oid = OrderConnector.getInstance().insertOrder(order);
        order.setOid(oid);
        Global.cartItems.forEach(i -> {
            try {
                i.setOid(oid);
                CartItemConnector.getInstance().insertCartItem(i);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        CustomDialog.show("Success", "Successfully paid with card, your order id is: " + oid, null);
        BackendSystem.getInstance().processOrder(order);
        Global.cartItems.clear();
        Global.order = null;
        backToMainMenu();
    }

    @FXML
    void payWithPoints(ActionEvent event) throws SQLException {
        System.out.println("Pay with points");
        if (Global.user.getPoints() >= 10) {
            Global.user.setPoints(Global.user.getPoints() - 10);
            UserConnector.getInstance().updateUser(Global.user);
            Order order = new Order(Global.user.getUid(), Global.calcPrice(Global.cartItems), "Paid",
                    new Date().toString(), "Loyalty Points", addressTextField.getText());
            if (!addressTextField.getText().endsWith("Ontario"))
                order.setPrice(order.getPrice() + 9.99);
            Integer oid = OrderConnector.getInstance().insertOrder(order);
            order.setOid(oid);
            Global.cartItems.forEach(i -> {
                try {
                    i.setOid(oid);
                    CartItemConnector.getInstance().insertCartItem(i);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            CustomDialog.show("Success", "Successfully paid with loyalty points, your order id is: " + oid, null);
            BackendSystem.getInstance().processOrder(order);
            Global.cartItems.clear();
            Global.order = null;
            backToMainMenu();
        } else
            CustomDialog.show("Error", "No enough loyalty points", null);

    }

    @FXML
    void pickup(ActionEvent event) throws SQLException {
        String addr = choiceBox.getSelectionModel().getSelectedItem();
        Order order = new Order(Global.user.getUid(), Global.calcPrice(Global.cartItems), "Paid",
                new Date().toString(), "Pickup", addr);
        // insert order and items
        Integer oid = OrderConnector.getInstance().insertOrder(order);
        order.setOid(oid);
        Global.cartItems.forEach(i -> {
            try {
                i.setOid(oid);
                CartItemConnector.getInstance().insertCartItem(i);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        CustomDialog.show("Success", "Successfully placed order, please pickup at " + addr + ", your order id is: " + oid, null);
        Global.cartItems.clear();
        Global.order = null;
        backToMainMenu();
    }

    @FXML
    void backToMainMenu() {
        try {
            Stage stage = (Stage) mainMenuBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/CustomerMenu.fxml")));
            stage.setScene(new Scene(root));
            stage.setTitle("Register");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() throws SQLException {

        cartColTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        cartColQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        cartItems = FXCollections.observableArrayList();
        cartItems.setAll(Global.cartItems);
        cartTableView.setItems(cartItems);

        TableColumn delete = new TableColumn("");
        delete.setCellFactory(deleteBtnFactory);
        cartTableView.getColumns().add(delete);


        if (Global.cartItems.size() != 0)
            totalPriceTextField.setText(String.valueOf(Global.calcPrice(Global.cartItems)));

        ObservableList<String> choices = FXCollections.observableArrayList();
        choices.addAll("Store 1", "Store 2");
        choiceBox.setItems(choices);

        choiceBox.setOnAction(action -> {
            addressTextField.setText(choiceBox.getSelectionModel().getSelectedItem());
        });


    }

    Callback<TableColumn<Movie, String>, TableCell<Movie, String>> deleteBtnFactory = new Callback<>() {
        @Override
        public TableCell call(final TableColumn<Movie, String> param) {
            return new TableCell<Movie, String>() {
                final Button btn = new Button("Delete");

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        btn.setOnAction(event -> {
                            try {
                                System.out.println("Delete from cart");
                                Global.cartItems.remove(this.getIndex());
                                cartItems.setAll(Global.cartItems);
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
