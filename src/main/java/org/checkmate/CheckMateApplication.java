package org.checkmate;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

import org.checkmate.common.util.SceneManager;

public class CheckMateApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        SceneManager sceneManager = new SceneManager(stage);
        sceneManager.init();
    }

    public static void main(String[] args) {
        launch();
    }
}