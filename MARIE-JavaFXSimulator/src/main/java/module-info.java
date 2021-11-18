module MARIESim {
    requires javafx.controls;
    requires javafx.fxml;
    requires MARIEAssembler;
    opens MARIE to javafx.fxml;
    exports MARIE;
}