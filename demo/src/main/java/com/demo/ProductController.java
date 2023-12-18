package com.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductController implements Initializable {

    @FXML
    private ImageView img;

    @FXML
    private Label name;

    @FXML
    private Label price;

    @FXML
    private Label release;

    @FXML
    private Label test;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }

    public void InitializeData(String test,Image image,String name,String price,String releaseDate)
    {
        img.setImage(image);
        this.name.setText(name);
        this.price.setText(price);
        release.setText(releaseDate);
        this.test.setText(test);
    }

    @FXML
    void buy(MouseEvent event)
    {
        User.Test(20);
        test.setText(Integer.toString(User.GetAmount()));
    }

}
