package com.example.project.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import com.example.project.Model.CartItem;
import com.example.project.Model.Movie;
import com.example.project.Repo.MovieConnector;
import com.example.project.Utils.Global;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SearchMovieController {

    ObservableList<CartItem> cartItems = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    void initialize() throws SQLException {
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
        //colTitle.setCellFactory(TextFieldTableCell.<Movie>forTableColumn());

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

        searchBtn.setOnAction(action -> {
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
        });

        mainMenuBtn.setOnAction(action -> {
            try {
                Stage stage = (Stage) mainMenuBtn.getScene().getWindow();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/CustomerMenu.fxml")));
                stage.setScene(new Scene(root));
                stage.setTitle("Customer Menu");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


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



