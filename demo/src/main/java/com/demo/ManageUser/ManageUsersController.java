package com.demo.ManageUser;

import com.demo.DataBaseConnection;
import com.demo.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManageUsersController  implements Initializable {

    private List<UserCard> userCards;
    @FXML
    private GridPane gameGrid;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            userCards = new ArrayList<>(GetUserCards());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        int row = 1;

        try {
            for (UserCard user : userCards) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../EditUser.fxml"));

                AnchorPane pane = fxmlLoader.load();

                EditUserCard userCard = fxmlLoader.getController();
                userCard.SetData(user);

                gameGrid.add(pane,0,++row);
                GridPane.setMargin(pane,new Insets(20));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<UserCard> GetUserCards() throws SQLException {
        List<UserCard> allUserCards = new ArrayList<UserCard>();
        ResultSet set = DataBaseConnection.getStatement().executeQuery("select * from user");
        while(set.next()) {
            final int Id = set.getInt(1);
            final String username = set.getString(2);
            final String password = set.getString(3);
            final String email = set.getString(4);
            final String firstName = set.getString(5);
            final String lastName = set.getString(6);
            final String image = set.getString(7);
            final int dollarAmount = set.getInt(8);
            AddUserCard(Id,username,image,allUserCards,password,email,firstName,lastName,dollarAmount);
        }
        return allUserCards;
    }

    private void AddUserCard(final int ID,final String username,final String image,List<UserCard> allUserCards,
                             final String password,final String email,final String firstName,final String lastName,
                             final int dollarAmount)
    {
        UserCard userCard = new UserCard(ID,username,image,password,email,firstName,lastName,dollarAmount);
        allUserCards.add(userCard);
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
