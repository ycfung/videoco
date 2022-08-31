package com.example.project.Repo;

import com.example.project.Model.CartItem;
import com.example.project.Model.Movie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CartItemConnector extends DBConnector {
    public CartItemConnector() throws SQLException {
        super();
    }

    private static CartItemConnector connector = null;


    public static CartItemConnector getInstance() throws SQLException {
        return connector == null ? new CartItemConnector() : connector;
    }

    public boolean insertCartItem(CartItem cartItem) throws SQLException {
        try {
            PreparedStatement sql = conn.prepareStatement("INSERT INTO videoco.cartItem (oid, mid, title, quantity) VALUES(?,?,?,?)");
            sql.setInt(1, cartItem.getOid());
            sql.setInt(2, cartItem.getMid());
            sql.setString(3, cartItem.getTitle());
            sql.setInt(4, cartItem.getQuantity());
            return sql.executeUpdate() != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<CartItem> getCartItemsByOid(Integer oid) {
        try {
            ArrayList<CartItem> list = new ArrayList<>();
            PreparedStatement sql = conn.prepareStatement("SELECT * FROM videoco.cartItem WHERE oid=?");
            sql.setInt(1, oid);
            ResultSet resultSet = sql.executeQuery();
            while (resultSet.next()) {
                list.add(new CartItem(resultSet.getInt(2), resultSet.getString(3), resultSet.getInt(4)));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
