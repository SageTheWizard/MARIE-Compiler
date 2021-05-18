module MARIE.JavaFXSimulator {
    requires javafx.controls;
    requires javafx.fxml;
    opens MARIE to javafx.fxml;
    exports MARIE;
}