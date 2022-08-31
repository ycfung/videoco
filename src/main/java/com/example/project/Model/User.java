package com.example.project.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private IntegerProperty uid;
    private StringProperty username;
    private StringProperty name;
    private StringProperty email;
    private StringProperty password;
    private StringProperty role;
    private IntegerProperty points;

    public User(String name, String email) {
        this.name = new SimpleStringProperty(name);
        this.username = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.role = new SimpleStringProperty("admin");
        this.password = new SimpleStringProperty("admin");
        this.points = new SimpleIntegerProperty(99999);
    }

    public User(String username, String name, String email, String password, String role, Integer points) {
        this.username = new SimpleStringProperty(username);
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
        this.role = new SimpleStringProperty(role);
        this.points = new SimpleIntegerProperty(points);
    }

    public User(Integer uid, String username, String name, String email, String password, String role, Integer points) {
        this.uid = new SimpleIntegerProperty(uid);
        this.username = new SimpleStringProperty(username);
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
        this.role = new SimpleStringProperty(role);
        this.points = new SimpleIntegerProperty(points);
    }


    public int getUid() {
        return uid.get();
    }

    public IntegerProperty uidProperty() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid.set(uid);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getRole() {
        return role.get();
    }

    public StringProperty roleProperty() {
        return role;
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public int getPoints() {
        return points.get();
    }

    public IntegerProperty pointsProperty() {
        return points;
    }

    public void setPoints(int points) {
        this.points.set(points);
    }


}
