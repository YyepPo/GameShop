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

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class BoughtProductController implements Initializable {
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

    //private SceneManager sceneManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void InitializeBoughtProductData(int id,String name, String price, String releaseDate, String gameDescription,
                                            ArrayList<String> screenshots,int ageRestrcition,String cpu,int ram,int storage,String card)
    {
        gameID = id;
        gameName.setText(name);
        this.price.setText(price);
        release.setText(releaseDate);
        description.setText(gameDescription);
        this.ageRestriction.setText(String.valueOf(ageRestrcition));
        this.cpu.setText(cpu);
        this.ram.setText(String.valueOf(ram) + "GB");
        this.storage.setText(String.valueOf(storage) + "GB");
        this.card.setText(card);

        /*Image img1 = new Image(screenshots.get(0));
        ss1.setImage(img1);

        Image img2 = new Image(screenshots.get(1));
        ss2.setImage(img2);

        Image img3 = new Image(screenshots.get(2));
        ss3.setImage(img3);*/
    }

    @FXML
    void OnHomeButtonPressed(MouseEvent event) throws IOException {
        //Load hello-view.fxml scene
        Test.SetIsInProfilePage(false);
        SceneManager.LoadScene(event,getClass().getResource("hello-view.fxml"));
    }

    @FXML
    void OnProfileButtonPressed(MouseEvent event) throws IOException {
        //Load profile.fxml scene
        Test.SetIsInProfilePage(true);
        SceneManager.LoadScene(event,getClass().getResource("profile.fxml"));
    }

    @FXML
    void OnDownloadButtonPressed(MouseEvent event)
    {
        //Download a file from given url
        String fileUrl = "file:///D:/Viti2/ShkencaKompjuterike2/Projekti/Icons/DownloadTest.pdf";
        String destinationPath = "Desktop";

        try {
            downloadFile(fileUrl, destinationPath);
            System.out.println("File downloaded successfully.");
        } catch (IOException e) {
            System.err.println("Error downloading the file: " + e.getMessage());
        }
    }

    private static void downloadFile(String fileUrl, String destinationPath) throws IOException {
        URL url = new URL(fileUrl);
        try (InputStream in = new BufferedInputStream(url.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(destinationPath)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer, 0, buffer.length)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
        }
    }
    //https://docs.google.com/document/d/1vUNDtNRBVzRDbZ46-0he_P4TVPgvTdT2Wc5jtROBO9U/edit?usp=drive_link
}
