package com.nicolas.dames.logique;

import javafx.scene.layout.GridPane;

public class GestionJeu {
    public Jeu nouveauJeu;

    public void commencerJeu(GridPane damierRef) {
        nouveauJeu = new Jeu(damierRef);

        nouveauJeu.dessinerJeu();

        System.out.println("Game started. Initial turn: " +
                (nouveauJeu.tourJoueur ? "WHITE" : "BLACK"));

    }

    public void finDeTourJoueur() {

        System.out.println(nouveauJeu.tourJoueur);
        nouveauJeu.changerTour();

        if (nouveauJeu.tourJoueur == false) {
            System.out.println("Ending player turn");
            jouerTourIA();
        }
    }

    private void jouerTourIA() {
        System.out.println("Starting AI turn");
        boolean tourTermine = nouveauJeu.tourDeLEnemi();
        if (tourTermine) {

            if(nouveauJeu.aucunMouvementPossible(false))
            {
                nouveauJeu.finPartie = true;
                nouveauJeu.estFinDePartie();
            }
            
            System.out.println("AI turn completed");
            nouveauJeu.changerTour();
        }
    }
}
