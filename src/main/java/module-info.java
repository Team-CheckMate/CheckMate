module org.checkmate {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.checkmate to javafx.fxml;
    exports org.checkmate;
}