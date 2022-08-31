package com.example.project.Repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DBConnector {
    static final String DB_URL = "jdbc:mysql://34.92.139.215/videoco";

    protected static Connection conn = null;

    public DBConnector() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://34.92.139.215/videoco", "root", "sdsc5003");
        conn.setAutoCommit(true);
    }
}
