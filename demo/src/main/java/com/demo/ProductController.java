package com.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductController implements Initializable {

    @FXML
    private Label description;

    @FXML
    private Label gameName;

    @FXML
    private Button homeButton;

    @FXML
    private HBox homeHBox;

    @FXML
    private Line homeLine;

    @FXML
    private ImageView img;

    @FXML
    private Label price;

    @FXML
    private Button profileButton;

    @FXML
    private HBox profileHBox;

    @FXML
    private Line profileLine;

    @FXML
    private Label release;

    @FXML
    private Button buyButton;

    BaseGame game;

    @FXML
    private ImageView ss1;

    @FXML
    private ImageView ss2;

    @FXML
    private ImageView ss3;

    private int gameID;

    private Statement st;
    private Connection conn;

    boolean bFromProfile;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final String urll = "jdbc:mysql://localhost:3306/gameshop";
        final String usernamee = "root";
        final String passwordd = "";

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn  = DriverManager.getConnection(urll,usernamee,passwordd);
            st = conn.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        String sql = "select * from user_game where game_id =" + 1;

            try {
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                ResultSet set = preparedStatement.executeQuery();
            } catch (SQLException e) {
                throw new RuntimeException("qwe");
            }
    }

    public void InitializeData(int id,String name, String price, String releaseDate, String gameDescription,
                               ArrayList<String> screenshots)
    {
        gameID = id;
        gameName.setText(name);
        this.price.setText(price);
        release.setText(releaseDate);
        description.setText(gameDescription);
        this.bFromProfile = bFromProfile;

        /*Image img1 = new Image(screenshots.get(0));
        ss1.setImage(img1);

        Image img2 = new Image(screenshots.get(1));
        ss2.setImage(img2);

        Image img3 = new Image(screenshots.get(2));
        ss3.setImage(img3);*/
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void OnHomeButtonPressed(MouseEvent event) throws IOException {
        //Load home page
        Test.SetIsInProfilePage(false);
        FXMLLoader load = new FXMLLoader();

        load.setLocation(getClass().getResource("hello-view.fxml"));
        root = load.load();

        HelloController controller = load.getController();
        controller.SetHelloControllerData(false);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void OnProfileButtonPressed(MouseEvent event) throws IOException {
        Test.SetIsInProfilePage(true);

        FXMLLoader load = new FXMLLoader();
        load.setLocation(getClass().getResource("profile.fxml"));
        root = load.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void buy(MouseEvent event) throws SQLException
    {

        //Check if user has the game

        //Check if user has enough coins to buy the game
        //If true,insert the game info into db
        if(User.GetAmount() < Double.valueOf(price.getText()))
        {
            System.out.println("User doesnt have enough money,basically hes broke");
            return;
        }

        User.SpendMoney(Double.valueOf(price.getText()));
        System.out.println(User.GetAmount());
        final int userID = User.GetUserID();
        System.out.println("User id is:" +userID);
        System.out.println("Game id is:" +gameID);

        ResultSet result = st.executeQuery("select * from user_game where game_id=" + gameID);
        if(result.next())
        {
            ResultSet resultt = st.executeQuery("select * from user_game where user_id=" + userID);
            if(resultt.next())
            {
                System.out.println("Player already owns the game");
                return;
            }
        }

        String sql ="INSERT INTO user_game (user_id, game_id) VALUES (?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1,userID);
        preparedStatement.setInt(2,gameID);
        preparedStatement.executeUpdate();
    }

    @FXML
    void BuyButtonHovered(MouseEvent event) {

    }
}


