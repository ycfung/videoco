package com.example.project.Model;

import javafx.beans.property.*;

public class Movie {

    private IntegerProperty mid;
    private StringProperty title;
    private StringProperty genre;
    private StringProperty director;
    private StringProperty actors;
    private IntegerProperty quantity;
    private StringProperty date;
    private DoubleProperty price;

    public Movie(String title, String genre, String director, String actors,
                 Integer quantity, String date, Double price) {
        this.title = new SimpleStringProperty(title);
        this.genre = new SimpleStringProperty(genre);
        this.director = new SimpleStringProperty(director);
        this.actors = new SimpleStringProperty(actors);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.date = new SimpleStringProperty(date);
        this.price = new SimpleDoubleProperty(price);
    }

    public Movie(Integer mid, String title, String genre, String director, String actors,
                 Integer quantity, String date, Double price) {
        this.mid = new SimpleIntegerProperty(mid);
        this.title = new SimpleStringProperty(title);
        this.genre = new SimpleStringProperty(genre);
        this.director = new SimpleStringProperty(director);
        this.actors = new SimpleStringProperty(actors);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.date = new SimpleStringProperty(date);
        this.price = new SimpleDoubleProperty(price);
    }


    public Integer getMid() {
        return mid.get();
    }

    public IntegerProperty midProperty() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid.set(mid);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getGenre() {
        return genre.get();
    }

    public StringProperty genreProperty() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    public String getDirector() {
        return director.get();
    }

    public StringProperty directorProperty() {
        return director;
    }

    public void setDirector(String director) {
        this.director.set(director);
    }

    public String getActors() {
        return actors.get();
    }

    public StringProperty actorsProperty() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors.set(actors);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

}
