package com.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FriendCardController {

    @FXML
    private ImageView friendImage;

    @FXML
    private Label friendName;
    private int friendID;
    Friend friend;

    public void SetData(Friend friend)
    {
        this.friend = friend;
        friendName.setText(friend.GetFriendName());
    }
}
