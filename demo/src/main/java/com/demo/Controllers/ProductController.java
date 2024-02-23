package com.demo.Controllers;

import com.demo.DataBaseConnection;
import com.demo.ManageUser.User;
import com.demo.SceneManager;
import com.demo.Test;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductController implements Initializable {

    @FXML
    private Label description;

    @FXML
    private Label gameName;

    @FXML
    private Label usersDollarAmount;

    @FXML
    private Button homeButton;

    @FXML
    private HBox homeHBox;

    @FXML
    private Line homeLine;

    @FXML
    private ImageView img;

    @FXML
    private Label gamePrice;

    @FXML
    private Button profileButton;

    @FXML
    private HBox profileHBox;

    @FXML
    private Line profileLine;

    @FXML
    private Label release;

    @FXML
    private Button buyButton;

    @FXML
    private ImageView ss1;

    @FXML
    private ImageView ss2;

    @FXML
    private ImageView ss3;

    private int gameID;

      boolean bFromProfile;


    @FXML
    private Label ageRestriction;
    @FXML
    private Label cpu;
    @FXML
    private Label ram;
    @FXML
    private Label storage;
    @FXML
    private Label card;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
                usersDollarAmount.setText(String.valueOf(User.GetDollarAmount(DataBaseConnection.getConnection())));
    }
    public void InitializeData(int id,String name, String price, String releaseDate, String gameDescription,
                               ArrayList<String> screenshots,int ageRestrcition,String cpu,int ram,int storage,String card)
    {
        gameID = id;
        gameName.setText(name);
        gamePrice.setText(price);
        release.setText(releaseDate);
        description.setText(gameDescription);
        this.bFromProfile = bFromProfile;
        this.ageRestriction.setText(String.valueOf(ageRestrcition));
        this.cpu.setText(cpu);
        this.ram.setText(String.valueOf(ram) + "GB");
        this.storage.setText(String.valueOf(storage) + "GB");
        this.card.setText(card);
        /*Image img1 = new Image(screenshots.get(0));
        ss1.setImage(img1);

        Image img2 = new Image(screenshots.get(1));
        ss2.setImage(img2);

        Image img3 = new Image(screenshots.get(2));
        ss3.setImage(img3);*/
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void OnHomeButtonPressed(MouseEvent event) throws IOException {
        //Load home page
        Test.SetIsInProfilePage(false);
        SceneManager.LoadScene(event,getClass().getResource("../hello-view.fxml"));
    }

    @FXML
    void OnProfileButtonPressed(MouseEvent event) throws IOException {
        Test.SetIsInProfilePage(true);

        //Load profile.fxml scene
        SceneManager sceneManager = SceneManager.getInstance();
        sceneManager.LoadScene(event,getClass().getResource("../profile.fxml"));
    }

    @FXML
    void buy(MouseEvent event) throws SQLException
    {
        //Check whether player owns this game or not
        String query = "SELECT * FROM user_game WHERE game_id = " + gameID +
                " AND user_id = " + User.GetUserID() +
                " AND EXISTS (SELECT 1 FROM user_game WHERE user_id = " + User.GetUserID() + ")";

        ResultSet set = DataBaseConnection.getStatement().executeQuery(query);
        if(set.next())
        {
            System.out.println("Player already owns the game");
            return;
        }

        //Check if user has enough coins to buy the game
        if(User.GetDollarAmount(DataBaseConnection.getConnection()) < Double.valueOf(gamePrice.getText()))
        {
            System.out.println("User doesnt have enough money");
            return;
        }

        double priceLabelToDouble = Double.parseDouble(gamePrice.getText());
        float dollarAmount = (float) (User.GetDollarAmount(DataBaseConnection.getConnection()) - priceLabelToDouble);

        //Updates the 'dollarAmount' field in the 'user' table with the calculated value,
        //sets the 'price' field text to the new dollar amount, and executes the SQL update statement.
        String updateSql = "Update user set dollarAmount =" + dollarAmount +" where user_ID =" + User.GetUserID();
        usersDollarAmount.setText(String.valueOf(dollarAmount));
        PreparedStatement updatePreparedStatement = DataBaseConnection.getConnection().prepareStatement(updateSql);
        updatePreparedStatement.executeUpdate();

        //0--Inserts a new record into the 'user_game' table associating the user (identified by user_id)
        // with a specific game (identified by game_id).
        String insertUser_gameSql ="INSERT INTO user_game (user_id, game_id) VALUES (?,?)";
        PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(insertUser_gameSql);
        preparedStatement.setInt(1,User.GetUserID());
        preparedStatement.setInt(2,gameID);
        preparedStatement.executeUpdate();
    }
}