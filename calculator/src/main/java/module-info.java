module org.ispw.calculator {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.ispw.calculator to javafx.fxml;
    exports org.ispw.calculator;
}