module com.example.itproermysql {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.itproermysql to javafx.fxml;
    exports com.example.itproermysql;
    exports com.example.itproermysql.controllers;
    opens com.example.itproermysql.controllers to javafx.fxml;
}