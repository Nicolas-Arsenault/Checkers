package com.nicolas.dames.controleurs;

import com.nicolas.dames.MainApplication;
import com.nicolas.dames.logique.GestionJeu;
import com.nicolas.dames.logique.Jeu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @classe JeuControleur.
 * @visibilite publique (public).
 * @description Fait le lien entre le visuel du jeu et la logique du jeu.
 */
public class JouerControleur {

    @FXML
    public Label gagnantTexte = null;
    public GridPane grille; //Damier

    /*Conteneneur de la grille etc...*/
    public BorderPane jouerBorderPane;

    /*Conteneurs des pions capturés*/
    public VBox captureEnnemiVBox;
    public VBox captureJoueurVBox;

    //Liste des pions mangés
    private List<ImageView> noirImageViews = new ArrayList<>();
    private List<ImageView> blancImageViews = new ArrayList<>();

    //CheckBox pour afficher les pions mangés
    public CheckBox checkBoxPions;

    //Comtpe des pions mangés
    int pionsNoirCapture = 0;
    int pionsBlancCapture = 0;

    //Coordonnées de sélection avec MouseClick
    int coordXAvant;
    int coordYAvant;

    //Savoir si un pion est déjà sélectionné ou non
    public static boolean estSelectionnee = false;

    //Création du jeu
    public GestionJeu gestionJeu = new GestionJeu();

    /**
     * @methode initialize.
     * @visibilite publique (public).
     * @description Méthode pour initialiser le jeu et un "mouse listener" pour savoir la case cliquée.
     */
    public void initialize() {

        //Commencer le jeu
        gestionJeu.commencerJeu(grille);

        //Reinitialiser le jeu "clean up"
        reinitJeu();
        gestionJeu.nouveauJeu.updateAllImages();
        gestionJeu.nouveauJeu.setController(this); //Donner une référence de l'instance de ce controlleur au jeu.

        /*Ajouter le "mouse listenener"*/
        grille.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {

            /*Extraire l'indice Ligne et l'indice colonne qu'on vient de cliquer dessus*/
            Node nodeCliquee = mouseEvent.getPickResult().getIntersectedNode();
            Integer colindex = GridPane.getColumnIndex(nodeCliquee);
            Integer ligneIndex = GridPane.getRowIndex(nodeCliquee);

            //Si on a cliqué sur une case contenant notre pion, enregistrer son index et effectuer la sélection*/
            if(gestionJeu.nouveauJeu.extractionPion(Jeu.extraireCoordDeXY(ligneIndex,colindex),false) != null
                && !estSelectionnee && gestionJeu.nouveauJeu.tourJoueur && !gestionJeu.nouveauJeu.finPartie)
            {
                gestionJeu.nouveauJeu.effectuerSelection(ligneIndex,colindex); //Effectuer la sélection du pion cliqué
                estSelectionnee = true; //Indiquer qu'il est sélectionné

                //Enregistrer les coordonnées sélectionnées
                coordXAvant = ligneIndex;
                coordYAvant = colindex;

                //Vérifier si la partie est finie
                finirPartie();
            }
            else if(estSelectionnee && gestionJeu.nouveauJeu.tourJoueur && !gestionJeu.nouveauJeu.finPartie)
            {
                //Si le pion est selectionnée et la personne veut faire un déplacement valide, effectuer ce déplacement
                if(gestionJeu.nouveauJeu.estDeplacementValide((Jeu.extraireCoordDeXY(coordXAvant,coordYAvant)),
                    Jeu.extraireCoordDeXY(ligneIndex,colindex)))
                {
                    //Mettre à jour la position du pion déplacé.
                    gestionJeu.nouveauJeu.mettreAJourPositionPions(
                            Jeu.extraireCoordDeXY(coordXAvant,coordYAvant),
                            Jeu.extraireCoordDeXY(ligneIndex,colindex),
                            gestionJeu.nouveauJeu.extractionPion(Jeu.extraireCoordDeXY(coordXAvant,coordYAvant),false)
                    );

                    estSelectionnee = false; //Le pion n'est plus sélectionné

                    //Finir le tour et canceller la sélection
                    gestionJeu.finDeTourJoueur();
                    gestionJeu.nouveauJeu.cancellerSelection(coordXAvant,coordYAvant);

                    //Mettre à jour les images déplacées
                    gestionJeu.nouveauJeu.updateAllImages();

                    //Vérifier si la partie est finie
                    finirPartie();
                }
                else if(gestionJeu.nouveauJeu.tourJoueur) //Si le joueur clique n'importe où, canceller sa sélection
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
        });
    }


    /**
     * @methode finirPartie.
     * @visibilite privée (private).
     * @description Méthode pour vérifier si la partie est finie et afficher le gagnant(e) en conséquence
     */
    private void finirPartie() {

        if (gestionJeu.nouveauJeu.estFinDePartie())
        {
            String gagnant = gestionJeu.nouveauJeu.getGagnant(); //Savoir qui a gagné.

            gagnantTexte.setText("Gagnant: " + gagnant); //Mettre à jour le texte du gagnant de la pratie.

            // Changer la couleur du texte en fonction du gagnant.
            if (gagnant.equals("noirs")) {
                gagnantTexte.setTextFill(Color.BLACK);
            } else {
                gagnantTexte.setTextFill(Color.WHITE);
            }
            return;
        }
        //Réinitialiser le texte.
        gagnantTexte.setText("");
    }

