package org.checkmate.server.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginPageController implements Initializable {
    //@FXML private Button loginBtnLabel;
    @FXML private TextField loginIdLabel;
    @FXML private PasswordField loginPwLabel;
    //final int FIELDLENGTH = 9;


    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //loginBtnLabel.setDisable(true);
//        loginBtnLabel.setOnAction(e -> {
//            userAction();
//        });   //로그인 버튼 클릭 이벤트
//        loginIdLabel.textProperty().addListener((obs, oldTxt, newTxt) -> {
//            checkFieldLength();
//        });
    }

//    public void checkFieldLength() {
//        loginBtnLabel.setDisable(false);
//        if(loginIdLabel.getLength() >= FIELDLENGTH) {
//            loginIdLabel.setText(loginIdLabel.getText().substring(0, FIELDLENGTH));
//        }
//    }

    //로그인 아이디와 패스워드 비교
    public void userAction() {
        if(!userField(this.loginIdLabel, this.loginPwLabel))
            return;

//        String id = this.loginIdLabel.getText();
//        String pw = this.loginPwLabel.getText();
//        Stage primaryStage = new Stage();
//        Parent root = FXMLLoader.load(getClass().getResource("view/layouts/mainPage.fxml"));
//        Scene scene = new Scene(root);
//        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        primaryStage.setScene(scene);
//        primaryStage.show();

//        MyDB DB = new MyDB();
//        if(pw.equals(DB.getPassword(id))) {
//            Msg("로그인 성공");
//        } else {
//            Msg("로그인 실패");
//            this.loginIdLabel.clear();
//            this.loginPwLabel.clear();
//            this.loginIdLabel.requestFocus();
//        }

    }

    //아이디, 비밀번호 입력체크
    public boolean userField(TextField loginIdLabel, PasswordField loginPwLabel) {
        if(loginIdLabel.getText().isEmpty()) {
            Msg("ID를 입력해주세요.");
            loginIdLabel.requestFocus();
            loginPwLabel.clear();
            return false;
        }
        if(loginPwLabel.getText().isEmpty()) {
            Msg("비밀번호를 입력해주세요.");
            loginPwLabel.requestFocus();
            return false;
        }
        return true;
    }

    //alert 메시지 설정
    public void Msg(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("경고!");
        alert.setHeaderText("로그인");
        alert.setContentText(msg);
        alert.show();
    }

    @FXML
    public void loginBtn(ActionEvent actionEvent) {
        if(!userField(this.loginIdLabel, this.loginPwLabel))
            return;

        String id = this.loginIdLabel.getText();
        String pw = this.loginPwLabel.getText();
        SceneManager sm = SceneManager.getInstance();

        sm.moveScene("/org/checkmate/view/layouts/user/mainPage.fxml");

    }

}