package org.checkmate.user.controller.view;

import static javafx.scene.control.Alert.AlertType.INFORMATION;
import static org.checkmate.user.util.FilePath.BOOK_LOAN;
import static org.checkmate.user.util.FilePath.MAIN_FX;
import static org.checkmate.user.util.FilePath.READ_MY_INFO_FX;
import static org.checkmate.user.util.FilePath.READ_NOT_RENT_LOAN_BOOK_FX;
import static org.checkmate.user.util.FilePath.READ_REQUEST_BOOK_FX;
import static org.checkmate.user.util.FilePath.READ_TM_LOAN_STATUS_FX;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import lombok.RequiredArgsConstructor;
import org.checkmate.common.controller.view.SceneManager;
import org.checkmate.common.dto.response.CommonResponse;
import org.checkmate.common.exception.ValidationException;
import org.checkmate.common.util.LoginSession;
import org.checkmate.common.util.PasswordEncoder;
import org.checkmate.user.controller.server.UserController;
import org.checkmate.user.dto.response.UpdatePasswordResponseDto;

@RequiredArgsConstructor
public class UpdatePasswordPageController implements Initializable {

    private final UserController server = new UserController();

    @FXML
    private Hyperlink userNameLink;
    @FXML
    private Text dtName;
    @FXML
    private TextField nowPw;
    @FXML
    private PasswordField changePw;
    @FXML
    private PasswordField checkChangePw;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var instance = LoginSession.getInstance();
        var userInfo = instance.getUserInfo();
        userNameLink.setText(userInfo.getEName());
        dtName.setText(userInfo.getDName() + "\n" + userInfo.getTName());
    }

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void goHome(ActionEvent event) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(MAIN_FX.getFilePath());
    }

    @FXML
    private void goToBookLoan(ActionEvent event) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(BOOK_LOAN.getFilePath());
    }

    @FXML
    private void goToLoanManage(ActionEvent event) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(READ_NOT_RENT_LOAN_BOOK_FX.getFilePath());
    }

    @FXML
    private void goToMyLoanBook(ActionEvent event) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(READ_TM_LOAN_STATUS_FX.getFilePath());
    }

    @FXML
    private void goToBookApply(ActionEvent event) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(READ_REQUEST_BOOK_FX.getFilePath());
    }

    @FXML
    public void changePw_btn(javafx.event.ActionEvent actionEvent) throws NoSuchAlgorithmException {
        validateUserFields();

        CommonResponse<UpdatePasswordResponseDto> changeResult = server.updatePassword(
                LoginSession.getInstance().getUserInfo().getLoginId(),
                PasswordEncoder.encrypt(nowPw.getText()),
                PasswordEncoder.encrypt(changePw.getText())
        );

        if (changeResult.getStatus()) {
            showAlert(changeResult.getMessage());
            SceneManager sm = SceneManager.getInstance();
            sm.moveScene(READ_MY_INFO_FX.getFilePath());
        } else {
            showAlert(changeResult.getMessage());
        }
    }

    public void showAlert(String msg) {
        Alert alert = new Alert(INFORMATION);
        alert.setTitle("알림");
        alert.setHeaderText("마이페이지");
        alert.setContentText(msg);
        alert.show();
    }

    public void validateUserFields() {
        isNotEmpty(nowPw, "현재 PW를 입력해주세요.");
        isNotEmpty(changePw, "변경하실 PW를 입력해주세요.");
        isNotEmpty(checkChangePw, "변경할 PW를 재입력해주세요.");
        isNotEquals(changePw, checkChangePw, "입력하신 비밀번호가 다릅니다.");
    }

    private void isNotEquals(TextField changePw, TextField checkChangePw, String message) {
        if (!checkChangePw.getText().equals(changePw.getText())) {
            showAlert(message);
            checkChangePw.requestFocus();
            checkChangePw.clear();
            throw new ValidationException(message);
        }
    }

    private void isNotEmpty(TextField field, String message) {
        if (field.getText().trim().isEmpty()) {
            showAlert(message);
            field.requestFocus();
            field.clear();
            throw new ValidationException(message);
        }
    }
}
