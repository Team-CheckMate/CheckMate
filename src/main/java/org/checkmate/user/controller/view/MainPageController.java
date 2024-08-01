package org.checkmate.user.controller.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import org.checkmate.common.controller.view.SceneManager;
import org.checkmate.common.util.LoginSession;

import static org.checkmate.user.util.FilePath.*;

public class MainPageController implements Initializable {

    @FXML
    private Hyperlink userNameLink;

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    /**
     * 마이페이지 이동 이벤트 처리
     */
    @FXML
    private void openMyPage(ActionEvent event) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(READ_MY_INFO_FX.getFilePath());
    }

    /**
     * 도서 대여 이벤트 처리
     */
    @FXML
    public void bookLoan(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/user/readLoanBookPage.fxml");
    }

    // 대여정보
    @FXML
    public void loanStatus(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(READ_NOT_RENT_LOAN_BOOK_FX.getFilePath());
    }

    // 부서현황
    @FXML
    public void teamStatus(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(READ_TM_LOAN_STATUS_FX.getFilePath());
    }

    // 도서신청
    @FXML
    public void reqPayment(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(READ_REQUEST_BOOK_FX.getFilePath());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var session = LoginSession.getInstance();
        var userInfo = session.getUserInfo();
        userNameLink.setText(userInfo.getEName());
    }

}
