package com.nicolas.dames;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("accueil.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);

        stage.setMinWidth(800);   // Minimum width 800
        stage.setMinHeight(600);  // Minimum height 600
        stage.setMaxWidth(1200);  // Maximum width 1200
        stage.setMaxHeight(700);  // Maximum height 700
        stage.isResizable();


        stage.setTitle("Jeu de dames - Nicolas Arsenault");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}