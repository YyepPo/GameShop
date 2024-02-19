package com.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

///
/// Singleton pattern
///
public class SceneManager {
    private static SceneManager instance;

    // Private constructor to prevent instantiation from outside
    SceneManager() {
    }

    public static SceneManager getInstance() {
        // Lazy initialization: create the instance if it doesn't exist
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    private static Stage stage;
    private static Scene scene;
    private static Parent root;
    public static void LoadScene(MouseEvent event, URL url) throws IOException {
        FXMLLoader load = new FXMLLoader();
        load.setLocation(url);
        root = load.load();
        //getClass().getResource(sceneName)
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}