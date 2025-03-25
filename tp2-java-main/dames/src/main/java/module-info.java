module com.nicolas.dames {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.nicolas.dames to javafx.fxml;
    exports com.nicolas.dames;
    exports com.nicolas.dames.controleurs;
    opens com.nicolas.dames.controleurs to javafx.fxml;
}