package com.nicolas.dames.controleurs;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class AccueilControleur {

    @FXML
    public BorderPane accueilBorderPane;
    public void initialize() throws IOException {
        MenuControleur.chargerMenu("menu.fxml", accueilBorderPane);
    }
}