module xyz.andrewtran.chemclock {
    requires javafx.controls;
    requires javafx.web;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.swing;
    opens xyz.andrewtran.chemclock to javafx.graphics;
}