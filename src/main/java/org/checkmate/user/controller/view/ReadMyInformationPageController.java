package org.checkmate.user.controller.view;

import static org.checkmate.user.util.FilePath.UPDATE_PW_FX;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import lombok.RequiredArgsConstructor;
import org.checkmate.common.controller.view.SceneManager;
import org.checkmate.common.service.LoginService;
import org.checkmate.common.service.LoginServiceImpl;
import org.checkmate.common.util.LoginSession;
import org.checkmate.user.dto.response.ReadMyInformationResponseDto;

/**
 * TODO: 주석 달기
 */
@RequiredArgsConstructor
public class ReadMyInformationPageController implements Initializable {

    private final LoginService loginService = new LoginServiceImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoginSession loginSession = LoginSession.getInstance();
        try {
            ReadMyInformationResponseDto myPageInfo = loginService.getMypageInfo(
                    loginSession.getUserInfo().getLoginId());
            username.setText(loginSession.getUserInfo().getEName());
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

}
