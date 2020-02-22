module org.ispw.calculator {
    requires javafx.controls;
    requires javafx.fxml;
	requires com.jfoenix;
	requires javafx.base;
	requires java.logging;
	

	requires transitive javafx.graphics;

    opens org.ispw.calculator.logic to javafx.fxml;
    exports org.ispw.calculator.logic;
}