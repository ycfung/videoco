package com.example.project.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.project.Model.CartItem;
import com.example.project.Model.Movie;
import com.example.project.Model.Order;
import com.example.project.Repo.CartItemConnector;
import com.example.project.Repo.MovieConnector;
import com.example.project.Repo.OrderConnector;
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

public class DialOperatorController {

    ObservableList<CartItem> cartItems = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField addressTextField;

    @FXML
    private TableView<CartItem> cartTableView;

    @FXML
    private TableColumn<CartItem, Integer> cartColQuantity;

    @FXML
    private TableColumn<CartItem, String> cartColTitle;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TableColumn<Movie, String> colActor;

    @FXML
    private TableColumn<Movie, String> colDate;

    @FXML
    private TableColumn<Movie, String> colDirector;

    @FXML
    private TableColumn<Movie, String> colGenre;

    @FXML
    private TableColumn<Movie, Integer> colMid;

    @FXML
    private TableColumn<Movie, Double> colPrice;

    @FXML
    private TableColumn<Movie, Integer> colQuantity;

    @FXML
    private TableColumn<Movie, String> colTitle;

    @FXML
    private Button mainMenuBtn;

    @FXML
    private TableView<Movie> movieTableView;

    @FXML
    private Button searchBtn;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button submitBtn;

    @FXML
    void submit(ActionEvent event) throws SQLException {
        ArrayList<CartItem> items = new ArrayList<>(cartItems);
        if (!addressTextField.getText().equals("")) {
            Double price = Global.calcPrice(items);
            Order order = new Order(Global.user.getUid(), price, "Unpaid",
                    new Date().toString(), "", addressTextField.getText());
            if (!addressTextField.getText().endsWith("Ontario"))
                order.setPrice(order.getPrice() + 9.99);
            Integer oid = OrderConnector.getInstance().insertOrder(order);
            order.setOid(oid);
            cartItems.forEach(i -> {
                try {
                    i.setOid(oid);
                    CartItemConnector.getInstance().insertCartItem(i);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            CustomDialog.show("Success", "Your order ID is " + oid + " price: $" + price, null);
            BackendSystem.getInstance().processOrder(order);
        }
    }

    @FXML
    void exit(ActionEvent event) {
        try {
            Global.user = null;
            Global.cartItems.clear();
            Stage stage = (Stage) mainMenuBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/login.fxml")));
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void search(ActionEvent event) {
        int choice = choiceBox.getSelectionModel().getSelectedIndex();
        if (searchTextField.getText().equals("")) {
            try {
                ObservableList<Movie> movies = FXCollections.observableArrayList();
                movies.setAll(MovieConnector.getInstance().getAllMovies());
                movieTableView.setItems(movies);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (choice == 0) {
            // by category
            try {
                ObservableList<Movie> movies = FXCollections.observableArrayList();
                movies.setAll(MovieConnector.getInstance().getByCategory(searchTextField.getText()));
                movieTableView.setItems(movies);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (choice == 1) {
            try {
                ObservableList<Movie> movies = FXCollections.observableArrayList();
                movies.setAll(MovieConnector.getInstance().getByName(searchTextField.getText()));
                movieTableView.setItems(movies);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void initialize() {

        ObservableList<String> choices = FXCollections.observableArrayList();
        choices.addAll("By category", "By name");
        choiceBox.setItems(choices);
        choiceBox.getSelectionModel().selectFirst();
        movieTableView.setEditable(false);
        colActor.setCellValueFactory(new PropertyValueFactory<>("actors"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDirector.setCellValueFactory(new PropertyValueFactory<>("director"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colMid.setCellValueFactory(new PropertyValueFactory<>("mid"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn add = new TableColumn("");
        add.setCellFactory(addBtnFactory);
        movieTableView.getColumns().add(add);

        TableColumn delete = new TableColumn("");
        delete.setCellFactory(deleteBtnFactory);
        cartTableView.getColumns().add(delete);

        cartColTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        cartColQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        cartItems = FXCollections.observableArrayList();
        cartItems.setAll(Global.cartItems);
        cartTableView.setItems(cartItems);
    }


    Callback<TableColumn<Movie, String>, TableCell<Movie, String>> addBtnFactory = new Callback<>() {
        @Override
        public TableCell call(final TableColumn<Movie, String> param) {
            return new TableCell<Movie, String>() {
                final Button btn = new Button("Add");

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        btn.setOnAction(event -> {
                            try {
                                System.out.println("Added to cart");
                                Integer mid = getTableView().getItems().get(this.getIndex()).getMid();
                                String title = getTableView().getItems().get(this.getIndex()).getTitle();
                                Global.cartItems.add(new CartItem(mid, title, 1));
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
