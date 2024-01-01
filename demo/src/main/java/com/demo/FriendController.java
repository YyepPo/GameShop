package com.demo;

import com.mysql.cj.protocol.Resultset;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;

import javax.lang.model.type.ArrayType;
import javax.xml.transform.Result;
import javax.xml.xpath.XPathEvaluationResult;
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

    private ArrayList<Integer> friendIDs = new ArrayList<>();
    private ArrayList<Friend> friends = new ArrayList<>();
    Connection connection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        int column = 0;
        int row = 1;

        final String urll = "jdbc:mysql://localhost:3306/gameshop";
        final String username = "root";
        final String password = "";
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(urll,username,password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        String sql = "SELECT u2.user_ID " +
                "From user u1 " +
                "Join user_friend uf on u1.user_ID = uf.user_ID "+
                "join user u2 on u2.user_ID = uf.friend_ID " +
                "Where u1.username = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,User.getUserName());

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
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("FriendCard.fxml"));

                AnchorPane pane = fxmlLoader.load();

                FriendCardController friendCardController = fxmlLoader.getController();
                friendCardController.SetData(friend);

                //If in a row there are 5 columns than go to the next row and set its column number to 0
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
            Statement statement = connection.createStatement();
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
        System.out.println(addFriendTextField.getText());
        Statement statement = connection.createStatement();
        String sql = "select * from user where username = '" + addFriendTextField.getText() +"';";
        ResultSet result = statement.executeQuery(sql);
        if(result.next())
        {
            String insert = "INSERT INTO user_friend(user_ID,friend_ID) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setInt(1,User.GetUserID());
            preparedStatement.setInt(2,result.getInt("user_ID"));
            preparedStatement.executeUpdate();
        }
        else
        {
            System.out.print("User could not found the user");
        }
    }

    @FXML
    void OnFriendButtonPressed(MouseEvent event)
    {

    }

    @FXML
    void OnHomeButtonPressed(MouseEvent event)
    {

    }

    private void AddFriendToGrid(int id,String name,String imagePath,ArrayList<Friend> allFriends)
    {
        Friend friend = new Friend(id,name,imagePath);
        allFriends.add(friend);
    }

}
