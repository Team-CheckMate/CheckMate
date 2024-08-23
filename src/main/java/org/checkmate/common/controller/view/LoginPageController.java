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
import lombok.extern.log4j.Log4j2;
import org.checkmate.common.controller.server.LoginController;
import org.checkmate.common.dto.response.UserInfo;
import org.checkmate.common.exception.DatabaseException;
import org.checkmate.common.exception.ValidationException;
import org.checkmate.common.util.LoginSession;
import org.checkmate.common.util.PasswordEncoder;

@Log4j2
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
            log.info(" <<< [ 📢 Call LoginController to \"{}\", \"{}\" ]",
                    loginIdField.getText(),
                    loginPwField.getText()
            );
            loginController.login(
                    loginIdField.getText(),
                    PasswordEncoder.encrypt(loginPwField.getText())
            );
            log.info(" <<< [ 📢 LoginSession Call ]");
            LoginSession instance = LoginSession.getInstance();
            log.info(" >>> [ ✅ LoginSession Successfully called! Get \"UserInfo\" ]");
            userInfo = instance.getUserInfo();

            if (Objects.equals(userInfo.getRole(), "ADMIN")) {
                log.info(" >>> [ 🪪 Role is \"{}\" ]", userInfo.getRole());
                SceneManager sm = SceneManager.getInstance();
                sm.moveScene(MANAGEMENT_FX.getFilePath());
            } else {
                log.info(" >>> [ 🪪 Role is \"{}\" ]", userInfo.getRole());
                SceneManager sm = SceneManager.getInstance();
                sm.moveScene(MAIN_FX.getFilePath());
            }
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
