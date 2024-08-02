package org.checkmate.user.controller.view;

import static org.checkmate.user.util.FilePath.BOOK_LOAN;
import static org.checkmate.user.util.FilePath.CREATE_REQUEST_BOOK_FX;
import static org.checkmate.user.util.FilePath.LOAN_MANAGE;
import static org.checkmate.user.util.FilePath.MAIN_FX;
import static org.checkmate.user.util.FilePath.READ_MY_INFO_FX;
import static org.checkmate.user.util.FilePath.READ_NOT_RENT_LOAN_BOOK_FX;
import static org.checkmate.user.util.FilePath.READ_REQUEST_BOOK_FX;
import static org.checkmate.user.util.FilePath.READ_TM_LOAN_STATUS_FX;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
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
import org.checkmate.common.service.LoginService;
import org.checkmate.common.service.LoginServiceImpl;
import org.checkmate.common.util.LoginSession;
import org.checkmate.common.util.PasswordEncoder;
import org.checkmate.user.dto.request.UpdatePasswordRequestDto;
import org.checkmate.user.dto.response.UpdatePasswordResponseDto;

@RequiredArgsConstructor
public class UpdatePasswordPageController implements Initializable {

    private final LoginService loginService = new LoginServiceImpl();

    @FXML private Hyperlink userNameLink;
    @FXML private Text dtName;
    @FXML private TextField nowPw;
    @FXML private PasswordField changePw;
    @FXML private PasswordField checkChangePw;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var instance = LoginSession.getInstance();
        var userInfo = instance.getUserInfo();
        userNameLink.setText(userInfo.getEName());
        dtName.setText(userInfo.getDName() + "\n" + userInfo.getTName());
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
