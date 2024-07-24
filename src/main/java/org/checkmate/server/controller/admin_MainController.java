package org.checkmate.server.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class admin_MainController implements Initializable  {

    @FXML
    private Button books_manage;

    @FXML
    private Button  rental_status;

    @FXML
    private Button user_manage;

    @FXML
    private Button  user_status;

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
    @FXML
    // 대여 관리
    public void books_manager_btn(ActionEvent actionEvent) {

        SceneManager sm = SceneManager.getInstance();

        sm.moveScene("/org/checkmate/view/layouts/user/mainPage.fxml");



    }
    // 대여현황 
    public void rental_status_btn(ActionEvent actionEvent) {

        SceneManager sm = SceneManager.getInstance();

        sm.moveScene("/org/checkmate/view/layouts/user/mainPage.fxml");



    }
    // 사원관리
    public void user_manager_btn(ActionEvent actionEvent) {

        SceneManager sm = SceneManager.getInstance();

        sm.moveScene("/org/checkmate/view/layouts/user/mainPage.fxml");



    }
// 사원현황 
    public void user_status_btn(ActionEvent actionEvent) {

        SceneManager sm = SceneManager.getInstance();

        sm.moveScene("/org/checkmate/view/layouts/user/mainPage.fxml");



    }
}
