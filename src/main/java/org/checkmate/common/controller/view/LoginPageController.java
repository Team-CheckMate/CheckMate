package org.checkmate.common.controller.view;

import static org.checkmate.admin.util.FilePath.*;
import static org.checkmate.user.util.FilePath.MAIN_FX;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Objects;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.checkmate.common.dto.request.LoginRequestDto;
import org.checkmate.common.dto.response.LoginResponseDto;
import org.checkmate.common.service.LoginService;
import org.checkmate.common.service.LoginServiceImpl;
import org.checkmate.common.util.LoginSession;
import org.checkmate.common.util.PasswordEncoder;


/**
 * 로그인 요청 객체
 * HISTORY1: 최초 생성                              [송헌욱  2024.07.22]
 * HISTORY2: JavaFX 조작 메서드 생성                  [권혁규  2024.07.24]
 * HISTORY3: 로그인, pw 암호화 기능 생성               [송헌욱, 이준희  2024.07.24]
 * HISTORY4: Dto, lombok, optional 변경 병합        [송헌욱  2024.07.25]
 */
public class LoginPageController {

    private final LoginService loginService;

    public LoginPageController() {
        loginService = new LoginServiceImpl();
    }

    @FXML private TextField loginIdField;
    @FXML private PasswordField loginPwField;

    @FXML private void exit(ActionEvent event) {
        Platform.exit();
    }

    public boolean userField(TextField loginIdField, PasswordField loginPwField) {
        if (loginIdField.getText().isEmpty()) {
            Msg("ID를 입력해주세요.");
            loginIdField.requestFocus();
            loginPwField.clear();
            return false;
        }
        if (loginPwField.getText().isEmpty()) {
            Msg("비밀번호를 입력해주세요.");
            loginPwField.requestFocus();
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
    public void loginBtnClick(ActionEvent actionEvent) throws SQLException, NoSuchAlgorithmException {
        if (!userField(loginIdField, loginPwField)) {
            return;
        }

        String id = this.loginIdField.getText();
        String pw = PasswordEncoder.encrypt(this.loginPwField.getText());

        LoginResponseDto memberInfo = loginService.login(LoginRequestDto.of(id, pw));

        LoginSession instance = LoginSession.getInstance(memberInfo);
        if (Objects.equals(memberInfo.getRole(), "ADMIN")) {
            System.out.println("관리자 로그인");
            SceneManager sm = SceneManager.getInstance();
            sm.moveScene(MANAGEMENT_FX.getFilePath());
            assert instance != null;
            System.out.println(instance.getMemberInfo().toString());
        } else {
            System.out.println("유저 로그인");
            SceneManager sm = SceneManager.getInstance();
            sm.moveScene(MAIN_FX.getFilePath());
            assert instance != null;
            System.out.println(instance.getMemberInfo().toString());
        }
    }
}
