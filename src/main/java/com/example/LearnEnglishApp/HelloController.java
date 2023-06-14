package com.example.LearnEnglishApp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Button StartButton;
    @FXML
    void initialize(){
        StartButton.setOnAction(event ->{
            StartButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("basic-view.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
           }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Learn English words");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });
    }
}