package org.checkmate.admin.controller.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.checkmate.admin.service.MemberService;
import org.checkmate.admin.service.MemberServiceImpl;
import org.checkmate.common.controller.view.SceneManager;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class UserAddPageController implements Initializable  {


    @FXML private TextField loginId;
    @FXML private TextField eName;
    @FXML MemberService adminMemberService;

    public UserAddPageController() {
        adminMemberService = new MemberServiceImpl();
    }
    //사이드바 이동
    @FXML private void goToBookManage(ActionEvent event)
    {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/admin/bookManagementPage.fxml");
    }
    @FXML private void goToLoanStatus(ActionEvent event)
    {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/admin/applyStatusViewPage.fxml"); //변경
    }
    @FXML private void goToUserManage(ActionEvent event)
    {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/admin/userManagementPage.fxml");
    }
    @FXML private void goToApplyStatus(ActionEvent event)
    {SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/admin/applyStatusViewPage.fxml");
    }

    @FXML private void exit(ActionEvent event) {
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
    private void createAddBtn(ActionEvent event){
        String l = loginId.getText();
        String e = eName.getText();


        int result =0;
        try {
            result = adminMemberService.createUser(l,e);
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

    }

    }
