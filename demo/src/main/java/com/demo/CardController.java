package com.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class CardController {
    @FXML
    private ImageView gameImg;

    @FXML
    private Label gameName;

    @FXML
    private Label gamePrice;
    private String gameReleaseDate;
    public void SetData(BaseGame game)
    {
        InputStream is = getClass().getResourceAsStream("..\\..\\images\\Icons\\New Project.jpg");
        if(is != null)
        {
            Image img = new Image(is);
            gameImg.setImage(img);
        }
        gameName.setText(game.GetGameName());
        String priceToString = Double.toString(game.GetGamePrice());
        gamePrice.setText(priceToString);
        gameReleaseDate = game.GetGameReleaseDate();
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

        int a = User.GetAmount();

        ProductController productController = load.getController();
        productController.InitializeData(Integer.toString(a),gameImg.getImage(),gameName.getText(),gamePrice.getText(),gameReleaseDate);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
