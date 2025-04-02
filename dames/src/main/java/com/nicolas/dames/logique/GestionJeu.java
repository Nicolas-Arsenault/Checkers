package com.nicolas.dames.logique;

import javafx.scene.layout.GridPane;

/**
 * @classe GestionJeu.
 * @visibilite publique (public).
 * @description Classe gérant le déroulement du jeu.
 */
public class GestionJeu {
    public Jeu nouveauJeu;

    /**
     * @methode commencerJeu.
     * @visibilite publique (public).
     * @description Méthode pour initialiser le jeu.
     * @param  damierRef Référence au GridPane du damier.
     */
    public void commencerJeu(GridPane damierRef) {
        //Instanciation du jeu
        nouveauJeu = new Jeu(damierRef);

        //Dessiner les pions du jeu
        nouveauJeu.dessinerJeu();
    }

    /**
     * @methode finDeTourJoueur.
     * @visibilite publique (public).
     * @description Méthode pour finir le tour du joueur et commencer celui de l'IA
     */
    public void finDeTourJoueur() {

        //Changer de tour.
        nouveauJeu.changerTour();

        //Jouer le tour de l'IA
        if (!nouveauJeu.tourJoueur) {
            jouerTourIA();
        }
    }

    /**
     * @methode jouerTourIA.
     * @visibilite privée (private).
     * @description Méthode pour jouer le tour de l'IA et changer de tour.
     */
    private void jouerTourIA() {

        //Commencer le tour de l'IA jusqu'à ce qu'il soit fini
        boolean tourTermine = nouveauJeu.tourDeLEnemi();
        if (tourTermine) {

            //Lorsqu'il est fini, changer de tour.
            nouveauJeu.changerTour();
        }
    }
}