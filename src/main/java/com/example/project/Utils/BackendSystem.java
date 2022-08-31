package com.example.project.Utils;

import com.example.project.Model.Order;
import com.example.project.Model.Warehouse;
import com.example.project.Repo.OrderConnector;

import java.sql.SQLException;
import java.util.ArrayList;

public class BackendSystem {

    private static BackendSystem instance = null;

    private static ArrayList<Warehouse> warehouses;

    public BackendSystem() {
        warehouses = new ArrayList<>();
        warehouses.add(new Warehouse(0));
        warehouses.add(new Warehouse(1));
        warehouses.add(new Warehouse(2));
    }

    public static BackendSystem getInstance() {
        return instance == null ? new BackendSystem() : instance;
    }

    public Integer processOrder(Order order) throws SQLException {
        int warehouseID = getStoreByAddress(order.getAddress());
        order.setStatus("Transit");
        OrderConnector.getInstance().updateOrder(order);
        CustomDialog.show("Info", "Your order will be shipped from warehouse" + warehouses.get(warehouseID).getId(), null);
        return warehouseID;
    }

    public static int getStoreByAddress(String address) {
        int hash = address.hashCode() > 0 ? address.hashCode() : address.hashCode() * (-1);
        return ((hash + 100) % 3) ;
    }

    public static void main(String[] args) {
        var str = BackendSystem.getStoreByAddress("asdfdsfdsf");
        System.out.println(str);
    }

}
