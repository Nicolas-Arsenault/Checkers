package com.nicolas.dames.logique;

/**
 * @classe Pion.
 * @visibilite défaut (default).
 * @description Représente un pion dans le jeu de dame.
 */
public class Pion
{
    /** Identifie si le pion est une dame. */
    public boolean estDame = false;
    /** Identifie si le pion appartient au robot. */
    public boolean estEnnemi;


    /**
     * @constructeur Pion.
     * @visibilite publique (public).
     * @param estEnnemi Indique si le pion appartient au robot, en format boolean.
     */
    public Pion(boolean estEnnemi)
    {
        this.estEnnemi = estEnnemi;
    }
}
