package org.checkmate.user.controller.view;

import static org.checkmate.user.util.FilePath.BOOK_LOAN;
import static org.checkmate.user.util.FilePath.LOAN_MANAGE;
import static org.checkmate.user.util.FilePath.MAIN_FX;
import static org.checkmate.user.util.FilePath.READ_REQUEST_BOOK_FX;
import static org.checkmate.user.util.FilePath.READ_TM_LOAN_STATUS_FX;
import static org.checkmate.user.util.FilePath.UPDATE_PW_FX;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;
import lombok.RequiredArgsConstructor;
import org.checkmate.common.controller.view.SceneManager;
import org.checkmate.common.service.LoginService;
import org.checkmate.common.service.LoginServiceImpl;
import org.checkmate.common.util.LoginSession;

/**
 * TODO: 주석 달기
 */
@RequiredArgsConstructor
public class ReadMyInformationPageController implements Initializable {

    private final LoginService loginService = new LoginServiceImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var instance = LoginSession.getInstance();
        var userInfo = instance.getUserInfo();
        userNameLink.setText(userInfo.getEName());
        empNo.setText(userInfo.getLoginId());
        tdName.setText(userInfo.getDName() + " / " + userInfo.getTName());
    }

    @FXML private Hyperlink userNameLink;
    @FXML private Text empNo;
    @FXML private Text tdName;

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void changePw_btn(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(UPDATE_PW_FX.getFilePath());
    }

    @FXML
    public void goHome(ActionEvent event) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(MAIN_FX.getFilePath());
    }

    @FXML
    private void goToBookLoan(ActionEvent event) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(BOOK_LOAN.getFilePath());
    }

    @FXML
    private void goToLoanManage(ActionEvent event) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(LOAN_MANAGE.getFilePath());
    }

    @FXML
    private void goToMyLoanBook(ActionEvent event) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(READ_TM_LOAN_STATUS_FX.getFilePath());
    }

    @FXML
    private void goToBookApply(ActionEvent event) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(READ_REQUEST_BOOK_FX.getFilePath());
    }

}
