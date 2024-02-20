package com.demo;

import com.sun.javafx.image.impl.ByteIndexed;
import com.sun.javafx.scene.SceneEventDispatcher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;

import java.io.IOException;

class  SideButtonsInfo
{
    @FXML
    private Button adminButton;

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
    private HBox profileHBox1;

    @FXML
    private Line profileLine;

    @FXML
    private Line profileLine1;
}

public class AdminPanelController {

    SideButtonsInfo SideButtonsInfo;

    @FXML
    void OnAdminButtonPressed(MouseEvent event) throws IOException {
        SceneManager.LoadScene(event,getClass().getResource("admin-panel.fxml"));
    }

    @FXML
    void OnHomeButtonPressed(MouseEvent event) throws IOException {
        SceneManager.LoadScene(event,getClass().getResource("hello-view.fxml"));
    }

    @FXML
    void OnProfileButtonPressed(MouseEvent event) throws IOException {
        SceneManager.LoadScene(event,getClass().getResource("profile.fxml"));
    }

    @FXML
    void OnManageUsersClicked(MouseEvent event) throws IOException {
        SceneManager.LoadScene(event,getClass().getResource("ManageUsers.fxml"));
    }

    @FXML
    void OnManageGamesClicked(MouseEvent event) throws IOException {
        SceneManager.LoadScene(event,getClass().getResource("ManageGames.fxml"));
    }
}
