package com.example.project.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.example.project.Model.Order;
import com.example.project.Model.User;
import com.example.project.Repo.OrderConnector;
import com.example.project.Repo.UserConnector;
import com.example.project.Utils.CustomDialog;
import com.example.project.Utils.Global;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button callOperatorBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button registerBtn;

    @FXML
    private TextField usernameTextField;

    @FXML
    void initialize() {

        registerBtn.setOnAction(action -> {
            try {
                Stage stage = (Stage) registerBtn.getScene().getWindow();
                Parent root = null;
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/register.fxml")));
                stage.setScene(new Scene(root));
                stage.setTitle("Register");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        loginBtn.setOnAction(action -> {
            System.out.println("pressed login");
            if (usernameTextField.getText().equals("") || passwordTextField.getText().equals(""))
                CustomDialog.show("Error", "Complete username and password", null);
            else {
                try {
                    User user = UserConnector.getInstance().getUserByUsername(usernameTextField.getText());
                    // success login
                    if (passwordTextField.getText().equals(user.getPassword())) {
                        switch (user.getRole()) {
                            case "customer":
                                System.out.println("This is customer");
                                Global.user = user;
                                CustomDialog.show("Success", "Login success", null);
                                // check late order
                                try {
                                    ArrayList<Order> orders = OrderConnector.getInstance().getByUidAndStatus(Global.user.getUid(), "Delivered");
                                    boolean late = false;
                                    int charge = 0;
                                    Calendar calendar = new GregorianCalendar();
                                    Date current = new Date();
                                    for (Order order : orders) {
                                        Date orderDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(order.getTime());
                                        if (order.getTime() == null)
                                            continue;
                                        calendar.setTime(orderDate);
                                        calendar.add(Calendar.DATE, 14);
                                        orderDate = calendar.getTime();
                                        if (orderDate.compareTo(current) < 0) {
                                            charge += getdays(orderDate, current);
                                            late = true;
                                        }
                                    }
                                    if (late)
                                        CustomDialog.show("Info", "You have late orders. You've been charged $" + charge + " CAD", null);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Stage stage = (Stage) loginBtn.getScene().getWindow();
                                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/CustomerMenu.fxml")));
                                    stage.setScene(new Scene(root));
                                    stage.setTitle("Customer Menu");
                                    stage.show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case "admin":
                                System.out.println("This is admin");
                                Global.user = user;
                                CustomDialog.show("Success", "Admin login success", null);
                                try {
                                    Stage stage = (Stage) loginBtn.getScene().getWindow();
                                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AdminMenu.fxml")));
                                    stage.setScene(new Scene(root));
                                    stage.setTitle("Admin Menu");
                                    stage.show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case "superuser":
                                System.out.println("This is superuser");
                                Global.user = user;
                                CustomDialog.show("Success", "Superuser login success", null);
                                try {
                                    Stage stage = (Stage) loginBtn.getScene().getWindow();
                                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/SuperuserView.fxml")));
                                    stage.setScene(new Scene(root));
                                    stage.setTitle("Superuser Menu");
                                    stage.show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case "operator":
                                System.out.println("This is operator");
                                Global.user = user;
                                CustomDialog.show("Success", "Operator login success", null);
                                try {
                                    Stage stage = (Stage) loginBtn.getScene().getWindow();
                                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/OperatorView.fxml")));
                                    stage.setScene(new Scene(root));
                                    stage.setTitle("Operator View");
                                    stage.show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                        }
                    } else
                        CustomDialog.show("Error", "Wrong password", "");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        callOperatorBtn.setOnAction(action -> {
            try {
                Global.user = UserConnector.getInstance().getUserByUsername("anonymous");
                Stage stage = (Stage) registerBtn.getScene().getWindow();
                Parent root = null;
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/DialOperatorView.fxml")));
                stage.setScene(new Scene(root));
                stage.setTitle("Dial Operator");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
    public static int getdays(Date dateStart, Date dateEnd) {
        return (int) ((dateEnd.getTime() - dateStart.getTime()) / 1000 / 60 / 60 / 24);
    }
}

