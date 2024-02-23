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

public class EditGameController {

    @FXML
    private Button adminButton;

    @FXML
    private TextField ageRestriction;

    @FXML
    private TextField descriptionText;

    @FXML
    private TextField gameImageText;

    @FXML
    private TextField gameNameText;

    @FXML
    private TextField genreText;

    @FXML
    private Button homeButton;

    @FXML
    private HBox homeHBox;

    @FXML
    private Line homeLine;

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

    GameCard gameCard = null;
    private int gameID;
    public void SetData(GameCard gameCard)
    {
        this.gameCard = gameCard;
        gameID = gameCard.getID();
        gameNameText.setText(gameCard.getName());
        genreText.setText(gameCard.getGenre());
        priceText.setText(String.valueOf(gameCard.getPrice()));
        releaseDateText.setText(gameCard.getReleaseDate());
        descriptionText.setText(gameCard.getDescription());
        //game img
        ageRestriction.setText(String.valueOf(gameCard.getAgeRestriction()));
    }

    @FXML
    void OnUpdateGameClicked(MouseEvent event) throws SQLException, IOException {
        if(InputFieldsValid())
        {
            String sql = "UPDATE game SET price = ?, description = ?,title = ?,releaseDate = ?,ageRestriction = ? WHERE game_ID = ?";
            PreparedStatement statement = DataBaseConnection.getConnection().prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(priceText.getText()));
            statement.setString(2,descriptionText.getText());
            statement.setString(3, gameNameText.getText());
            statement.setString(4,releaseDateText.getText());
            statement.setInt(5,Integer.parseInt(ageRestriction.getText()));
            statement.setInt(6,gameID);
            statement.executeUpdate();

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Update successful!");
                SceneManager.LoadScene(event,getClass().getResource("../admin-panel.fxml"));
            } else {
                System.out.println("No game found with that name.");
            }
        }
    }

    private boolean InputFieldsValid() {
        if (gameNameText.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Game name text is empty!");
            return false;
        }
        if (genreText.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Genre text is empty!");
            return false;
        }
        if (priceText.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Price text is empty!");
            return false;
        }
        if (releaseDateText.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Release date text is empty!");
            return false;
        }
        if (descriptionText.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Description text is empty!");
            return false;
        }
        if (ageRestriction.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Age restriction text is empty!");
            return false;
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
