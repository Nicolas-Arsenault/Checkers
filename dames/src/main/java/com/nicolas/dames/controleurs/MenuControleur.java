package com.nicolas.dames.controleurs;

import com.nicolas.dames.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;


public class MenuControleur {

    @FXML
    public Label menuLabel;
    public BorderPane panePourScene;

    public void initialize() throws IOException {
        // Set the background image
        Image arrierePlanImage = new Image(getClass().getResource("/images/background.jpg").toExternalForm());
        changerScene("accueil.fxml");

        // Adjust BackgroundSize to make the background cover the entire pane
        // The "cover" value ensures the background stretches to cover the whole pane
        BackgroundSize tailleArrierePlan = new BackgroundSize(
                BackgroundSize.AUTO, // Width auto
                BackgroundSize.AUTO, // Height auto
                true,  // Stretch to cover horizontally
                true,  // Stretch to cover vertically
                false, // No need to preserve aspect ratio
                true   // Make sure it fills the entire area
        );

        // Set the background image with a proper background size to cover the whole area
        panePourScene.setBackground(new Background(new BackgroundImage(
                arrierePlanImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, // Centers the image while scaling
                tailleArrierePlan
        )));
    }


    private void changerScene(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(fxmlFile));
        Pane pane = loader.load();
        panePourScene.setCenter(pane);

    }

    public void changerSceneAccueil() throws IOException {
        changerScene("accueil.fxml");
    }

    public void changerSceneRegles(ActionEvent actionEvent) throws IOException {
        changerScene("regles.fxml");
    }

    public void changerSceneJouer(ActionEvent actionEvent) throws IOException {
        changerScene("jouerPartie.fxml");

    }

    public void quitter(ActionEvent actionEvent) throws IOException {
        if(montrerConfirmationFenetre("quitter l'application."))
        {
            Stage stage = (Stage) panePourScene.getScene().getWindow();
            stage.close();
        }
    }

    public static boolean montrerConfirmationFenetre(String action) throws IOException {

        Alert alerte = new Alert(Alert.AlertType.WARNING);
        ButtonType confirmer = new ButtonType("Confirmer", ButtonBar.ButtonData.OK_DONE);
        ButtonType annuler = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

        alerte.setTitle("Confirmation utilisateur");
        alerte.setHeaderText("Veuillez confirmer l'action suivante : " + action);

        alerte.getButtonTypes().setAll(confirmer, annuler);


        return alerte.showAndWait().get() == confirmer;
    }
}
