/**
 * @author Nicolas Arsenault
 * @programme dames_nico
 * @dateCreation 2025-03-30
 * @dateModification 2025-04-02
 * @descrpition Ce programme simule un jeu de dames, où l'utilisateur joue
 * contre un robot. Ce dernier fait des coups aléatoires. Les pions
 * peuvent bouger en diagonales et seulement en avançant. Les dames
 * peuvent reculer et avancer sur les diagonales. Pour manger un autre
 * pion, il faut que la case de la diagonale en sa direction soit libre.
 * En arrivant à l'autre bout du plateau, un pion est promu en dame.
 * La partie finie lorsqu'un joueur ne peut plus rien joueur ou lorsqu'il
 * n'y a plus aucune pièces.
 */

package com.nicolas.dames;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @classe MainApplication.
 * @visibilite publique (public)
 * @description Classe principale, qui est le point d'entrée du programme.
 */
public class MainApplication extends Application {
    @Override

    /**
     * @methode start.
     * @visibilite publique (public).
     * @description Méthode créer le stage et la première scène
     * @param stage reçoit le stage principal
     */
    public void start(Stage stage) throws IOException {

        //Charger menu.fxml (scène)
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);

        stage.setMinWidth(800);   // Min largeur 800
        stage.setMinHeight(600);  // Min hauteur 600
        stage.setMaxWidth(1200);  // Maximum largeur 1200
        stage.setMaxHeight(700);  // Maximum hauteur 700
        stage.isResizable();

        //Donner un titre au stage
        stage.setTitle("Jeu de dames - Nicolas Arsenault");
        //Assigner la scène au stage
        stage.setScene(scene);
        //Afficher le stage
        stage.show();

    }

    /**
     * @methode main.
     * @visibilite publique (public).
     * @description Méthode qui est le point d'entrée au programme. Lance la création du stage.
     * @param args arguments en ligne de commande reçus...
     */
    public static void main(String[] args) {
        launch();
    }
}