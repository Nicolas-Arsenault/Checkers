package com.nicolas.dames.controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AccueilControleur {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}