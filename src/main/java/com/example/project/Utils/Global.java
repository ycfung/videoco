package com.example.project.Utils;

import com.example.project.Model.CartItem;
import com.example.project.Model.Movie;
import com.example.project.Model.Order;
import com.example.project.Model.User;
import com.example.project.Repo.MovieConnector;

import java.sql.SQLException;
import java.util.ArrayList;

public class Global {

    public static User user;

    public static Order order;

    public static ArrayList<CartItem> cartItems = new ArrayList<>();

    public static Double calcPrice(ArrayList<CartItem> cartItems) throws SQLException {
        Double price = 0.0;
        for (var item : cartItems) {
            System.out.println(item.getMid());
            Movie movie = MovieConnector.getInstance().getMovieByID(item.getMid());
            System.out.println(movie==null);
            price += movie.getPrice() * item.getQuantity();
        }
        return price;
    }

}
