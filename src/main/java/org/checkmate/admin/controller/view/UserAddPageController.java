package org.checkmate.admin.controller.view;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.checkmate.admin.service.AdminMemberService;
import org.checkmate.admin.service.AdminMemberServiceImpl;
import org.checkmate.common.controller.view.SceneManager;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class UserAddPageController implements Initializable  {

    @FXML private Label Menu;

    @FXML private Label MenuBack;

    @FXML private AnchorPane slider;

    @FXML private TextField loginId;

    @FXML private TextField eName;
    @FXML AdminMemberService adminMemberService;

    public UserAddPageController() {
        adminMemberService = new AdminMemberServiceImpl();
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
     //   MemberSession memberSession = MemberSession.getInstance();
      //  initializePage(memberSession.getMemberInfo());
    }

//    private void initializePage(MemberInfoResponseDto responseDto) {
//        slider.setTranslateX(-176);
//
//        Menu.setOnMouseClicked(event -> {
//            TranslateTransition slide = new TranslateTransition();
//            slide.setDuration(Duration.seconds(0.4));
//            slide.setNode(slider);
//
//            slide.setToX(0);
//            slide.play();
//
//            slider.setTranslateX(-176);
//
//            slide.setOnFinished((ActionEvent e)->{
//                Menu.setVisible(false);
//                MenuBack.setVisible(true);
//            });
//        });
//
//        MenuBack.setOnMouseClicked(event -> {
//            TranslateTransition slide = new TranslateTransition();
//            slide.setDuration(Duration.seconds(0.4));
//            slide.setNode(slider);
//
//            slide.setToX(-176);
//            slide.play();
//
//            slider.setTranslateX(0);
//
//            slide.setOnFinished((ActionEvent e)->{
//                Menu.setVisible(true);
//                MenuBack.setVisible(false);
//            });
//        });
    }
