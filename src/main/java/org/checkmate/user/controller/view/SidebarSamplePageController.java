package org.checkmate.user.controller.view;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import org.checkmate.common.dto.response.UserInfo;
import org.checkmate.common.util.LoginSession;

public class SidebarSamplePageController implements Initializable {

    @FXML private Label Menu;
    @FXML private Label MenuBack;
    @FXML private AnchorPane slider;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoginSession loginSession = LoginSession.getInstance();
        initializePage(loginSession.getUserInfo());
    }

    private void initializePage(UserInfo responseDto) {
        slider.setTranslateX(-176);

        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-176);

            slide.setOnFinished((ActionEvent e) -> {
                Menu.setVisible(false);
                MenuBack.setVisible(true);
            });
        });

        MenuBack.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-176);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e) -> {
                Menu.setVisible(true);
                MenuBack.setVisible(false);
            });
        });
    }
}
