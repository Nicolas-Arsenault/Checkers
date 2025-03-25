package com.nicolas.dames.controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ReglesControleur {

    @FXML
    public BorderPane regleBorderPane;
    public TextArea reglesTexteArea;

    public void initialize() throws IOException {
        MenuControleur.chargerMenu("menu.fxml", regleBorderPane);
        reglesTexteArea.setEditable(false);
    }
}
