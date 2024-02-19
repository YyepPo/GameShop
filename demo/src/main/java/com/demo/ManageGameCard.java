package com.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ManageGameCard {

    @FXML
    private Label gameName;
    @FXML
    private ImageView gameImage;

    public void SetData(GameCard game)
    {
        gameName.setText(game.getName());
    }

    @FXML
    void OnDeleteGameClicked(MouseEvent event) {
        System.out.println("Delete game");
    }

    @FXML
    void OnEditGameClicked(MouseEvent event) {
        System.out.println("Edit game");
    }

}