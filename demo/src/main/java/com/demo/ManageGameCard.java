package com.demo;

import com.demo.EditGame.EditGameController;
import com.mysql.cj.protocol.Resultset;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ManageGameCard implements Initializable {

    @FXML
    private Label gameName;
    @FXML
    private ImageView gameImage;

    private int gameID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    GameCard gameCard = null;
    public void SetData(GameCard game)
    {
        gameCard = game;
        gameName.setText(game.getName());
        gameID = game.getID();

        //Image img = new Image(game.getImg());
        //gameImage.setImage(img);
    }

    @FXML
    void OnDeleteGameClicked(MouseEvent event) throws SQLException {
        System.out.println("Delete game");
        String sql = "Delete from game where game_ID = ?" ;
        PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1,gameID);
        int affectedRow = preparedStatement.executeUpdate();
        if(affectedRow > 0)
        {
            System.out.println("Game with id " + gameID + " has been deleted");
        }
    }

    @FXML
    void OnEditGameClicked(MouseEvent event) throws SQLException, IOException {
        Stage stage;
        Scene scene;
        Parent root;

        FXMLLoader load = new FXMLLoader();
        load.setLocation(getClass().getResource("EditGame/EditGame.fxml"));
        root = load.load();
        EditGameController editGameController = load.getController();
        editGameController.SetData(gameCard);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}