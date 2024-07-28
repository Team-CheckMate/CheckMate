package org.checkmate.user.controller.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import org.checkmate.common.util.SceneManager;
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
*/

public class ReadMyInformationPageController implements Initializable {

    private final LoginService loginService;

    public ReadMyInformationPageController() {
        loginService = new LoginServiceImpl();}

    @FXML
    private Text welcomeMent;
    @FXML
    private Text empNo;

    @FXML
    private Text teamName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoginSession loginSession = LoginSession.getInstance();
        try {
            ReadMyInformationResponseDto myPageInfo = loginService.getMypageInfo(
                    loginSession.getMemberInfo().getLoginId());
            System.out.println(myPageInfo.getTeamName()+""+myPageInfo.getLoginId());
            welcomeMent.setText( loginSession.getMemberInfo().getEName()+"님 환영합니다!");
            empNo.setText(myPageInfo.getLoginId());
            teamName.setText(myPageInfo.getTeamName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void exit(javafx.event.ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void changePw_btn(javafx.event.ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/user/updatePasswordPage.fxml");
    }
}
