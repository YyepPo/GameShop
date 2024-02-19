package com.demo;

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
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CardController implements Initializable {
    @FXML
    private ImageView gameImg;

    @FXML
    private Label gameName;

    @FXML
    private Label gamePrice;
    private String gameReleaseDate;
    private String gameDescription;
    private int gameID;
    BaseGame game;

    private ArrayList<String> screenshots;

    private int AgeRestriction;
    private String Cpu;
    private int Ram;
    private int Storage;
    private String Card;

    @FXML
    private Label ageRestriction;
    @FXML
    private Label cpu;
    @FXML
    private Label ram;
    @FXML
    private Label storage;
    @FXML
    private Label card;

    boolean bIsFromProfile;
    public void SetData(VideoGame game,boolean bIsFromProfile)
    {
        this.game = game;
        InputStream is = getClass().getResourceAsStream("..\\..\\images\\Icons\\New Project.jpg");
        if(is != null)
        {
            Image img = new Image(is);
            gameImg.setImage(img);
        }
        gameID = game.GetGameID();
        gameName.setText(game.GetGameName());
        String priceToString = Double.toString(game.GetGamePrice());
        gamePrice.setText(priceToString);
        gameReleaseDate = game.GetGameReleaseDate();
        gameDescription = game.GetGameDesc();
        screenshots = game.GetScreenShots();
        AgeRestriction = game.GetAgeRestriction();
        Cpu = game.GetSystemRequirement().processor;
        Ram = game.GetSystemRequirement().memory;
        Storage = game.GetSystemRequirement().storage;
        Card = game.GetSystemRequirement().graphicsCard;
        this.bIsFromProfile = bIsFromProfile;
    }

    @FXML
    void productPressed(MouseEvent event) throws IOException
    {
        Stage stage;
        Scene scene;
        Parent root;

        //Check whether user clicked the product within the profile
        //If true load BoughtProduct.fxml where user can download the product
        //If false load Product.fxml where user can purchase the product
        FXMLLoader load = new FXMLLoader();
        if(Test.GetIsInProfilePage())
        {
            load.setLocation(getClass().getResource("BoughtProduct.fxml"));

            root = load.load();

            BoughtProductController boughtProductController = load.getController();
            boughtProductController.InitializeBoughtProductData(gameID,gameName.getText(),
                    gamePrice.getText(),gameReleaseDate,gameDescription,screenshots,AgeRestriction,Cpu,Ram,Storage,Card);

        }
        else
        {
            load.setLocation(getClass().getResource("Product.fxml"));

            root = load.load();

            ProductController productController = load.getController();
            productController.InitializeData(gameID,gameName.getText(),
                    gamePrice.getText(),gameReleaseDate,gameDescription,screenshots,AgeRestriction,Cpu,Ram,Storage,Card);

        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
