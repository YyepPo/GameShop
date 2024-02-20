package com.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class LoginController implements Initializable
{
    @FXML
    private Pane loginPane;

    @FXML
    private TextField password;

    @FXML
    private TextField username;

    @FXML
    private TextField registerEmail;

    @FXML
    private TextField registerFirstname;

    @FXML
    private TextField registerLastName;

    @FXML
    private Pane registerPane;

    @FXML
    private TextField registerPassword;

    @FXML
    private TextField registerUsername;

    @FXML
    private TextField registerImagePath;
    @FXML
    private TextField registerDollarAmount;
    boolean bUserNameExists = false;
    boolean bPasswordExists = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataBaseConnection.CreateConnectionWithDB();
    }

    @FXML
    void Register(MouseEvent event)
    {
        loginPane.setVisible(false);
        registerPane.setVisible(true);
    }

    @FXML
    void login(MouseEvent event) throws SQLException, ClassNotFoundException, IOException {

        if(!AreInputFieldsValid())
        {
            System.out.println("Field are not filled");
            return;
        }

        /*String enteredPassword = password.getText();
        String hashedEnteredPassword = hashPassword(enteredPassword);
        System.out.println(hashedEnteredPassword);
        ResultSet usernamePasswordResult = DataBaseConnection.getStatement().executeQuery("select * from user where username ="+"'"+username.getText()+"' and password="+hashedEnteredPassword);
        if(usernamePasswordResult.next())
        {
            String hashedPasswordFromDatabase = usernamePasswordResult.getString("password");
            if(usernamePasswordResult.getString(3).equals(hashedPasswordFromDatabase)  &&
                usernamePasswordResult.getString(2).equals(username.getText()))
            {
                ResultSet set = DataBaseConnection.getStatement().executeQuery("select user_ID from user where username="+"'"+username.getText()+"'");
                if(set.next())
                {
                    User.SetUserID(set.getInt(1));
                    System.out.println(set.getInt(1));
                }
                //conn.close();
                User.setUserName(username.getText());

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("hello-view.fxml"));
                Parent root = loader.load();

                HelloController controller = loader.getController();
                controller.SetHelloControllerData();
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }*/
        try {
            String usernameValue = username.getText();
            String passwordValue = password.getText();
            String hashedPassword = hashPassword(passwordValue);

            String query = "SELECT user_ID,password FROM user WHERE username = ?";
            PreparedStatement statement = DataBaseConnection.getConnection().prepareStatement(query);
            statement.setString(1, usernameValue);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                if (hashedPassword.equals(storedPassword)) {
                    int userID = resultSet.getInt("user_ID");
                    User.SetUserID(userID);
                    System.out.println(userID);

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("hello-view.fxml"));
                    Parent root = loader.load();

                    HelloController controller = loader.getController();
                    controller.SetHelloControllerData();
                    Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else {
                    System.out.println("Incorrect password");
                }
            } else {
                System.out.println("User not found");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void LoadHelloViewScene(MouseEvent event) throws IOException {

    }

    @FXML
    void register(MouseEvent event) throws SQLException, ClassNotFoundException {
        final String username = registerUsername.getText();
        final String password = registerPassword.getText();
        final String email = registerEmail.getText();
        final String firstName = registerFirstname.getText();
        final String lastName = registerLastName.getText();
        final String imagePath = registerImagePath.getText();
        final int dollarAmount = Integer.parseInt(registerDollarAmount.getText());

        String hashedPassword = hashPassword(password);

       System.out.println(username+" "+hashedPassword+" "+email+" "+ firstName +" "+ lastName);
    
        String query = " insert into user (username,password, email, firstName, lastName,profileImagePath,dollarAmount) "
                + " values (?, ?, ?, ?, ?, ?,?) ";

        PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(query);
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,hashedPassword);
        preparedStatement.setString(3,email);
        preparedStatement.setString(4,firstName);
        preparedStatement.setString(5,lastName);
        preparedStatement.setString(6,imagePath);
        preparedStatement.setInt(7,dollarAmount);
        preparedStatement.executeUpdate();
        DataBaseConnection.getConnection().close();

        User.setUserName(username);

        registerPane.setVisible(false);
        loginPane.setVisible(true);
    }

    private boolean CanUserLogin()
    {
        return bUserNameExists && bPasswordExists;
    }

    private boolean AreInputFieldsValid() {
        if (username.getText().isEmpty() && password.getText().isEmpty()) {
            System.out.println("Fill both fields");
            return false;
        }

        if (username.getText().isEmpty()) {
            System.out.println("Fill username");
            return false;
        }

        if (password.getText().isEmpty()) {
            System.out.println("Fill password");
            return false;
        }
        return true;
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
}
