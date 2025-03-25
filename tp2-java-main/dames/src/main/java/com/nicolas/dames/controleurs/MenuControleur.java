package com.nicolas.dames.controleurs;


import com.nicolas.dames.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;



public class MenuControleur {

    @FXML
    public Label menuLabel;

    public void changerSceneAccueil(ActionEvent actionEvent) throws IOException {

        FXMLLoader menuLoader = new FXMLLoader(MainApplication.class.getResource("accueil.fxml"));
        Stage stage;
        stage = (Stage) menuLabel.getScene().getWindow();
        stage.setScene(new Scene(menuLoader.load()));
        stage.show();
    }

    public void changerSceneRegles(ActionEvent actionEvent) throws IOException {
        FXMLLoader menuLoader = new FXMLLoader(MainApplication.class.getResource("regles.fxml"));
        Stage stage;
        stage = (Stage) menuLabel.getScene().getWindow();
        stage.setScene(new Scene(menuLoader.load()));
        stage.show();
    }

    public void changerSceneJouer(ActionEvent actionEvent) throws IOException {
        FXMLLoader menuLoader = new FXMLLoader(MainApplication.class.getResource("joueurPartie.fxml"));
        Stage stage;
        stage = (Stage) menuLabel.getScene().getWindow();
        stage.setScene(new Scene(menuLoader.load()));

        stage.show();
    }

    public static void chargerMenu(String nomFichier, BorderPane refBorderPane) throws IOException {
        FXMLLoader menuLoader = new FXMLLoader(MainApplication.class.getResource(nomFichier));
        VBox loadedMenu = menuLoader.load();
        refBorderPane.setLeft(loadedMenu);
    }

    public void quitter(ActionEvent actionEvent) throws IOException {
        FXMLLoader menuLoader = new FXMLLoader(MainApplication.class.getResource("confirmation.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(menuLoader.load()));

        stage.setTitle("Confirmation utilisateur");

        //Forcer le focus sur cette fenêtre
        stage.initOwner((Stage) menuLabel.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }
}
