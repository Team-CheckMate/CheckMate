package org.checkmate.admin.controller.view;

import com.jfoenix.controls.JFXButton;

import java.sql.ResultSet;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.checkmate.admin.controller.server.BookController;
import org.checkmate.admin.dto.response.ReadBookLoanRecordsForChartResponseDto;
import org.checkmate.admin.mapper.BookManagementMapper;
import org.checkmate.admin.service.BookManagementService;
import org.checkmate.admin.service.BookManagementServiceImpl;

import java.awt.*;
import java.sql.SQLException;

import org.checkmate.common.controller.view.SceneManager;
import org.checkmate.common.util.Departments;

public class BookRentStatusChartByDepartmentsPageController {

    private final BookController bookController;

    // 기본 생성자
    public BookRentStatusChartByDepartmentsPageController() {
        bookController = new BookController();
    }


    @FXML private BarChart<String, Number> barChart;
    @FXML private PieChart pieChart;
    @FXML Label percentageLabel;
    private boolean isBarChartVisible = true;

    @FXML
    public void initialize() throws SQLException {
        BookManagementMapper bm = new BookManagementMapper();
        bm.readPivotDepartmentsBookLoanRecords();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("대여한 책 개수");
        for (Departments departments : Departments.values()) {
            if (departments.getCount() > 0) {
                series.getData().add(new XYChart.Data<>(departments.getDepartmentName(), departments.getCount()));
            }
        }
        barChart.getData().add(series);

        for (Departments departments : Departments.values()) {
            if (departments.getCount() > 0) {
                pieChart.getData().add(new PieChart.Data(departments.getDepartmentName(), departments.getCount()));
            }
        }
        percentageLabel.setTextFill(Color.BLACK);
        percentageLabel.setStyle("-fx-font: 20 arial;");

        for (final PieChart.Data data : pieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    try {
//                        percentageLabel.setTranslateX(e.getSceneX()-percentageLabel.getLayoutX());
//                        percentageLabel.setTranslateX(e.getSceneY()-percentageLabel.getLayoutY());
//                        percentageLabel.setText(String.valueOf(data.getPieValue())+"%");
                        double newX1 = e.getSceneX() - (percentageLabel.getWidth() / 2);
                        double newY1= e.getSceneY() - (percentageLabel.getHeight() / 2);

                        // 현재 위치를 이동시키기 위해 setTranslateX/Y 사용
                        percentageLabel.setTranslateX(newX1);
                        percentageLabel.setTranslateY(newY1);

                        // 데이터 값 설정
                        percentageLabel.setText(String.format("%.2f%%", data.getPieValue()));
                        System.out.println("Pie chart clicked");
                        Bounds b1 = data.getNode().getBoundsInLocal();

                        double newX = (b1.getWidth()) / 2.0 + b1.getMinX();
                        double newY = (b1.getHeight()) / 2.0 + b1.getMinY();

                        // Make sure pie wedge location is reset
                        data.getNode().setTranslateX(0);
                        data.getNode().setTranslateY(0);

                        TranslateTransition tt = new TranslateTransition(Duration.millis(1500), data.getNode());
                        tt.setByX(newX);
                        tt.setByY(newY);
                        tt.setAutoReverse(true);
                        tt.setCycleCount(2);
                        tt.play();
                    } catch (Exception ex) {
                        ex.printStackTrace(); // 예외를 출력하여 디버깅
                    }
                }
            });
        }
    }
    @FXML private void exit(ActionEvent event) {
        Platform.exit();
    }
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

    @FXML
    private void toggleChart(ActionEvent actionEvent) {
        isBarChartVisible = !isBarChartVisible;
        barChart.setVisible(isBarChartVisible);
        pieChart.setVisible(!isBarChartVisible);
    }
}
