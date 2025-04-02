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

/**
 * @classe MenuControleur.
 * @visibilite publique (public).
 * @description Fait le menu graphique et la logique de traitement de celui-ci.
 */
public class MenuControleur {

    @FXML
    public Label menuLabel; //Texte "Menu"
    public BorderPane panePourScene; //Le BorderPane principal.

    /**
     * @methode initialize.
     * @visibilite publique (public).
     * @description Méthode pour initialiser l'accueil et l'aspect graphique du BorderPane principal.
     */
    public void initialize() throws IOException {
        // Image d'arrière-plan
        Image arrierePlanImage = new Image(getClass().getResource("/images/background.jpg").toExternalForm());
        changerScene("accueil.fxml");

        // Fair ene sorte que l'arrière-plan couvre tout
        BackgroundSize tailleArrierePlan = new BackgroundSize(
                BackgroundSize.AUTO, // Largeur
                BackgroundSize.AUTO, // Hauteur
                true,  // Étirement horizontal
                true,  // Étirement vertical
                false, // Aspect ratio
                true   // Prends toute l'espace disponible
        );

        //Mettre l'arrière-plan à la scène
        panePourScene.setBackground(new Background(new BackgroundImage(
                arrierePlanImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                tailleArrierePlan
        )));
    }


    /**
     * @methode changerScene.
     * @visibilite privée (private).
     * @description Méthode pour changer les scènes.
     * @param fxmlFile nom du fichier fxml à charger dans le milieu du BorderPane principal.
     */
    private void changerScene(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(fxmlFile));
        Pane pane = loader.load();
        panePourScene.setCenter(pane);

    }

    /**
     * @methode changerSceneAccueil.
     * @visibilite publique (public).
     * @description Méthode changeant la scène principale à celle de l'accueil. Est appelée lors d'un "mouseClick" sur le bouton "Accueil".
     */
    public void changerSceneAccueil() throws IOException {
        changerScene("accueil.fxml");
    }

    /**
     * @methode changerSceneRegles.
     * @visibilite publique (public).
     * @description Méthode changeant la scène principale à celle des règles. Est appelée lors d'un "mouseClick" sur le bouton "Règles"
     */
    public void changerSceneRegles(ActionEvent actionEvent) throws IOException {
        changerScene("regles.fxml");
    }

    /**
     * @methode changerSceneJouer.
     * @visibilite publique (public).
     * @description Méthode changeant la scène principale à celle de la partie. Est appelée lors d'un "mouseClick" sur le bouton "Jouer une partie"
     */
    public void changerSceneJouer(ActionEvent actionEvent) throws IOException {
        changerScene("jouerPartie.fxml");

    }

    /**
     * @methode quitter.
     * @visibilite publique (public).
     * @description Méthode pour quitter le programme. Est appelée lors d'un "mouseClick" sur le bouton "Quitter".
     */
    public void quitter(ActionEvent actionEvent) throws IOException {

        //Si la fenêtre de confirmation a été validée
        if(montrerConfirmationFenetre("quitter l'application."))
        {
            //Fermer le stage.
            Stage stage = (Stage) panePourScene.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * @methode montrerConfirmationFenetre.
     * @visibilite publique (public).
     * @description Méthode pour montrer une fenêtre de confirmation et de lire la confirmation utilisateure<.
     * @param action Nom de l'action à confirmer.
     */
    public static boolean montrerConfirmationFenetre(String action) throws IOException {

        Alert alerte = new Alert(Alert.AlertType.WARNING); //Créer une nouvelle alerte

        /*Créer les boutons de cette alerte*/
        ButtonType confirmer = new ButtonType("Confirmer", ButtonBar.ButtonData.OK_DONE);
        ButtonType annuler = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

        //Propriétées de la fenêtre
        alerte.setTitle("Confirmation utilisateur");
        alerte.setHeaderText("Veuillez confirmer l'action suivante : " + action);

        //Ajouter les boutons à l'alerte
        alerte.getButtonTypes().setAll(confirmer, annuler);

        //Afficher l'alerte et attendre une action de utilisateur.
        return alerte.showAndWait().get() == confirmer;
    }
}
