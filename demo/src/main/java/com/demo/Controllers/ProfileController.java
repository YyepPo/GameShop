package com.demo.Controllers;

import com.demo.*;
import com.demo.ManageUser.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private Button homeButton;

    @FXML
    private HBox homeHBox;

    @FXML
    private Line homeLine;

    @FXML
    private Button profileButton;

    @FXML
    private HBox profileHBox;

    @FXML
    private Line profileLine;
    @FXML
    private Label usernameText;
    @FXML
    private Label gameNumberText;

    @FXML
    private GridPane gameGrid;

    private ArrayList<VideoGame> games = new ArrayList<>();
    private ArrayList<Integer> gameIds = new ArrayList<>();

    @FXML
    private ScrollPane gamesPane;
    @FXML
    private GridPane friendGrid;

    int gameCounter = 0;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameText.setText(User.getUserName());

        profileButton.setStyle("-fx-background-color: #4061A3");
        profileHBox.setStyle("-fx-background-color: #4061A3");

        homeLine.setVisible(false);
        profileLine.setVisible(true);

        System.out.println(User.getUserName());

        int column = 0;
        int row = 1;

        String sql = "SELECT u.username, g.game_ID " +
                "FROM user u " +
                "JOIN user_game ug ON u.user_ID = ug.user_id " +
                "JOIN game g ON ug.game_id = g.game_ID " +
                "WHERE u.username = ?";

        try {
            PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(sql);
            preparedStatement.setString(1,User.getUserName());

            ResultSet results = preparedStatement.executeQuery();

            while (results.next())
            {
                final int titleResult = results.getInt("game_ID");
                gameIds.add(titleResult);
                gameCounter++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        gameNumberText.setText("Games: " + String.valueOf(gameCounter));

        try {
            games = new ArrayList<VideoGame>(GetGames());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            for (VideoGame game : games) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../Card.fxml"));

                AnchorPane pane = fxmlLoader.load();

                CardController cardController = fxmlLoader.getController();
                cardController.SetData(game,true);

                //If in a row there are 5 columns than go to the next row and set its column number to 0
                if(column == 3)
                {
                    column = 0;
                    row++;
                }

                gameGrid.add(pane,column++,row);
                GridPane.setMargin(pane,new Insets(20));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<VideoGame> GetGames() throws SQLException {

        List<VideoGame> baseGames = new ArrayList<VideoGame>();

        for (int i = 0; i < gameIds.size(); i++) {
            ResultSet set = DataBaseConnection.getStatement().executeQuery("select * from game where game_ID="+gameIds.get(i));
            while(set.next())
            {
                final int gameID = set.getInt(1);
                final String gameTitle = set.getString(2);
                final String gameGenre = set.getString(3);
                final float gamePrice = set.getFloat(4);
                final String releaseDate = set.getString(5);
                final String description = set.getString(6);
                final String img = set.getString(7);
                //..\..\images\Icons\New Project.jpg
                final int ageRestriction = set.getInt(8);
                final String operatingSystem = set.getString(9);
                final int memory = set.getInt(10);
                final String graphicsCard = set.getString(11);
                final int storage = set.getInt(12);
                final String cpu = set.getString(16);

                final String ss1 = set.getString(13);
                final String ss2 = set.getString(14);
                final String ss3 = set.getString(15);

                ArrayList<String> screenshots = new ArrayList<>();
                screenshots.add(ss1);
                screenshots.add(ss2);
                screenshots.add(ss3);

                AddGameCard(gameID,gameTitle,img,description
                        ,gamePrice,gameGenre,screenshots,16,
                        operatingSystem,cpu,memory,graphicsCard,
                        releaseDate,storage, baseGames);
            }
        }

        return baseGames;
    }

        private void AddGameCard(int id, String name, String gameImg, String gameDesc, double gamePrice, String gameType,
                             ArrayList<String> screenShots, int ageRestriction, String operationSystem, String processor,
                             int memory, String graphicsCard, String gameReleaseDate, int storage, List<VideoGame> gameCards
    )
    {
        VideoGame game = new VideoGame(id,name,gameImg,gameDesc,gamePrice,gameType,screenShots,ageRestriction,operationSystem,processor,
                memory,graphicsCard,storage,gameReleaseDate);
        game.SetImgSrc(gameImg);
        gameCards.add(game);
    }

    @FXML
    void OnFriendButtonPressed(MouseEvent event) throws IOException
    {
        SceneManager.LoadScene(event,getClass().getResource("../Friend.fxml"));
    }

    @FXML
    void OnHomeButtonPressed(MouseEvent event) throws IOException {

        Test.SetIsInProfilePage(false);
        SceneManager.LoadScene(event,getClass().getResource("../hello-view.fxml"));
    }

}
