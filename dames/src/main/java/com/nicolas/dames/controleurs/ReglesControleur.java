package com.nicolas.dames.controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class ReglesControleur {

    @FXML
    public TextArea reglesTexteArea;

    public void initialize() throws IOException {
        reglesTexteArea.setEditable(false);
    }
}
