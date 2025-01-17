package org.checkmate.admin.controller.view;

import static org.checkmate.admin.util.FilePath.BOOK_APPLY_FX;
import static org.checkmate.admin.util.FilePath.BOOK_MANAGEMENT_FX;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.checkmate.common.controller.view.SceneManager;

public class ManagementPageController implements Initializable  {

    @FXML private Button books_manage;
    @FXML private Button  rental_status;
    @FXML private Button user_manage;
    @FXML private Button  user_status;

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    // 도서 관리
    @FXML
    public void books_manager_btn(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(BOOK_MANAGEMENT_FX.getFilePath());
    }

    // 대여현황
    public void rental_status_btn(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/admin/bookRentStatusPage.fxml");
    }

    // 사원관리
    public void user_manager_btn(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/admin/userManagementPage.fxml");
    }

    // 사원현황
    public void user_status_btn(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(BOOK_APPLY_FX.getFilePath());
    }

}
