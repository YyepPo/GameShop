package com.demo.ManageUser;

import com.demo.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;

import java.io.IOException;

public class EditUserController {

    @FXML
    private Button adminButton;

    @FXML
    private TextField dollarAmountText;

    @FXML
    private TextField emailText;

    @FXML
    private TextField firstNameText;

    @FXML
    private Button homeButton;

    @FXML
    private HBox homeHBox;

    @FXML
    private Line homeLine;

    @FXML
    private TextField lastNameText;

    @FXML
    private TextField passwordText;

    @FXML
    private Button profileButton;

    @FXML
    private HBox profileHBox;

    @FXML
    private HBox profileHBox1;

    @FXML
    private TextField profileImagePath;

    @FXML
    private Line profileLine;

    @FXML
    private Line profileLine1;

    @FXML
    private TextField usernameText;

    private int userID;
    public void SetData(UserCard userCard)
    {
        userID = userCard.getId();
        usernameText.setText(userCard.getUsername());
        passwordText.setText(userCard.getPassword());
        emailText.setText(userCard.getEmail());
        firstNameText.setText(userCard.getFirstName());
        lastNameText.setText(userCard.getLastName());
        profileImagePath.setText(userCard.getImage());
        dollarAmountText.setText(String.valueOf(userCard.getDollarAmount()));
    }


    @FXML
    void OnUpdateUserClicked(MouseEvent event) {

    }

    ///
    ///Event Listeners
    ///
    @FXML
    void OnAdminButtonPressed(MouseEvent event) throws IOException {
        SceneManager.LoadScene(event,getClass().getResource("../admin-panel.fxml"));
    }

    @FXML
    void OnHomeButtonPressed(MouseEvent event) throws IOException {
        SceneManager.LoadScene(event,getClass().getResource("../hello-view.fxml"));
    }

    @FXML
    void OnProfileButtonPressed(MouseEvent event) throws IOException {
        SceneManager.LoadScene(event,getClass().getResource("../profile.fxml"));
    }

}
