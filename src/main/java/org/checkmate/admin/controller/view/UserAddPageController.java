package org.checkmate.admin.controller.view;

import static org.checkmate.admin.util.FilePath.BOOK_APPLY_FX;
import static org.checkmate.admin.util.FilePath.BOOK_LOAN_STATUS_FX;
import static org.checkmate.admin.util.FilePath.BOOK_MANAGEMENT_FX;
import static org.checkmate.admin.util.FilePath.USER_MANAGEMENT_FX;
import static org.checkmate.user.util.FilePath.MAIN_ADMIN;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.checkmate.admin.service.MemberService;
import org.checkmate.admin.service.MemberServiceImpl;
import org.checkmate.common.controller.view.SceneManager;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import org.checkmate.common.util.LoginSession;


public class UserAddPageController implements Initializable {

  @FXML private Hyperlink userNameLink;
  @FXML private TextField loginId;
  @FXML private TextField eName;
  MemberService adminMemberService;

  public UserAddPageController() {
    adminMemberService = new MemberServiceImpl();
  }

  //사이드바 이동
  @FXML
  public void goHome(ActionEvent event) {
    SceneManager sm = SceneManager.getInstance();
    sm.moveScene(MAIN_ADMIN.getFilePath());
  }

  @FXML
  private void goToBookManage(ActionEvent event) {
    SceneManager sm = SceneManager.getInstance();
    sm.moveScene(BOOK_MANAGEMENT_FX.getFilePath());
  }

  @FXML
  private void goToLoanStatus(ActionEvent event) {
    SceneManager sm = SceneManager.getInstance();
    sm.moveScene(BOOK_LOAN_STATUS_FX.getFilePath()); //변경
  }

  @FXML
  private void goToUserManage(ActionEvent event) {
    SceneManager sm = SceneManager.getInstance();
    sm.moveScene(USER_MANAGEMENT_FX.getFilePath());
  }

  @FXML
  private void goToApplyStatus(ActionEvent event) {
    SceneManager sm = SceneManager.getInstance();
    sm.moveScene(BOOK_APPLY_FX.getFilePath());
  }

  @FXML
  private void exit(ActionEvent event) {
    Platform.exit();
  }

  @FXML
  public void Msg(String msg) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("경고!");
    alert.setHeaderText("삭제");
    alert.setContentText(msg);
    alert.show();
  }

  @FXML
  private void createAddBtn(ActionEvent event) {
    String l = loginId.getText();
    String e = eName.getText();

    int result = 0;
    try {
      result = adminMemberService.createUser(l, e);
    } catch (SQLException ex) {
      throw new RuntimeException(ex);
    }

    String msg = result != 0 ? "삽입되었습니다" : "삽입 실패하였습니다";
    Msg("사원번호 " + e + msg);
    System.out.println(msg);
    SceneManager sm = SceneManager.getInstance();
    sm.moveScene("/org/checkmate/view/layouts/admin/userManagementPage.fxml");
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    userNameLink.setText("관리자");
  }

}
