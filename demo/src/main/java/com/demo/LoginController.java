package com.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

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

    boolean bUserNameExists = false;
    boolean bPasswordExists = false;

    @FXML
    void Register(MouseEvent event)
    {
        loginPane.setVisible(false);
        registerPane.setVisible(true);
    }

    @FXML
    void login(MouseEvent event) throws SQLException, ClassNotFoundException
    {
        final String urll = "jdbc:mysql://localhost:3306/gameshop";
        final String usernamee = "root";
        final String passwordd = "";
        Statement st;
        Connection conn;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn  = DriverManager.getConnection(urll,usernamee,passwordd);
            st = conn.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        if(!AreInputFieldsValid())
        {
            System.out.println("Field are not filled");
            return;
        }

        String a = password.getText();
        int stringPasswordToIntPassword = Integer.parseInt(a);
        ResultSet usernamePasswordResult = st.executeQuery("select * from user where username ="+"'"+username.getText()+"' and password="+stringPasswordToIntPassword);

        while(usernamePasswordResult.next())
        {
            if(usernamePasswordResult.getInt(3) == stringPasswordToIntPassword &&
                usernamePasswordResult.getString(2).equals(username.getText()))
            {
                System.out.println("Hebele hubele");
                conn.close();
                return;
            }
        }
    }

    @FXML
    void register(MouseEvent event) throws SQLException, ClassNotFoundException {
        final String username = registerUsername.getText();
        final int password = Integer.parseInt(registerPassword.getText());
        final String email = registerEmail.getText();
        final String firstName = registerFirstname.getText();
        final String lastName = registerLastName.getText();

        System.out.println(username+" "+password+" "+email+" "+ firstName +" "+ lastName);

        String query = " insert into user (username, password, email, fistName, lsatName)"
                + " values (?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,username);
        preparedStatement.setInt(2,password);
        preparedStatement.setString(3,email);
        preparedStatement.setString(4,firstName);
        preparedStatement.setString(5,lastName);
        preparedStatement.executeUpdate();
        connection.close();
        registerPane.setVisible(false);
        loginPane.setVisible(true);
    }

    private boolean CanUserLogin()
    {
        return bUserNameExists && bPasswordExists;
    }

    private boolean AreInputFieldsValid()
    {
        if(username.getText().isEmpty() && password.getText().isEmpty())
        {
            System.out.println("Fill both fields");
            return false;
        }

        if(username.getText().isEmpty())
        {
            System.out.println("Fill username");
            return false;
        }

        if(password.getText().isEmpty())
        {
            System.out.println("Fill password");
            return false;
        }
        return true;
    }

    Connection connection;
    Statement statement;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
    }
}
