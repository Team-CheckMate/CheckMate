package org.checkmate.user.controller.view;

import static org.checkmate.user.util.FilePath.READ_MY_INFO_FX;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import lombok.RequiredArgsConstructor;
import org.checkmate.common.controller.view.SceneManager;
import org.checkmate.common.service.LoginService;
import org.checkmate.common.service.LoginServiceImpl;
import org.checkmate.common.util.LoginSession;
import org.checkmate.common.util.PasswordEncoder;
import org.checkmate.user.dto.request.UpdatePasswordRequestDto;
import org.checkmate.user.dto.response.UpdatePasswordResponseDto;

@RequiredArgsConstructor
public class UpdatePasswordPageController implements Initializable {

    private final LoginService loginService = new LoginServiceImpl();

    @FXML private Text username;
    @FXML private TextField nowPw;
    @FXML private PasswordField changePw;
    @FXML private PasswordField checkChangePw;

    @FXML
    private void exit(javafx.event.ActionEvent event) {
        Platform.exit();
    }
    //사이드바 이동
    @FXML private void goToBookLoan(javafx.event.ActionEvent event)
    {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/user/readLoanBookPage.fxml");
    }
    @FXML private void goToLoanManage(javafx.event.ActionEvent event)
    {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/user/readLoanBookPage.fxml"); //변경
    }
    @FXML private void goToMyLoanBook(javafx.event.ActionEvent event)
    {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/user/readLoanBookPage.fxml");
    }
    @FXML private void goToBookApply(javafx.event.ActionEvent event)
    {SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/user/readLoanBookPage.fxml");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var session = LoginSession.getInstance();
        var memberInfo = session.getUserInfo();
        username.setText(memberInfo.getEName());
    }

    public boolean userField(TextField nowPw, PasswordField changePw, PasswordField checkChangePw) {
        if (nowPw.getText().isEmpty()) {
            Msg("현재 PW를 입력해주세요.");
            nowPw.requestFocus();
            nowPw.clear();
            return false;
        }
        if (changePw.getText().isEmpty()) {
            Msg("변경하실 PW를 입력해주세요.");
            changePw.requestFocus();
            return false;
        }
        if (checkChangePw.getText().isEmpty()) {
            Msg("변경할 PW를 재입력해주세요.");
            checkChangePw.requestFocus();
            return false;
        }
        if (!checkChangePw.getText().equals(changePw.getText())) {
            Msg("입력하신 비밀번호가 다릅니다.");
            checkChangePw.requestFocus();
            return false;
        }
        return true;
    }

    public void Msg(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("알림");
        alert.setHeaderText("마이페이지");
        alert.setContentText(msg);
        alert.show();
    }

    @FXML
    public void changePw_btn(javafx.event.ActionEvent actionEvent) throws NoSuchAlgorithmException, SQLException {
        System.out.println("버튼실행됨");
        if(!userField(nowPw,changePw,checkChangePw)){
            return;
        }
        String loginId= LoginSession.getInstance().getUserInfo().getLoginId();
        String nowPw =  PasswordEncoder.encrypt(this.nowPw.getText());
        String changePw = PasswordEncoder.encrypt(this.changePw.getText());

        UpdatePasswordResponseDto changeResult = loginService.changePw(
            UpdatePasswordRequestDto.builder().loginId(loginId).nowPw(nowPw).changePw(changePw)
                .build());
        if(changeResult.isSuccess()){
            Msg("비밀번호가 변경되었습니다!");
            SceneManager sm = SceneManager.getInstance();
            sm.moveScene(READ_MY_INFO_FX.getFilePath());
            System.out.println(changeResult.getMessage());
        }else{
            Msg(changeResult.getMessage());
        }
    }
}
