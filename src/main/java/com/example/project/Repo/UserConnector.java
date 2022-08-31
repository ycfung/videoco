package com.example.project.Repo;

import com.example.project.Model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserConnector extends DBConnector {

    private static UserConnector connector = null;

    public UserConnector() throws SQLException {
        super();
    }

    public static UserConnector getInstance() throws SQLException {
        return connector == null ? new UserConnector() : connector;
    }

    // success, return uid, else, return -1
    public Integer addUser(User user) {
        try {
            PreparedStatement sql = conn.prepareStatement("INSERT INTO videoco.user (username, name, email, password, role," +
                    " points) VALUES(?,?,?,?,?,?)");
            sql.setString(1, user.getUsername());
            sql.setString(2, user.getName());
            sql.setString(3, user.getEmail());
            sql.setString(4, user.getPassword());
            sql.setString(5, user.getRole());
            sql.setInt(6, user.getPoints());
            if (sql.executeUpdate() == 0)
                return -1;
            sql = conn.prepareStatement("SELECT MAX(uid) FROM user");
            ResultSet resultSet = sql.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean updateUser(User user) throws SQLException {
        try {
            PreparedStatement sql = conn.prepareStatement("UPDATE videoco.user SET username=?, name=?, " +
                    "email=?, password=?, role=?, points=? WHERE uid=?");
            sql.setString(1, user.getUsername());
            sql.setString(2, user.getName());
            sql.setString(3, user.getEmail());
            sql.setString(4, user.getPassword());
            sql.setString(5, user.getRole());
            sql.setInt(6, user.getPoints());
            sql.setInt(7, user.getUid());
            return sql.executeUpdate() != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(User user) throws SQLException {
        try {
            PreparedStatement sql = conn.prepareStatement("DELETE FROM videoco.user where uid=?");
            sql.setInt(1, user.getUid());
            return sql.executeUpdate() != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getUserByUsername(String username) {
        try {
            PreparedStatement sql = conn.prepareStatement("SELECT * FROM videoco.user WHERE username=?");
            sql.setString(1, username);
            ResultSet resultSet = sql.executeQuery();
            resultSet.next();
            return new User(resultSet.getInt(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4),
                    resultSet.getString(5), resultSet.getString(6),
                    resultSet.getInt(7));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList<User> getAllAdmins() {
        try {
            ArrayList<User> list = new ArrayList<>();
            PreparedStatement sql = conn.prepareStatement("SELECT * FROM videoco.user WHERE role='admin'");
            ResultSet resultSet = sql.executeQuery();
            while (resultSet.next()) {
                list.add(new User(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getString(6),
                        resultSet.getInt(7)));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public ArrayList<User> getAllCustomers() {
        try {
            ArrayList<User> list = new ArrayList<>();
            PreparedStatement sql = conn.prepareStatement("SELECT * FROM videoco.user WHERE role='customer'");
            ResultSet resultSet = sql.executeQuery();
            while (resultSet.next()) {
                list.add(new User(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getString(6),
                        resultSet.getInt(7)));
            }
            return list;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUserByUid(Integer uid) {
        try {
            PreparedStatement sql = conn.prepareStatement("SELECT * FROM videoco.user WHERE uid=?");
            sql.setInt(1, uid);
            ResultSet resultSet = sql.executeQuery();
            resultSet.next();
            return new User(resultSet.getInt(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4),
                    resultSet.getString(5), resultSet.getString(6),
                    resultSet.getInt(7));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws SQLException {
        var a = UserConnector.getInstance().getAllCustomers();
        System.out.println(a.size());
    }

}
