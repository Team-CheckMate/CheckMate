package org.checkmate.user.controller.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import org.checkmate.common.controller.view.SceneManager;

public class TemplatePage {

    @FXML private Hyperlink userNameLink;

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    @FXML private void goToBookLoan(ActionEvent event)
    {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/user/readLoanBookPage.fxml");
    }
    @FXML private void goToLoanManage(ActionEvent event)
    {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/user/readLoanBookPage.fxml"); //변경
    }
    @FXML private void goToMyLoanBook(ActionEvent event)
    {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/user/readLoanBookPage.fxml");
    }
    @FXML private void goToBookApply(ActionEvent event)
    {SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/user/readLoanBookPage.fxml");
    }

}
