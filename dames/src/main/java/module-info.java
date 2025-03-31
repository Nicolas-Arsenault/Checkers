module com.nicolas.dames {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.nicolas.dames to javafx.fxml;
    exports com.nicolas.dames;
    exports com.nicolas.dames.logique;

    opens com.nicolas.dames.controleurs to javafx.fxml;
    opens com.nicolas.dames.logique to javafx.fxml;
}