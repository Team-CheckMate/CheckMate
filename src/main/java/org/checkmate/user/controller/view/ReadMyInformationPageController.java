package org.checkmate.user.controller.view;

import static org.checkmate.user.util.FilePath.UPDATE_PW_FX;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import org.checkmate.common.controller.view.SceneManager;
import org.checkmate.user.dto.response.ReadMyInformationResponseDto;
import org.checkmate.common.service.LoginService;
import org.checkmate.common.service.LoginServiceImpl;
import org.checkmate.common.util.LoginSession;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * MyPage 컨트롤러
 * HISTORY1: 최초 생성                              [이준희  2024.07.26]
 * HISTORY2: 화면 디자인                             [송헌욱  2024.07.29]
 */

public class ReadMyInformationPageController implements Initializable {

    private final LoginService loginService;

    public ReadMyInformationPageController() {
        loginService = new LoginServiceImpl();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoginSession loginSession = LoginSession.getInstance();
        try {
            ReadMyInformationResponseDto myPageInfo = loginService.getMypageInfo(
                    loginSession.getMemberInfo().getLoginId());
            username.setText(loginSession.getMemberInfo().getEName());
            empNo.setText(myPageInfo.getLoginId());
            tName.setText(myPageInfo.getTeamName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML private Text username;
    @FXML private Text empNo;
    @FXML private Text tName;

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void changePw_btn(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(UPDATE_PW_FX.getFilePath());
    }

    //사이드바 이동
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
