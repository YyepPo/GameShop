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

    @FXML
    private Label profileName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        profileName.setText(User.getUserName());
    }

    private User user;
    public void InitializeData(User user)
    {
        this.user = user;
    }

    @FXML
    void buy(MouseEvent event)
    {
        User.Test(20);
        test.setText(Integer.toString(User.GetAmount()));
    }

}
