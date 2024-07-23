module org.checkmate {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;

    opens org.checkmate to javafx.fxml;
    exports org.checkmate;
    exports org.checkmate.client;
    opens org.checkmate.client to javafx.fxml;
}