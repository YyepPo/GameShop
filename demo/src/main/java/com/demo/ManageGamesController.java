package com.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManageGamesController implements Initializable {

    @FXML
    private GridPane gameGrid;

    private List<GameCard> gameCards;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            gameCards = new ArrayList<GameCard>(GetGameCards());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        int row = 1;

        try {
            for (GameCard game : gameCards) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ManageGameCard.fxml"));

                AnchorPane pane = fxmlLoader.load();

                ManageGameCard cardController = fxmlLoader.getController();
                cardController.SetData(game);

                gameGrid.add(pane,0,++row);
                GridPane.setMargin(pane,new Insets(20));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<GameCard> GetGameCards() throws SQLException
    {
        List<GameCard> allGameCards = new ArrayList<GameCard>();
        ResultSet set = DataBaseConnection.getStatement().executeQuery("select * from game");
        while(set.next()) {
            final int ID = set.getInt(1);
            final String title = set.getString(2);
            final String genre = set.getString("genre");
            final int price = set.getInt("price");
            final String releaseDate = set.getString("releaseDate");
            final String description = set.getString("description");
            final int ageRestriction = set.getInt("ageRestriction");
            final String image = set.getString(7);
            AddGameCard(ID,title,image,allGameCards,genre,price,releaseDate,description,ageRestriction);
        }

        return allGameCards;
    }

    private void AddGameCard(final int ID,final String title,final String image,List<GameCard> cards,
                             final String genre,final int price,final String releaseDate,final String description,
                             final int ageRestriction)
    {
        GameCard gameCard = new GameCard(ID,title,image,genre,price,releaseDate,description,ageRestriction);
        cards.add(gameCard);
    }






















    @FXML
    void OnAdminButtonPressed(MouseEvent event) throws IOException {
        SceneManager.LoadScene(event,getClass().getResource("admin-panel.fxml"));
    }

    @FXML
    void OnHomeButtonPressed(MouseEvent event) throws IOException {
        SceneManager.LoadScene(event,getClass().getResource("hello-view.fxml"));
    }

    @FXML
    void OnProfileButtonPressed(MouseEvent event) throws IOException {
        SceneManager.LoadScene(event,getClass().getResource("profile.fxml"));
    }


}