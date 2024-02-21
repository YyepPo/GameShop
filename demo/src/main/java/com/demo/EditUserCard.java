package com.demo;

import com.demo.EditGame.EditGameController;
import com.demo.EditUser.EditUserController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
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

    private UserCard userCard;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void SetData(UserCard user)
    {
        userCard = user;
        this.user = user;
        ID = user.getId();
        userName.setText(user.getUsername());
        //userImage.setImage(image);
        //Image image = new Image(user.getImage());
    }

    @FXML
    void OnDeleteUserClicked(MouseEvent event) throws SQLException {
        String sql = "Delete from user where user_ID = ?" ;
        PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1,ID);
        int affectedRow = preparedStatement.executeUpdate();
        if(affectedRow > 0)
        {
            System.out.println("Game with id " + ID + " has been deleted");
        }
    }

    @FXML
    void OnEditUserClicked(MouseEvent event) throws IOException {
        Stage stage;
        Scene scene;
        Parent root;

        FXMLLoader load = new FXMLLoader();
        load.setLocation(getClass().getResource("EditUser/EditUser.fxml"));
        root = load.load();
        EditUserController editUserController = load.getController();
        editUserController.SetData(userCard);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
