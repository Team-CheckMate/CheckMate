package org.checkmate;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

import org.checkmate.server.controller.SceneManager;

public class CheckMateApplication extends Application {

    //private double xOffset = 0;
    //private double yOffset = 0;

    @Override
    public void start(Stage stage) throws IOException {
        SceneManager sceneManager = new SceneManager(stage);
        sceneManager.init();
//        // 폰트 적용
//        Font.loadFont(getClass().getResourceAsStream("/org/checkmate/fonts/BMEULJIRO.otf"), 4);
//        Parent root = FXMLLoader.load(
//                Objects.requireNonNull(getClass().getResource("view/layouts/loginPage.fxml")));
//        Scene scene = new Scene(root);
//        scene.getStylesheets().add(Objects.requireNonNull(
//                getClass().getResource("/org/checkmate/view/style/login.css")).toExternalForm());
//        stage.initStyle(StageStyle.UNDECORATED);
//
//        root.setOnMousePressed(event -> {
//            xOffset = event.getSceneX();
//            yOffset = event.getSceneY();
//        });
//
//        root.setOnMouseDragged(event -> {
//            stage.setX(event.getScreenX() - xOffset);
//            stage.setY(event.getScreenY() - yOffset);
//        });
//
//        stage.setScene(scene);
        //stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}