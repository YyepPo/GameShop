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
import java.sql.ResultSet;
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
    @FXML
    private TextField operatingSystemText;
    @FXML
    private TextField memoryText;
    @FXML
    private TextField gpuText;
    @FXML
    private TextField gameStorageText;
    @FXML
    private TextField cpuText;

    @FXML
    private TextField ss1;

    @FXML
    private TextField ss2;
    @FXML
    private TextField ss3;


    GameCard gameCard = null;
    private int gameID;
    public void SetData(GameCard gameCard) throws SQLException {
        this.gameCard = gameCard;
        gameID = gameCard.getID();
        gameNameText.setText(gameCard.getName());
        genreText.setText(gameCard.getGenre());
        priceText.setText(String.valueOf(gameCard.getPrice()));
        releaseDateText.setText(gameCard.getReleaseDate());
        descriptionText.setText(gameCard.getDescription());
        //game img
        ageRestriction.setText(String.valueOf(gameCard.getAgeRestriction()));

        String sql = "select * from game_system_requirements where game_ID = " + gameID;
        ResultSet set = DataBaseConnection.getStatement().executeQuery(sql);
        if(set.next())
        {
            cpuText.setText(set.getString("cpu"));
            gameStorageText.setText("gameStorage");
            memoryText.setText("memory");
            operatingSystemText.setText("operatingSystem");
            gpuText.setText("graphicsCard");
        }
        JOptionPane.showMessageDialog(null,String.valueOf(gameID));

    }

    @FXML
    void OnUpdateGameClicked(MouseEvent event) throws SQLException, IOException {
        if(InputFieldsValid())
        {
            String sql = "UPDATE game SET price = ?, description = ?,title = ?,releaseDate = ?,ageRestriction = ?,screenshot1 = ?,screenshot2 = ?,screenshot3 = ? WHERE game_ID = ?";
            PreparedStatement statement = DataBaseConnection.getConnection().prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(priceText.getText()));
            statement.setString(2,descriptionText.getText());
            statement.setString(3, gameNameText.getText());
            statement.setString(4,releaseDateText.getText());
            statement.setInt(5,Integer.parseInt(ageRestriction.getText()));
            statement.setString(6,ss1.getText());
            statement.setString(7,ss2.getText());
            statement.setString(8,ss3.getText());
            statement.setInt(9,gameID);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Update successful Game table!");
                SceneManager.LoadScene(event,getClass().getResource("../admin-panel.fxml"));
            } else {
                System.out.println("No game found with that name GAME TABLE.");
            }

            InsertDataIntoGameSystemRequirementsTable();
        }
    }

    private void InsertDataIntoGameSystemRequirementsTable() throws SQLException {
        String systemSql = "Update game_system_requirements set operatingSystem = ?,memory = ?,graphicsCard = ?,gameStorage = ?,cpu = ? where game_ID =  " + gameID;
        PreparedStatement requirementsStatement = DataBaseConnection.getConnection().prepareStatement(systemSql);
        requirementsStatement.setString(1,operatingSystemText.getText());
        requirementsStatement.setInt(2,Integer.parseInt(memoryText.getText()));
        requirementsStatement.setString(3,gpuText.getText());
        requirementsStatement.setInt(4,Integer.parseInt(gameStorageText.getText()));
        requirementsStatement.setString(5,cpuText.getText());
        requirementsStatement.executeUpdate();
        int rowsUpdated = requirementsStatement.executeUpdate();

        if (rowsUpdated > 0) {
            System.out.println("Update successful! Properties");
            JOptionPane.showMessageDialog(null,String.valueOf(gameID));
        } else {
            System.out.println("No game found with that name properties.");
            String insertSql = "Insert into game_system_requirements values(?,?,?,?,?,?)";
            PreparedStatement insertStatement = DataBaseConnection.getConnection().prepareStatement(insertSql);
            insertStatement.setInt(1,gameID);
            insertStatement.setString(2,operatingSystemText.getText());
            insertStatement.setInt(3,Integer.parseInt(memoryText.getText()));
            insertStatement.setString(4,gpuText.getText());
            insertStatement.setInt(5,Integer.parseInt(gameStorageText.getText()));
            insertStatement.setString(6, cpuText.getText());
            int insertedRow = insertStatement.executeUpdate();
            if(insertedRow > 0)
            {
                JOptionPane.showMessageDialog(null,"Inserted successfully");
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Insertion has failed");
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
    @FXML
    void OnFriendButtonPressed(MouseEvent event) throws IOException {
        SceneManager.LoadScene(event,getClass().getResource("../Friend.fxml"));
    }

}
