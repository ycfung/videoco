package com.example.project.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CartItem {


    private IntegerProperty oid = new SimpleIntegerProperty();
    private IntegerProperty mid;
    private StringProperty title;
    private IntegerProperty quantity;

    public CartItem(Integer mid, String title, Integer quantity) {
        this.mid = new SimpleIntegerProperty(mid);
        this.title = new SimpleStringProperty(title);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    public int getMid() {
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

    public int getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public int getOid() {
        return oid.get();
    }

    public IntegerProperty oidProperty() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid.set(oid);
    }

}