    /**
     * @methode recommencerPartie.
     * @visibilite publique (public).
     * @description Méthode vérifie si le bouton "recommencer la partie" a été cliqué et confirmé
     */
    public void recommencerPartie() throws IOException {
        if(MenuControleur.montrerConfirmationFenetre("recommencer la partie."))
        {
            //Si confirmé, reéinitialiser le jeu".
            reinitJeu();

            //Écraser le fxml déjà chargé. (N'utilise pas plus de ressources).
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("jouerPartie.fxml"));
            Pane pane = loader.load();
            BorderPane mainBorderPane = accederMenuBorderPane();
            mainBorderPane.setCenter(pane);
        }
    }

    /**
     * @methode reinitJeu.
     * @visibilite privée (private).
     * @description Méthode pour initialiser les pions sur le plateau.
     */
    private void reinitJeu() {
        gestionJeu.nouveauJeu.reinitialiserJeu();
        gagnantTexte.setText("");
        gestionJeu.nouveauJeu.dessinerJeu();
    }

    /**
     * @methode initialisationJeu.
     * @visibilite privée (private).
     * @return Le BorderPane de la scène principale "menu".
     * @description Méthode pour accéder au BorderPane du menu principal.
     */
    private BorderPane accederMenuBorderPane()
    {
        Node node = jouerBorderPane; //Référence au BorderPane du jeu

        //Get parent jusqu'à ce qu'on soit à la racine
        while (node.getParent() != null){
            node = node.getParent();
        }
        //Extraire la racine et retourner ce BorderPane
        Node parentNode = node;
        return (BorderPane) parentNode;
    }

    /**
     * @methode incrementerCaptureNoir.
     * @visibilite publique (public).
     * @description Incrémente et ajoute à la liste d'image les pions noirs capturés
     */
    public void incrementerCaptureNoir(boolean estDame) {
        // Créer l'image en fonction de si c'est une dame ou non.
        Image image;
        if(estDame)
        {
            image = new Image(Jeu.class.getResourceAsStream("/images/noir-dame.png"));
        }
        else
        {
            image = new Image(Jeu.class.getResourceAsStream("/images/noir-normal.png"));
        }

        //Créer la View
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(35);
        imageView.setFitWidth(35);
        imageView.setTranslateY(10);

        pionsNoirCapture++; // Incrémenter le compte
        noirImageViews.add(imageView); // Ajouter l'image dans la liste

        // Mettre à jour l'affichage des pions capturés
        afficherCaptureNoir();
    }

    /**
     * @methode incrementerCaptureBlanc.
     * @visibilite publique (public).
     * @description Incrémente et ajoute à la liste d'image les pions blancs capturés
     */
    public void incrementerCaptureBlanc(boolean estDame) {
        // Créer l'image en fonction de si c'est une dame ou non.
        Image image;

        if(estDame)
        {
            image = new Image(Jeu.class.getResourceAsStream("/images/blanc-dame.png"));
        }
        else
        {
            image = new Image(Jeu.class.getResourceAsStream("/images/blanc-normal.png"));
        }

        //Créer la View
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(35);
        imageView.setFitWidth(35);
        imageView.setTranslateY(10);
        pionsBlancCapture++; // Incrémenter le compte
        blancImageViews.add(imageView); // Ajouter l'image à la liste

        // Mettre à jour l'affichage
        afficherCaptureBlanc();
    }

    /**
     * @methode afficherCaptureNoir.
     * @visibilite privée (private).
     * @description Méthode pour afficher la capture de pions noirs.
     */
    private void afficherCaptureNoir() {

        //Vérifie si la case "Cacher les pions" est coché
        if (checkBoxPions.isSelected()) {
            captureEnnemiVBox.setVisible(true);

            captureEnnemiVBox.getChildren().clear(); // Effacer toutes les images de la Vbox

            //ajouter toutes les images dans la VBox
            for (ImageView imageView : noirImageViews) {
                captureEnnemiVBox.getChildren().add(imageView);
            }
        } else {
            // Si la case n'est pas cochée, effacer toutes les images.
            captureEnnemiVBox.getChildren().clear();
        }
    }

    /**
     * @methode afficherCaptures.
     * @visibilite publique (public).
     * @description Méthode appelée lors d'un "MouseClick" sur la case "Cacher les pions" pour afficher tous les pions capturés.
     */
    public void afficherCaptures()
    {
        afficherCaptureBlanc();
        afficherCaptureNoir();
    }

    /**
     * @methode afficherCaptureBlanc.
     * @visibilite privée (private).
     * @description Méthode pour afficher la capture de pions blancs.
     */
    private void afficherCaptureBlanc() {

        //Vérifie si la case "Cacher les pions" est coché
        if (checkBoxPions.isSelected()) {
            captureJoueurVBox.setVisible(true);

            captureJoueurVBox.getChildren().clear(); // Effacer toutes les images de la Vbox

            //ajouter toutes les images dans la VBox
            for (ImageView imageView : blancImageViews) {
                captureJoueurVBox.getChildren().add(imageView);
            }
        } else {
            // Si la case n'est pas cochée, effacer toutes les images.
            captureJoueurVBox.getChildren().clear();
        }
    }

    /**
     * @methode quitterPartie.
     * @visibilite publique (public).
     * @description Méthode pour retourner au menu et réinitialiser le jeu.
     */
    public void quitterPartie() throws IOException {

        //Si l'utilisateur clique "Quitter la partie" et confirme.
        if(MenuControleur.montrerConfirmationFenetre("quitter la partie."))
        {
            //Réintialiser le jeu
            reinitJeu();

            //Changer de scène.
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("accueil.fxml"));
            Pane pane = loader.load();
            BorderPane mainBorderPane = accederMenuBorderPane();
            mainBorderPane.setCenter(pane);
        }
    }

}