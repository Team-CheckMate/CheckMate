package org.checkmate.common.controller.view;

import static javafx.scene.control.Alert.AlertType.WARNING;
import static org.checkmate.admin.util.FilePath.MANAGEMENT_FX;
import static org.checkmate.user.util.FilePath.MAIN_FX;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.NoArgsConstructor;
import org.checkmate.common.controller.server.LoginController;
import org.checkmate.common.dto.response.UserInfo;
import org.checkmate.common.exception.ValidationException;
import org.checkmate.common.util.LoginSession;
import org.checkmate.common.util.PasswordEncoder;


/**
 * 로그인 요청 객체
 * HISTORY1: 최초 생성                              [송헌욱  2024.07.22]
 * HISTORY2: JavaFX 조작 메서드 생성                  [권혁규  2024.07.24]
 * HISTORY3: 로그인, pw 암호화 기능 생성               [송헌욱, 이준희  2024.07.24]
 * HISTORY4: Dto, lombok, optional 변경 병합        [송헌욱  2024.07.25]
 */
@NoArgsConstructor(force = true)
public class LoginPageController {

    private final LoginController loginController = new LoginController();

    @FXML private TextField loginIdField;
    @FXML private PasswordField loginPwField;

    @FXML private void exit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void loginBtnClick(ActionEvent actionEvent) throws NoSuchAlgorithmException {
        validateUserFields();

        UserInfo userInfo = loginController.getUserInfo(
                loginIdField.getText(),
                PasswordEncoder.encrypt(loginPwField.getText())
        );

        LoginSession instance = LoginSession.getInstance(userInfo);
        try {
            if (Objects.equals(userInfo.getRole(), "ADMIN")) {
                System.out.println("관리자 로그인");
                SceneManager sm = SceneManager.getInstance();
                sm.moveScene(MANAGEMENT_FX.getFilePath());
            } else {
                System.out.println("유저 로그인");
                SceneManager sm = SceneManager.getInstance();
                sm.moveScene(MAIN_FX.getFilePath());
            }
            assert instance != null;
            System.out.println(instance.getUserInfo().toString());
        } catch (ValidationException e) {
            showAlert(e.getMessage());
        }
    }

    public void showAlert(String msg) {
        Alert alert = new Alert(WARNING);
        alert.setTitle("경고!");
        alert.setHeaderText("로그인");
        alert.setContentText(msg);
        alert.show();
    }

    public void validateUserFields() {
        isNotEmpty(loginIdField, "ID를 입력해주세요.");
        isNotEmpty(loginPwField, "비밀번호를 입력해주세요.");
    }

    private void isNotEmpty(TextField field, String message) {
        if (field.getText().trim().isEmpty()) {
            throw new ValidationException(message);
        }
    }

}
