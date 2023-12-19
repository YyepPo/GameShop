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
import javafx.stage.Stage;

import javax.xml.transform.Result;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
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

    boolean bIsUserInHomePage = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        homeButton.setStyle("-fx-background-color: darkgrey");
        homeHBox.setStyle("-fx-background-color: darkgrey");

        games = new ArrayList<BaseGame>(GetGames());

        int column = 0;
        int row = 1;
        gameCardNumber.setText("Games: " + Integer.toString(gameCounter));
        try {
            for (BaseGame game : games) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Card.fxml"));

                AnchorPane pane = fxmlLoader.load();

                CardController cardController = fxmlLoader.getController();
                cardController.SetData(game);

                //If in a row there are 5 columns than go to the next row and set its column number to 0
                //So basically a row can have 5 columns
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
        profileName.setText(User.getUserName());
    }

    final String urll = "jdbc:mysql://localhost:3306/gameshop";
    final String username = "root";
    final String password = "";

    public Connection GetConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(urll, username, password);
    }

        private List<BaseGame> GetGames()
    {
        List<BaseGame> baseGames = new ArrayList<BaseGame>();
        AddGameCard("Witcher 3","..\\..\\images\\Icons\\New Project.jpg",
                "Action RPG game",14.99,E_GameType.ActionRPG,null,16,
                "Windows 10 64 bit","i5 12400f",8,"GTX 1050Ti",
                "Nov 19/2021",50, baseGames);
        AddGameCard("Witcher 3","..\\..\\images\\Icons\\New Project.jpg",
                "Action RPG game",14.99,E_GameType.ActionRPG,null,16,
                "Windows 10 64 bit","i5 12400f",8,"GTX 1050Ti",
                "Nov 19/2021",50, baseGames);
        AddGameCard("Witcher 3","..\\..\\images\\Icons\\New Project.jpg",
                "Action RPG game",14.99,E_GameType.ActionRPG,null,16,
                "Windows 10 64 bit","i5 12400f",8,"GTX 1050Ti",
                "Nov 19/2021",50, baseGames);
        AddGameCard("Witcher 3","..\\..\\images\\Icons\\New Project.jpg",
                "Action RPG game",14.99,E_GameType.ActionRPG,null,16,
                "Windows 10 64 bit","i5 12400f",8,"GTX 1050Ti",
                "Nov 19/2021",50, baseGames);
        return baseGames;
    }

    private void AddGameCard(String name, String gameImg, String gameDesc, double gamePrice, E_GameType gameType,
                             ArrayList<String> screenShots, int ageRestriction, String operationSystem,String processor,
                             int memory,String graphicsCard,String gameReleaseDate,int storage,List<BaseGame> gameCards
                             )
    {
        VideoGame game = new VideoGame(name,gameImg,gameDesc,gamePrice,gameType,screenShots,ageRestriction,operationSystem,processor,
                memory,graphicsCard,storage,gameReleaseDate);
        game.SetImgSrc(gameImg);
        gameCards.add(game);
        gameCounter++;
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

    @FXML
    void OnProfileButtonPressed(MouseEvent event) throws IOException
    {
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