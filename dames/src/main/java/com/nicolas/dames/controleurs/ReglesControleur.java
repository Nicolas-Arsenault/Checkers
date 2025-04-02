package com.nicolas.dames.controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.IOException;

/**
 * @classe ReglesControleur.
 * @visibilite publique (public).
 * @description Classe faisant le lien entre la partie graphique des règles et la logique de traitement.
 */
public class ReglesControleur {

    @FXML
    public TextArea reglesTexteArea; //Endroit où les règles sont décrites.


    /**
     * @methode initialize.
     * @visibilite publique (public).
     * @description Méthode appelée lors du changement de scène. Rend le texte non modifiable.
     */
    public void initialize() throws IOException {
        reglesTexteArea.setEditable(false);
    }
}
