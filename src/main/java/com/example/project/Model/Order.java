package com.example.project.Model;

import javafx.beans.property.*;

public class Order {
    private IntegerProperty oid=new SimpleIntegerProperty();
    private IntegerProperty uid;
    private DoubleProperty price;
    private StringProperty status;
    private StringProperty time;
    private StringProperty payment;
    private StringProperty address;
    private IntegerProperty warehouseID = new SimpleIntegerProperty();


    public Order(Integer oid, Integer uid, Double price, String status, String time, String payment, String address) {
        this.oid = new SimpleIntegerProperty(oid);
        this.uid = new SimpleIntegerProperty(uid);
        this.price = new SimpleDoubleProperty(price);
        this.status = new SimpleStringProperty(status);
        this.time = new SimpleStringProperty(time);
        this.payment = new SimpleStringProperty(payment);
        this.address = new SimpleStringProperty(address);
    }

    public Order(Integer uid, Double price, String status, String time, String payment, String address) {
        this.uid = new SimpleIntegerProperty(uid);
        this.price = new SimpleDoubleProperty(price);
        this.status = new SimpleStringProperty(status);
        this.time = new SimpleStringProperty(time);
        this.payment = new SimpleStringProperty(payment);
        this.address = new SimpleStringProperty(address);
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

    public int getUid() {
        return uid.get();
    }

    public IntegerProperty uidProperty() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid.set(uid);
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

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getPayment() {
        return payment.get();
    }

    public StringProperty paymentProperty() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment.set(payment);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }


    public int getWarehouseID() {
        return warehouseID.get();
    }

    public IntegerProperty warehouseIDProperty() {
        return warehouseID;
    }

    public void setWarehouseID(int warehouseID) {
        this.warehouseID.set(warehouseID);
    }


}
