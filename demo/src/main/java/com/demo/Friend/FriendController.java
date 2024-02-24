package com.demo.Friend;

import com.demo.Controllers.FriendCardController;
import com.demo.DataBaseConnection;
import com.demo.E_UserType;
import com.demo.SceneManager;
import com.demo.ManageUser.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FriendController implements Initializable {

    @FXML
    private TextField addFriendTextField;

    @FXML
    private Line fiendLine;

    @FXML
    private Button friendButton;

    @FXML
    private GridPane friendGrid;

    @FXML
    private HBox friendHBox;

    @FXML
    private ScrollPane gamesPane;

    @FXML
    private Button homeButton;

    @FXML
    private HBox homeHBox;

    @FXML
    private Line homeLine;

    @FXML
    private Button profileButton;

    @FXML
    private HBox profileHBox;

    @FXML
    private Line profileLine;

    @FXML
    private Label usernameText;

    private ArrayList<Integer> friendIDs = new ArrayList<>();
    private ArrayList<Friend> friends = new ArrayList<>();

    @FXML
    private Button adminButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        E_UserType userRole = User.IsAdmin(DataBaseConnection.getConnection());
        adminButton.setVisible(userRole == E_UserType.Admin);

        int column = 0;
        int row = 1;

        usernameText.setText(User.getUserName());

        //Find and retrieve the user IDs of friends for a
        //given username from a database, employing JOIN operations and PreparedStatement for
        //secure database interaction
        String sql = "SELECT u2.user_ID " +
                "From user u1 " +
                "Join user_friend uf on u1.user_ID = uf.user_ID "+
                "join user u2 on u2.user_ID = uf.friend_ID " +
                "Where u1.username = ?";
        try {
            PreparedStatement statement = DataBaseConnection.getConnection().prepareStatement(sql);
            statement.setString(1, User.getUserName());

            ResultSet set = statement.executeQuery();

            while(set.next())
            {
                int friendID = set.getInt("user_ID");
                friendIDs.add(friendID);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            friends = new ArrayList<Friend>(GetFriends());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            for (Friend friend: GetFriends()) {
                //Load FriendCard.fxml scene
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../FriendCard.fxml"));

                AnchorPane pane = fxmlLoader.load();

                FriendCardController friendCardController = fxmlLoader.getController();
                friendCardController.SetData(friend);

                //ensure that after reaching the third column, it moves to the next row
                if(column == 3)
                {
                    column = 0;
                    row++;
                }

                friendGrid.add(pane,column++,row);
                GridPane.setMargin(pane,new Insets(20));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ArrayList<Friend> GetFriends() throws SQLException {
        ArrayList<Friend> allFriends = new ArrayList<>();

        for (int i = 0; i < friendIDs.size(); i++)
        {
            Statement statement = DataBaseConnection.getConnection().createStatement();
            ResultSet set = statement.executeQuery("select * from user where user_ID = " + friendIDs.get(i));
            while(set.next())
            {
                int id = set.getInt("user_ID");
                String name = set.getString("username");

                AddFriendToGrid(id,name,"",allFriends);
            }
        }
        return allFriends;
    }
    @FXML
    void AddFriendEvent(MouseEvent event) throws SQLException   {
        Statement statement = DataBaseConnection.getConnection().createStatement();
        //Check whether the given friend username exists
        //If it does then add that friend
        String sql = "select * from user where username = '" + addFriendTextField.getText() +"';";
        ResultSet result = statement.executeQuery(sql);
        if(result.next())
        {
            String insert = "INSERT INTO user_friend(user_ID,friend_ID) VALUES (?,?)";
            PreparedStatement preparedStatement = DataBaseConnection.getConnection().prepareStatement(insert);
            preparedStatement.setInt(1,User.GetUserID());
            preparedStatement.setInt(2,result.getInt("user_ID"));
            preparedStatement.executeUpdate();
        }
        else
        {
            System.out.print("User could not found the user");
        }
    }

    private void AddFriendToGrid(int id,String name,String imagePath,ArrayList<Friend> allFriends)
    {
        Friend friend = new Friend(id,name,imagePath);
        allFriends.add(friend);
    }

    @FXML
    void OnFriendButtonPressed(MouseEvent event) throws IOException {
        SceneManager.LoadScene(event,getClass().getResource("../Friend.fxml"));
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
    void OnAdminButtonPressed(MouseEvent event) throws IOException {
        SceneManager.LoadScene(event,getClass().getResource("../admin-panel.fxml"));
    }


}
