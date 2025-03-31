package com.nicolas.dames.controleurs;

import com.nicolas.dames.MainApplication;
import com.nicolas.dames.logique.GestionJeu;
import com.nicolas.dames.logique.Jeu;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JouerControleur {

    public static String gagnant;
    @FXML
    public Label gagnantTexte = null;
    public GridPane grille;
    public BorderPane jouerBorderPane;

    /*Captures*/
    public VBox captureEnnemiVBox;
    public VBox captureJoueurVBox;

    private List<ImageView> noirImageViews = new ArrayList<>();
    private List<ImageView> blancImageViews = new ArrayList<>();

    /*CheckBox afficher*/
    public CheckBox checkBoxNoir;
    public CheckBox checkBoxBlanc;

    int pionsNoirCapture = 0;
    int pionsBlancCapture = 0;

    int coordXAvant;
    int coordYAvant;
    public static boolean estSelectionnee = false;
    public GestionJeu gestionJeu = new GestionJeu();

    public void initialize() {

        gestionJeu.commencerJeu(grille);
        reinitJeu();
        gestionJeu.nouveauJeu.updateAllImages();
        gestionJeu.nouveauJeu.setController(this);

        grille.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                Node clickedNode = mouseEvent.getPickResult().getIntersectedNode();
                Integer colindex = GridPane.getColumnIndex(clickedNode);
                Integer rowIndex = GridPane.getRowIndex(clickedNode);

                if(gestionJeu.nouveauJeu.extractionPion(Jeu.extraireCoordDeXY(rowIndex,colindex),false) != null
                    && !estSelectionnee && gestionJeu.nouveauJeu.tourJoueur && !gestionJeu.nouveauJeu.finPartie)
                {
                    gestionJeu.nouveauJeu.effectuerSelection(rowIndex,colindex);
                    estSelectionnee = true;

                    coordXAvant = rowIndex;
                    coordYAvant = colindex;
                    finirPartie();
                }
                else if(estSelectionnee && gestionJeu.nouveauJeu.tourJoueur && !gestionJeu.nouveauJeu.finPartie)
                {
                    //si le pion est selectionnée et la personne veut faire un déplacement valide, effectuer ce déplacement
                    if(gestionJeu.nouveauJeu.estDeplacementValide((Jeu.extraireCoordDeXY(coordXAvant,coordYAvant)),
                        Jeu.extraireCoordDeXY(rowIndex,colindex)))
                    {
                        System.out.println("coord actu: " + Jeu.extraireCoordDeXY(coordXAvant,coordYAvant) + "coord deplace" + Jeu.extraireCoordDeXY(rowIndex,colindex));

                        gestionJeu.nouveauJeu.mettreAJourPositionPions(
                                Jeu.extraireCoordDeXY(coordXAvant,coordYAvant),
                                Jeu.extraireCoordDeXY(rowIndex,colindex),
                                gestionJeu.nouveauJeu.extractionPion(Jeu.extraireCoordDeXY(coordXAvant,coordYAvant),false)
                        );
                        estSelectionnee = false;

                        gestionJeu.finDeTourJoueur();
                        gestionJeu.nouveauJeu.cancellerSelection(coordXAvant,coordYAvant);

                        gestionJeu.nouveauJeu.updateAllImages();
                        finirPartie();
                    }
                    else if(gestionJeu.nouveauJeu.tourJoueur)
                    {
                        estSelectionnee = false;
                        gestionJeu.nouveauJeu.cancellerSelection(coordXAvant,coordYAvant);
                        finirPartie();
                    }
                    else
                    {
                        finirPartie();
                    }
                }

                System.out.println("Col : " + colindex + " Row : " + rowIndex);
            }
        });
    }


    public boolean finirPartie() {
        if (gestionJeu.nouveauJeu.estFinDePartie()) {
            gagnantTexte.setText("Gagnant: " + gestionJeu.nouveauJeu.getGagnant());
            return true;
        }
        gagnantTexte.setText("");
        return false;
    }


    public void recommencerPartie() throws IOException {
        if(MenuControleur.montrerConfirmationFenetre("recommencer la partie."))
        {
            reinitJeu();

            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("jouerPartie.fxml"));
            Pane pane = loader.load();
            BorderPane mainBorderPane = accederMenuBorderPane();
            mainBorderPane.setCenter(pane);
        }
    }

    private void reinitJeu() {
        gestionJeu.nouveauJeu.reinitialiserJeu();
        gagnantTexte.setText("");
        gestionJeu.nouveauJeu.dessinerJeu();
    }

    private BorderPane accederMenuBorderPane()
    {
        Node node = jouerBorderPane;
        while (node.getParent() != null){
            node = node.getParent();
        }
        Node parentNode = node;
        return (BorderPane) parentNode;


    }

    public void incrementerCaptureNoir(boolean estDame) {
        // Create the image view for a black piece
        Image image;
        if(estDame)
        {
            image = new Image(Jeu.class.getResourceAsStream("/images/noir-dame.png"));
        }
        else
        {
            image = new Image(Jeu.class.getResourceAsStream("/images/noir-normal.png"));
        }

        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(35);
        imageView.setFitWidth(35);
        imageView.setTranslateY(10);

        pionsNoirCapture++; // Increment the count
        noirImageViews.add(imageView); // Add the new image view to the list

        // Immediately update the VBox to reflect the new image
        afficherCaptureNoir();
    }

    public void incrementerCaptureBlanc(boolean estDame) {
        // Create the image view for a white piece
        Image image;

        if(estDame)
        {
            image = new Image(Jeu.class.getResourceAsStream("/images/blanc-dame.png"));
        }
        else
        {
            image = new Image(Jeu.class.getResourceAsStream("/images/blanc-normal.png"));
        }

        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(35);
        imageView.setFitWidth(35);
        imageView.setTranslateY(10);
        pionsBlancCapture++; // Increment the count
        blancImageViews.add(imageView); // Add the new image view to the list

        // Immediately update the VBox to reflect the new image
        afficherCaptureBlanc();
    }

    public void afficherCaptureNoir() {
        if (checkBoxNoir.isSelected()) {
            captureEnnemiVBox.setVisible(true);
            // Add all the ImageViews to the VBox
            captureEnnemiVBox.getChildren().clear(); // Clear any existing images first
            captureEnnemiVBox.getChildren().add(checkBoxNoir); // Re-add the checkbox if it's visible

            for (ImageView imageView : noirImageViews) {
                captureEnnemiVBox.getChildren().add(imageView); // Add the new image views
            }
        } else {
            // Remove all ImageViews from the VBox, but keep the checkbox
            captureEnnemiVBox.getChildren().clear();
            captureEnnemiVBox.getChildren().add(checkBoxNoir); // Keep the checkbox
        }
    }

    public void afficherCaptureBlanc() {
        if (checkBoxBlanc.isSelected()) {
            captureJoueurVBox.setVisible(true);
            // Add all the ImageViews to the VBox
            captureJoueurVBox.getChildren().clear(); // Clear any existing images first
            captureJoueurVBox.getChildren().add(checkBoxBlanc); // Re-add the checkbox if it's visible

            for (ImageView imageView : blancImageViews) {
                captureJoueurVBox.getChildren().add(imageView); // Add the new image views
            }
        } else {
            // Remove all ImageViews from the VBox, but keep the checkbox
            captureJoueurVBox.getChildren().clear();
            captureJoueurVBox.getChildren().add(checkBoxBlanc); // Keep the checkbox
        }
    }

    public void quitterPartie() throws IOException {
        if(MenuControleur.montrerConfirmationFenetre("quitter la partie."))
        {
            reinitJeu();

            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("accueil.fxml"));
            Pane pane = loader.load();
            BorderPane mainBorderPane = accederMenuBorderPane();
            mainBorderPane.setCenter(pane);
        }
    }

}