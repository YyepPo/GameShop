package com.demo;

import com.mysql.cj.protocol.Resultset;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ManageGameCard implements Initializable {

    @FXML
    private Label gameName;
    @FXML
    private ImageView gameImage;

    private int gameID;

    Connection connection;
    Statement statement;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final String urll = "jdbc:mysql://localhost:3306/gameshop";
        final String username = "root";
        final String password = "";
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(urll,username,password);
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void SetData(GameCard game)
    {
        gameName.setText(game.getName());
        gameID = game.getID();
        //Image img = new Image(game.getImg());
        //gameImage.setImage(img);
    }

    @FXML
    void OnDeleteGameClicked(MouseEvent event) throws SQLException {

    }

    @FXML
    void OnEditGameClicked(MouseEvent event) throws SQLException {
        System.out.println("Edit game");
        System.out.println("Delete game");
        String sql = "Delete from game where game_ID = ?" ;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,gameID);
        int affectedRow = preparedStatement.executeUpdate();
        if(affectedRow > 0)
        {
            System.out.println("Row affected");
        }
    }


}