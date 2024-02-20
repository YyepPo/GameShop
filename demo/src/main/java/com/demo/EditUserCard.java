package com.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class EditUserCard implements Initializable {

    UserCard user;
//    Connection connection;
//    Statement statement;

    @FXML
    private ImageView userImage;
    @FXML
    private Label userName;
    private int ID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void SetData(UserCard user)
    {
        this.user = user;
        ID = user.getId();
        userName.setText(user.getUsername());
        //userImage.setImage(image);
        //Image image = new Image(user.getImage());
    }

    @FXML
    void OnDeleteUserClicked(MouseEvent event) {

    }

    @FXML
    void OnEditUserClicked(MouseEvent event) throws SQLException {
        System.out.println("Delete user button clicked");
        String sql = "Delete from user where user_ID = ?" ;
        PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1,ID);
        int affectedRow = preparedStatement.executeUpdate();
        if(affectedRow > 0)
        {
            System.out.println("Game with id " + ID + " has been deleted");
        }
    }


}
