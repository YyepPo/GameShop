package com.demo.EditGame;

import com.demo.DataBaseConnection;
import com.demo.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;

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
    void OnAddGameClicked(MouseEvent event) throws SQLException {
        if(!AreInputFieldValid()) {return;}

        String sql = "INSERT INTO game (title, genre, price, releaseDate,description,gameImg,ageRestriction,operatingSystem,memory,graphicsCard,gameStorage,CPU) VALUES (?, ?, ?, ?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(sql);
        preparedStatement.setString(1,gameNameText.getText());
        preparedStatement.setString(2,genreText.getText());
        preparedStatement.setInt(3,Integer.parseInt(priceText.getText()));
        preparedStatement.setString(4,releaseDateText.getText());
        preparedStatement.setString(5,descriptionText.getText());
        preparedStatement.setString(6, gameImageText.getText());
        preparedStatement.setInt(7,Integer.parseInt(ageRestriction.getText()));
        preparedStatement.setString(8,operatingSystemText.getText());
        preparedStatement.setInt(9,Integer.parseInt(memoryText.getText()));
        preparedStatement.setString(10,graphicsCardText.getText());
        preparedStatement.setInt(11,Integer.parseInt(gameStorageText.getText()));
        preparedStatement.setString(12, cpuText.getText());

        int rowsInserted = preparedStatement.executeUpdate();
        if (rowsInserted > 0) {
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
}
