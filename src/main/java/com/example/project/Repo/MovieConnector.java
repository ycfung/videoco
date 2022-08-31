package com.example.project.Repo;

import com.example.project.Model.Movie;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MovieConnector extends DBConnector {

    private static MovieConnector connector = null;

    public MovieConnector() throws SQLException {
        super();
    }

    public static MovieConnector getInstance() throws SQLException {
        return connector == null ? new MovieConnector() : connector;
    }


    public boolean deleteMovie(Integer mid) {
        try {
            PreparedStatement sql = conn.prepareStatement("DELETE FROM videoco.movie where mid=?");
            sql.setInt(1, mid);
            return sql.executeUpdate() != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Integer addMovie(Movie movie) {
        try {
            PreparedStatement sql = conn.prepareStatement("INSERT INTO videoco.movie (title, genre, director, actors, " +
                    "quantity, date, price) VALUES(?,?,?,?,?,?,?)");
            sql.setString(1, movie.getTitle());
            sql.setString(2, movie.getGenre());
            sql.setString(3, movie.getDirector());
            sql.setString(4, movie.getActors());
            sql.setInt(5, movie.getQuantity());
            sql.setString(6, movie.getDate());
            sql.setDouble(7, movie.getPrice());
            if (sql.executeUpdate() == 0)
                return -1;
            sql = conn.prepareStatement("SELECT MAX(mid) FROM videoco.movie");
            ResultSet resultSet = sql.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public Movie getMovieByID(Integer id) {
        try {
            PreparedStatement sql = conn.prepareStatement("SELECT * FROM videoco.movie WHERE mid=?");
            sql.setInt(1, id);
            ResultSet resultSet = sql.executeQuery();
            resultSet.next();
            return new Movie(resultSet.getInt(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4),
                    resultSet.getString(5), resultSet.getInt(6),
                    resultSet.getString(7), resultSet.getDouble(8));
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Movie> getAllMovies() {
        try {
            ArrayList<Movie> list = new ArrayList<>();
            PreparedStatement sql = conn.prepareStatement("SELECT * FROM videoco.movie");
            ResultSet resultSet = sql.executeQuery();
            while (resultSet.next()) {
                list.add(new Movie(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getInt(6),
                        resultSet.getString(7), resultSet.getDouble(8)));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Movie> getByCategory(String category) {
        try {
            ArrayList<Movie> list = new ArrayList<>();
            PreparedStatement sql = conn.prepareStatement("SELECT * FROM videoco.movie WHERE genre=?");
            sql.setString(1, category);
            ResultSet resultSet = sql.executeQuery();
            while (resultSet.next()) {
                list.add(new Movie(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getInt(6),
                        resultSet.getString(7), resultSet.getDouble(8)));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Movie> getByName(String title) {
        try {
            ArrayList<Movie> list = new ArrayList<>();
            PreparedStatement sql = conn.prepareStatement("SELECT * FROM videoco.movie WHERE title=?");
            sql.setString(1, title);
            ResultSet resultSet = sql.executeQuery();
            while (resultSet.next()) {
                list.add(new Movie(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getInt(6),
                        resultSet.getString(7), resultSet.getDouble(8)));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
