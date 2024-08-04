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
import org.checkmate.common.exception.DatabaseException;
import org.checkmate.common.exception.ValidationException;
import org.checkmate.common.util.LoginSession;
import org.checkmate.common.util.PasswordEncoder;

@NoArgsConstructor
public class LoginPageController {

    private final LoginController loginController = new LoginController();

    @FXML
    private TextField loginIdField;
    @FXML
    private PasswordField loginPwField;

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void loginBtnClick(ActionEvent actionEvent) throws NoSuchAlgorithmException {
        validateUserFields();

        UserInfo userInfo;
        try {
            userInfo = loginController.getUserInfo(
                    loginIdField.getText(),
                    PasswordEncoder.encrypt(loginPwField.getText())
            );

            System.out.println("Session Call");
            LoginSession instance = LoginSession.getInstance();

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
        } catch (DatabaseException | ValidationException e) {
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
            showAlert(message);
            throw new ValidationException(message);
        }
    }

}
