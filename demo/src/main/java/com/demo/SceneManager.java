package com.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class SceneManager
{

    public static void SetScene(MouseEvent event, String sceneName) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneManager.class.getResource(sceneName));
        Parent root = loader.load();

        HelloController controller = loader.getController();
        controller.SetHelloControllerData();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
