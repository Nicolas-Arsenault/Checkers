package com.nicolas.dames.controleurs;

import com.nicolas.dames.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class JouerControleur {

    @FXML
    public BorderPane jouerBorerPane;
    public GridPane damierPlanche;

    public void initialize() throws IOException {
        MenuControleur.chargerMenu("menu.fxml", jouerBorerPane);

    }
}
