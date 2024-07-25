module org.checkmate {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires com.jfoenix;
    requires java.desktop;

    opens org.checkmate to javafx.fxml;
    exports org.checkmate;
    exports org.checkmate.server.controller;
    opens org.checkmate.server.controller to javafx.fxml;
    opens org.checkmate.server.entity;
}