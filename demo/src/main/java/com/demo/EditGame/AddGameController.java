package com.demo.EditGame;

import com.demo.Controllers.BoughtProductController;
import com.demo.Controllers.HelloController;
import com.demo.DataBaseConnection;
import com.demo.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddGameController {

    @FXML
    private Button adminButton;

    @FXML
    private TextField ageRestriction;

    @FXML
    private TextField cpuText;

    @FXML
    private TextField descriptionText;

    @FXML
    private TextField gameImageText;

    @FXML
    private TextField gameNameText;

    @FXML
    private TextField gameStorageText;

    @FXML
    private TextField genreText;

    @FXML
    private TextField graphicsCardText;

    @FXML
    private Button homeButton;

    @FXML
    private HBox homeHBox;

    @FXML
    private Line homeLine;

    @FXML
    private TextField memoryText;

    @FXML
    private TextField operatingSystemText;

    @FXML
    private TextField priceText;

    @FXML
    private Button profileButton;

    @FXML
    private HBox profileHBox;

    @FXML
    private HBox profileHBox1;

    @FXML
    private Line profileLine;

    @FXML
    private Line profileLine1;

    @FXML
    private TextField releaseDateText;

    @FXML
    private TextField ss1;

    @FXML
    private TextField ss2;

    @FXML
    private TextField ss3;

    int gameID = 16;

    @FXML
    void OnAddGameClicked(MouseEvent event) throws SQLException, IOException {
        if(!AreInputFieldValid()) {return;}

        String sql = "INSERT INTO game (title, genre, price, releaseDate,description,gameImg,ageRestriction,screenshot1,screenshot2,screenshot3) VALUES (?, ?, ?, ?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(sql);
        preparedStatement.setString(1,gameNameText.getText());
        preparedStatement.setString(2,genreText.getText());
        preparedStatement.setInt(3,Integer.parseInt(priceText.getText()));
        preparedStatement.setString(4,releaseDateText.getText());
        preparedStatement.setString(5,descriptionText.getText());
        preparedStatement.setString(6, gameImageText.getText());
        preparedStatement.setInt(7,Integer.parseInt(ageRestriction.getText()));
        preparedStatement.setString(8, ss1.getText());
        preparedStatement.setString(9, ss2.getText());
        preparedStatement.setString(10, ss3.getText());

        int rowsInserted = preparedStatement.executeUpdate();

        if (rowsInserted > 0) {
            String insertIntoGameSystemSql = "Insert into game_system_requirements(operatingSystem,memory,graphicsCard,gameStorage,cpu) values(?,?,?,?,?)";
            PreparedStatement statement = DataBaseConnection.getConnection().prepareStatement(insertIntoGameSystemSql);
            statement.setString(1,operatingSystemText.getText());
            statement.setInt(2,Integer.parseInt(memoryText.getText()));
            statement.setString(3, graphicsCardText.getText());
            statement.setInt(4,Integer.parseInt(gameStorageText.getText()));
            statement.setString(5, cpuText.getText());
            int rowsInsertedIntoGameSystem = statement.executeUpdate();
            if(rowsInsertedIntoGameSystem > 0)
            {
                JOptionPane.showMessageDialog(null,gameNameText.getText() + " has been added successfully");
                SceneManager.LoadScene(event,getClass().getResource("../admin-panel.fxml"));
            }
            System.out.println("A new row has been inserted.");

        }

    }

    private final boolean AreInputFieldValid()
    {
        TextField[] textFields = {gameNameText,genreText,priceText,releaseDateText,
                descriptionText,gameImageText,ageRestriction,operatingSystemText,memoryText,
                graphicsCardText,gameStorageText,cpuText};

        for (TextField textField : textFields) {
            if (textField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null,textField.toString() + "is empty");
                return false;
            }
        }
        return true;
    }


    @FXML
    void OnAdminButtonPressed(MouseEvent event) throws IOException {
        SceneManager.LoadScene(event,getClass().getResource("../admin-panel.fxml"));
    }
    @FXML
    void OnHomeButtonPressed(MouseEvent event) throws IOException {
        SceneManager.LoadScene(event,getClass().getResource("../hello-view.fxml"));
    }
    @FXML
    void OnProfileButtonPressed(MouseEvent event) throws IOException {
        SceneManager.LoadScene(event,getClass().getResource("../profile.fxml"));
    }
    @FXML
    void OnFriendButtonPressed(MouseEvent event) throws IOException
    {
        SceneManager.LoadScene(event,getClass().getResource("../Friend.fxml"));
    }
}
