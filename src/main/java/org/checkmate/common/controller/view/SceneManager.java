package org.checkmate.common.controller.view;

import static org.checkmate.common.util.FilePath.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;
import lombok.Getter;

public class SceneManager {

    @Getter
    private static SceneManager instance;

    private double xOffset = 0;
    private double yOffset = 0;

    private Stage stage;
    private Scene scene;

    public SceneManager(Stage stage) {
        if (SceneManager.instance != null) {
            return;
        }
        SceneManager.instance = this;
        this.stage = stage;
    }

    public void init() {
        try {
            Font.loadFont(getClass().getResourceAsStream(EXTERNAL_FT.getFilePath()), 10);
            moveScene(LOGIN_FX.getFilePath());
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enableMouseDrag(Parent root) {
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    public void moveScene(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
            scene = new Scene(root);
            enableMouseDrag(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
