package com.example.project.Repo;

import com.example.project.Model.CartItem;
import com.example.project.Model.Order;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderConnector extends DBConnector {

    public OrderConnector() throws SQLException {
        super();
    }

    private static OrderConnector connector = null;


    public static OrderConnector getInstance() throws SQLException {
        return connector == null ? new OrderConnector() : connector;
    }

    public Integer insertOrder(Order order) {
        try {
            PreparedStatement sql = conn.prepareStatement("INSERT INTO videoco.order (uid, price, status, time," +
                    " payment, address) VALUES(?,?,?,?,?,?)");
            sql.setInt(1, order.getUid());
            sql.setDouble(2, order.getPrice());
            sql.setString(3, order.getStatus());
            sql.setString(4, order.getTime());
            sql.setString(5, order.getPayment());
            sql.setString(6, order.getAddress());
            if (sql.executeUpdate() == 0)
                return -1;
            sql = conn.prepareStatement("SELECT MAX(oid) FROM videoco.order");
            ResultSet resultSet = sql.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean updateOrder(Order order) {
        try {
            PreparedStatement sql = conn.prepareStatement("UPDATE videoco.order SET uid=?, price=?, " +
                    "status=?, time=?, payment=?, address=? WHERE oid=?");
            sql.setInt(1, order.getUid());
            sql.setDouble(2, order.getPrice());
            sql.setString(3, order.getStatus());
            sql.setString(4, order.getTime());
            sql.setString(5, order.getPayment());
            sql.setString(6, order.getAddress());
            sql.setInt(7, order.getOid());
            return sql.executeUpdate() != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteOrder(Order order) {
        try {
            PreparedStatement sql = conn.prepareStatement("DELETE FROM videoco.order where oid=?");
            sql.setInt(1, order.getOid());
            return sql.executeUpdate() != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Order getOrderByOid(Integer oid) {
        try {
            ArrayList<Order> list = new ArrayList<>();
            PreparedStatement sql = conn.prepareStatement("SELECT * FROM videoco.order WHERE oid=?");
            sql.setInt(1,oid);
            ResultSet resultSet = sql.executeQuery();
            resultSet.next();
            return new Order(resultSet.getInt(1), resultSet.getInt(2),
                    resultSet.getDouble(3), resultSet.getString(4),
                    resultSet.getString(5), resultSet.getString(6),
                    resultSet.getString(7));
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Order> getAllOrders() {
        try {
            ArrayList<Order> list = new ArrayList<>();
            PreparedStatement sql = conn.prepareStatement("SELECT * FROM videoco.order");
            ResultSet resultSet = sql.executeQuery();
            while (resultSet.next()) {
                list.add(new Order(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getDouble(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getString(6),
                        resultSet.getString(7)));
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Order> getByUidAndStatus(Integer uid, String status) {
        try {
            ArrayList<Order> list = new ArrayList<>();
            PreparedStatement sql = conn.prepareStatement("SELECT * FROM videoco.order WHERE uid=? AND status=?");
            sql.setInt(1, uid);
            sql.setString(2, status);
            ResultSet resultSet = sql.executeQuery();
            while (resultSet.next()) {
                list.add(new Order(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getDouble(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getString(6),
                        resultSet.getString(7)));
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Order> getOrdersByUid(Integer uid) {
        try {
            ArrayList<Order> list = new ArrayList<>();
            PreparedStatement sql = conn.prepareStatement("SELECT * FROM videoco.order WHERE uid=?");
            sql.setInt(1, uid);
            ResultSet resultSet = sql.executeQuery();
            while (resultSet.next()) {
                list.add(new Order(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getDouble(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getString(6),
                        resultSet.getString(7)));
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }


    public static void main(String[] args) throws SQLException {
        Order order = OrderConnector.getInstance().getOrderByOid(1);
        System.out.println(order.getAddress());
    }

}
