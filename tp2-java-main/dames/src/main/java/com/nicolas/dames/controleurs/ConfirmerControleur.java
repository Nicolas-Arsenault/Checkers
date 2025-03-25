package com.nicolas.dames.controleurs;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ConfirmerControleur {

    @FXML
    public Label confirmerLabel;

    public void annuler(ActionEvent actionEvent) {
        Stage stage = (Stage) confirmerLabel.getScene().getWindow();
        stage.close();
    }

    public void confirmer(ActionEvent actionEvent) {

        System.exit(0);
    }
}
