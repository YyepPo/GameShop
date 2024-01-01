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

    boolean bDownloadGame = false;
    public void SetData(BaseGame game,boolean bIsFromProfile)
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
    }

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    void productPressed(MouseEvent event) throws IOException
    {
        FXMLLoader load = new FXMLLoader();
        load.setLocation(getClass().getResource("Product.fxml"));

        root = load.load();

        ProductController productController = load.getController();
        productController.InitializeData(gameID,gameName.getText(),
                gamePrice.getText(),gameReleaseDate,gameDescription,screenshots,bDownloadGame);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        if(bDownloadGame){}
    }
}
