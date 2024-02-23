package com.demo.ManageUser;

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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddUserController {

    @FXML
    private Button adminButton;

    @FXML
    private TextField dollarAmountText;

    @FXML
    private TextField emailText;

    @FXML
    private TextField firstNameText;

    @FXML
    private Button homeButton;

    @FXML
    private HBox homeHBox;

    @FXML
    private Line homeLine;

    @FXML
    private TextField lastNameText;

    @FXML
    private TextField passwordText;

    @FXML
    private Button profileButton;

    @FXML
    private HBox profileHBox;

    @FXML
    private HBox profileHBox1;

    @FXML
    private TextField profileImagePath;

    @FXML
    private Line profileLine;

    @FXML
    private Line profileLine1;

    @FXML
    private TextField usernameText;
    @FXML
    private TextField userRoleText;

    @FXML
    void OnAddUserClicked(MouseEvent event) throws SQLException {
        if(!AreInputFieldValid()) {return;}

        String hashedPassword = hashPassword(passwordText.getText());
        String sql = "INSERT INTO user (username,password,email,firstName,lastName,profileImagePath,dollarAmount) VALUES (?, ?, ?, ?,?,?,?)";
        PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(sql);
        preparedStatement.setString(1,usernameText.getText());
        preparedStatement.setString(2,hashedPassword);
        preparedStatement.setString(3,emailText.getText());
        preparedStatement.setString(4,firstNameText.getText());
        preparedStatement.setString(5,lastNameText.getText());
        preparedStatement.setString(6, profileImagePath.getText());
        preparedStatement.setInt(7,Integer.parseInt(dollarAmountText.getText()));
        int affectedRow = preparedStatement.executeUpdate();
        if(affectedRow > 0)
        {
            System.out.println("An user has been inserted into user table");
        }

        //Get user id
        int userID = 0;
        String sqlSelect = "SELECT user_ID FROM user WHERE username = ?";
        PreparedStatement statement = DataBaseConnection.getConnection().prepareStatement(sqlSelect);
        statement.setString(1, usernameText.getText());
        ResultSet result = statement.executeQuery();
        if (result.next())
        {
            userID = result.getInt("user_ID");
            JOptionPane.showMessageDialog(null,String.valueOf(userID));
        }

        //Set user type inside of user_type table
        String sqlInsert = "insert into user_type(user_role,user_ID) values (?,?)";
        PreparedStatement insertStatement = DataBaseConnection.getConnection().prepareStatement(sqlInsert);

        insertStatement.setInt(1, Integer.parseInt(userRoleText.getText()));
        insertStatement.setInt(2, userID);
        
        int affectedRowUserType = insertStatement.executeUpdate();
        if(affectedRowUserType > 0)
        {
            System.out.println("An user has been inserted into user_type table");
        }
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private final boolean AreInputFieldValid()
    {
        TextField[] textFields = {usernameText,passwordText,emailText,firstNameText,lastNameText,profileImagePath,dollarAmountText,userRoleText};

        for (TextField textField : textFields) {
            if (textField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null,textField.toString() + "is empty");
                return false;
            }
        }

        if(!ContainsOnlyNonLetters(userRoleText.getText())) {

            return false;
        }

        return true;
    }

    public boolean ContainsOnlyNonLetters(String text) {
        return text.matches("[^a-zA-Z]*");
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
