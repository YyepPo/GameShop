package com.demo;

import com.mysql.cj.protocol.Resultset;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private GridPane gameGrid;

    Connection connection;
    Statement statement;

    private ArrayList<BaseGame> games = new ArrayList<>();
    private ArrayList<Integer> gameIds = new ArrayList<>();

    @FXML
    private ScrollPane gamesPane;
    @FXML
    private GridPane friendGrid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profileButton.setStyle("-fx-background-color: #4061A3");
        profileHBox.setStyle("-fx-background-color: #4061A3");

        homeLine.setVisible(false);
        profileLine.setVisible(true);

        System.out.println(User.getUserName());

        int column = 0;
        int row = 1;

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

        String sql = "SELECT u.username, g.game_ID " +
                "FROM user u " +
                "JOIN user_game ug ON u.user_ID = ug.user_id " +
                "JOIN game g ON ug.game_id = g.game_ID " +
                "WHERE u.username = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,User.getUserName());

            ResultSet results = preparedStatement.executeQuery();

            while (results.next())
            {
                final int titleResult = results.getInt("game_ID");
                gameIds.add(titleResult);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            games = new ArrayList<BaseGame>(GetGames());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            for (BaseGame game : games) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Card.fxml"));

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

    @FXML
    void OnHomeButtonPressed(MouseEvent event) throws IOException {

        Test.SetIsInProfilePage(false);
        Stage stage;
        Scene scene;
        Parent root;

        FXMLLoader load = new FXMLLoader();
        load.setLocation(getClass().getResource("hello-view.fxml"));
        root = load.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private List<BaseGame> GetGames() throws SQLException {

        List<BaseGame> baseGames = new ArrayList<BaseGame>();

        for (int i = 0; i < gameIds.size(); i++) {
            ResultSet set = statement.executeQuery("select * from game where game_ID="+gameIds.get(i));
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

                final String ss1 = set.getString(13);
                final String ss2 = set.getString(14);
                final String ss3 = set.getString(15);

                ArrayList<String> screenshots = new ArrayList<>();
                screenshots.add(ss1);
                screenshots.add(ss2);
                screenshots.add(ss3);

                AddGameCard(gameID,gameTitle,img,description
                        ,gamePrice,gameGenre,screenshots,16,
                        operatingSystem,"i5 12400f",memory,graphicsCard,
                        releaseDate,storage, baseGames);
            }
        }

        return baseGames;
    }

        private void AddGameCard(int id, String name, String gameImg, String gameDesc, double gamePrice, String gameType,
                             ArrayList<String> screenShots, int ageRestriction, String operationSystem, String processor,
                             int memory, String graphicsCard, String gameReleaseDate, int storage, List<BaseGame> gameCards
    )
    {
        VideoGame game = new VideoGame(id,name,gameImg,gameDesc,gamePrice,gameType,screenShots,ageRestriction,operationSystem,processor,
                memory,graphicsCard,storage,gameReleaseDate);
        game.SetImgSrc(gameImg);
        gameCards.add(game);
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void OnFriendButtonPressed(MouseEvent event) throws IOException
    {
        FXMLLoader load = new FXMLLoader();
        load.setLocation(getClass().getResource("Friend.fxml"));
        root = load.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
