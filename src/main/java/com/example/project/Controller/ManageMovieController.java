package com.example.project.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.project.Model.Movie;
import com.example.project.Repo.MovieConnector;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ManageMovieController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField actorTextField;

    @FXML
    private Button addBtn;

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
    private TextField dateTextField;

    @FXML
    private TextField directorTextField;

    @FXML
    private TextField genreTextField;

    @FXML
    private Button mainMenuBtn;

    @FXML
    private TableView<Movie> movieTableView;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField quantityTextField;

    @FXML
    private TextField titleTextField;

    @FXML
    void add(ActionEvent event) throws SQLException {
        Movie movie = new Movie(titleTextField.getText(), genreTextField.getText(), directorTextField.getText(),
                actorTextField.getText(), Integer.parseInt(quantityTextField.getText()), dateTextField.getText(),
                Double.parseDouble(priceTextField.getText()));
        MovieConnector.getInstance().addMovie(movie);
        CustomDialog.show("Success", "Movie is added", null);
    }

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
    void initialize() throws SQLException {
        movieTableView.setEditable(true);
        colActor.setCellValueFactory(new PropertyValueFactory<>("actors"));
        colActor.setCellFactory(TextFieldTableCell.<Movie>forTableColumn());
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDate.setCellFactory(TextFieldTableCell.<Movie>forTableColumn());
        colDirector.setCellValueFactory(new PropertyValueFactory<>("director"));
        colDirector.setCellFactory(TextFieldTableCell.<Movie>forTableColumn());
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colGenre.setCellFactory(TextFieldTableCell.<Movie>forTableColumn());
        colMid.setCellValueFactory(new PropertyValueFactory<>("mid"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colTitle.setCellFactory(TextFieldTableCell.<Movie>forTableColumn());


        TableColumn delete = new TableColumn("");
        delete.setCellFactory(deleteBtnFactory);
        movieTableView.getColumns().add(delete);
        ObservableList<Movie> movies = FXCollections.observableArrayList();
        movies.setAll(MovieConnector.getInstance().getAllMovies());
        movieTableView.setItems(movies);

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
                                Integer mid = getTableView().getItems().get(this.getIndex()).getMid();
                                System.out.println("Delete movie");
                                MovieConnector.getInstance().deleteMovie(mid);
                                ObservableList<Movie> movies = FXCollections.observableArrayList();
                                movies.setAll(MovieConnector.getInstance().getAllMovies());
                                movieTableView.setItems(movies);
                                CustomDialog.show("Success", "Movie " + mid + " is deleted", null);
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
