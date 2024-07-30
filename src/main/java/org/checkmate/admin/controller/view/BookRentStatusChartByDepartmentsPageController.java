package org.checkmate.admin.controller.view;

import com.jfoenix.controls.JFXButton;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import org.checkmate.admin.controller.server.BookController;
import org.checkmate.admin.dto.response.ReadBookLoanRecordsForChartResponseDto;
import org.checkmate.admin.mapper.BookManagementMapper;
import org.checkmate.admin.service.BookManagementService;
import org.checkmate.admin.service.BookManagementServiceImpl;

import java.awt.*;
import java.sql.SQLException;
import org.checkmate.common.util.Departments;

public class BookRentStatusChartByDepartmentsPageController {

    private final BookController bookController;

    // 기본 생성자
    public BookRentStatusChartByDepartmentsPageController() {
        bookController = new BookController();
    }


    @FXML private BarChart<String, Number> barChart;
    @FXML private PieChart pieChart;
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
    }

    @FXML
    private void toggleChart(ActionEvent actionEvent) {
        isBarChartVisible = !isBarChartVisible;
        barChart.setVisible(isBarChartVisible);
        pieChart.setVisible(!isBarChartVisible);
    }
}
