package org.checkmate.server.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.checkmate.server.dto.request.LoginRequestDto;
import org.checkmate.server.dto.response.LoginResponseDto;
import org.checkmate.server.service.MemberService;
import org.checkmate.server.service.MemberServiceImpl;

/**
 * 로그인 요청 객체
 * HISTORY1: 최초 생성                              [송헌욱  2024.07.22]
 * HISTORY2: JavaFX 조작 메서드 생성                  [권혁규  2024.07.24]
 * HISTORY3: 로그인 기능 생성                         [송헌욱, 이준희  2024.07.24]
 */
public class LoginPageController implements Initializable {

    private final MemberService memberService;

    // 기본 생성자
    public LoginPageController() {
        memberService = new MemberServiceImpl();
    }

    @FXML
    private Button loginBtnLabel;

    @FXML
    private TextField loginIdLabel;

    @FXML
    private PasswordField loginPwLabel;

    @FXML
    private Text loginTitle;

    @FXML
    private Text loginSubtitle;

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public boolean userField(TextField loginIdLabel, PasswordField loginPwLabel) {
        if (loginIdLabel.getText().isEmpty()) {
            Msg("ID를 입력해주세요.");
            loginIdLabel.requestFocus();
            loginPwLabel.clear();
            return false;
        }
        if (loginPwLabel.getText().isEmpty()) {
            Msg("비밀번호를 입력해주세요.");
            loginPwLabel.requestFocus();
            return false;
        }
        return true;
    }

    public void Msg(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("경고!");
        alert.setHeaderText("로그인");
        alert.setContentText(msg);
        alert.show();
    }

    @FXML
    public void loginBtn(ActionEvent actionEvent) {
        if (!userField(this.loginIdLabel, this.loginPwLabel)) {
            return;
        }

        String id = this.loginIdLabel.getText();
        String pw = this.loginPwLabel.getText();

        LoginRequestDto requestDto = new LoginRequestDto(id, pw);
        LoginResponseDto responseDto = memberService.login(requestDto);

        if (responseDto.isSuccess()) {
            SceneManager sm = SceneManager.getInstance();
            sm.moveScene("/org/checkmate/view/layouts/user/mainPage.fxml");
        } else {
            Msg("로그인 실패: " + responseDto.getMessage());
        }
    }
}
