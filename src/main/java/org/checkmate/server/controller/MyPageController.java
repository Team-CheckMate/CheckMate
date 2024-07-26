package org.checkmate.server.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.checkmate.server.dto.response.MyPageResponsedto;
import org.checkmate.server.service.MemberService;
import org.checkmate.server.service.MemberServiceImpl;
import org.checkmate.server.util.MemberSession;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * MyPage 정보 요청 객체
 * HISTORY1: 최초 생성                              [이준희  2024.07.26]
*/

public class MyPageController implements Initializable {

    private final MemberService memberService;

    public MyPageController() {memberService = new MemberServiceImpl();}

    @FXML
    private Text welcomeMent;
    @FXML
    private Text empNo;

    @FXML
    private Text teamName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MemberSession memberSession = MemberSession.getInstance();
        System.out.println("empNo: " + empNo); // null이면 FXML 파일과 매핑되지 않은 것임
        System.out.println("teamName: " + teamName);
        System.out.println("ho");
        try {
            MyPageResponsedto myPageInfo = memberService.getMypageInfo(memberSession.getMemberInfo().getLoginId());
            System.out.println(myPageInfo.getTeamName()+""+myPageInfo.getLoginId());
            welcomeMent.setText( memberSession.getMemberInfo().getEName()+"님 환영합니다!");
                        empNo.setText(myPageInfo.getLoginId());
            teamName.setText(myPageInfo.getTeamName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void exit(javafx.event.ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void changePw_btn(javafx.event.ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/user/changePwPage.fxml");
    }
}
