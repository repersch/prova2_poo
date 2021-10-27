module edu.ifsp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires sqlite.jdbc;

    opens edu.ifsp.controller to javafx.fxml;
    opens edu.ifsp.view to javafx.fxml;
    opens edu.ifsp.model to javafx.base;

    exports edu.ifsp.controller;
    exports edu.ifsp.view;
    exports edu.ifsp.model;
}