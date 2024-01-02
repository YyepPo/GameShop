package com.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import javax.xml.transform.Result;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private GridPane gameGrid;

    @FXML
    private Label gameCardNumber;

    @FXML
    private Label profileName;

    private List<BaseGame> games;

    private static int gameCounter = 0;

    @FXML
    private HBox profileHBox;
    @FXML
    private HBox homeHBox;

    @FXML
    private Button profileButton;
    @FXML
    private Button homeButton;

    @FXML
    private Line profileLine;
    @FXML
    private Line homeLine;

    boolean bIsUserInHomePage = true;

    Connection connection;
    Statement statement;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        gameCounter = 0;

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

        homeButton.setStyle("-fx-background-color: #4061A3");
        homeHBox.setStyle("-fx-background-color: #4061A3");

        homeLine.setVisible(true);
        profileLine.setVisible(false);

        try {
            games = new ArrayList<BaseGame>(GetGames());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        int column = 0;
        int row = 1;

        for(int i = 0;i<games.size();i++)
        {
            gameCounter++;
        }

        gameCardNumber.setText("Games: " + Integer.toString(gameCounter));
        try {
            for (BaseGame game : games) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Card.fxml"));

                AnchorPane pane = fxmlLoader.load();

                CardController cardController = fxmlLoader.getController();
                cardController.SetData(game,false);

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

    public void SetHelloControllerData()
    {
        //profileName.setText(User.getUserName());
    }

    private List<BaseGame> GetGames() throws SQLException {
        List<BaseGame> baseGames = new ArrayList<BaseGame>();
        ResultSet set = statement.executeQuery("select * from game");
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
        return baseGames;
    }

    private void AddGameCard(int id,String name, String gameImg, String gameDesc, double gamePrice, String gameType,
                             ArrayList<String> screenShots, int ageRestriction, String operationSystem,String processor,
                             int memory,String graphicsCard,String gameReleaseDate,int storage,List<BaseGame> gameCards
                             )
    {
        VideoGame game = new VideoGame(id,name,gameImg,gameDesc,gamePrice,gameType,screenShots,ageRestriction,operationSystem,processor,
                memory,graphicsCard,storage,gameReleaseDate);
        game.SetImgSrc(gameImg);
        gameCards.add(game);
    }

    @FXML
    void OnHomeButtonPressed(MouseEvent event)
    {
        if(bIsUserInHomePage)
        {
            System.out.println("User is already in home page");
            return;
        }
    }

    public void SetHelloControllerData(boolean isInProfilePage)
    {
        Test.SetIsInProfilePage(isInProfilePage);
    }


    @FXML
    void OnProfileButtonPressed(MouseEvent event) throws IOException
    {
        //Load profile page
        Test.SetIsInProfilePage(true);
         Stage stage;
         Scene scene;
         Parent root;

        FXMLLoader load = new FXMLLoader();
        load.setLocation(getClass().getResource("profile.fxml"));
        root = load.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}