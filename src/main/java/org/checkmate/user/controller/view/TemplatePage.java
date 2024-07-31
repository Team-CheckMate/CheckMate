package org.checkmate.user.controller.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;

public class TemplatePage {

    @FXML private Hyperlink userNameLink;

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

}
