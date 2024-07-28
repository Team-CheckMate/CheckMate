package org.checkmate.common.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class SceneManager {
    private static SceneManager instance;
    private double xOffset = 0;
    private double yOffset = 0;

    Stage stage;
    Scene scene;
    //Share share;

    public SceneManager( Stage stage ) {
        if(SceneManager.instance != null) return;
        SceneManager.instance = this;

        this.stage = stage;
        //this.share = new Share();
        //this.stage.setUserData(share);
    }

    public static SceneManager getInstance() {
        return instance;
    }

    public Stage getStage() {
        return this.stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setTitle( String title ) {
        stage.setTitle(title);
    }

    public void init() {
        try {
            // 폰트 적용
            Font.loadFont(getClass().getResourceAsStream("/org/checkmate/fonts/BMEULJIRO.otf"), 4);
            //Parent root = FXMLLoader.load(getClass().getResource("/org/checkmate/view/layouts/loginPage.fxml"));
            Parent root = FXMLLoader.load(
                    Objects.requireNonNull(getClass().getResource("/org/checkmate/view/layouts/comm/loginPage.fxml")));
            scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(
                    getClass().getResource("/org/checkmate/view/style/common/loginPage.css")).toExternalForm());
            stage.initStyle(StageStyle.UNDECORATED);

            root.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });

            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void moveScene( String location ) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(location));
            scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
