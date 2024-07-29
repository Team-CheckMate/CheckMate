package org.checkmate.admin.controller.view;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import org.checkmate.admin.dto.response.ReadBookLoanRecordsForChartResponseDto;
import org.checkmate.admin.service.BookManagementService;
import org.checkmate.admin.service.BookManagementServiceImpl;

import java.awt.*;
import java.sql.SQLException;

public class BookRentStatusChartByDepartmentsPageController {

    private final BookManagementService bookService;

    // 기본 생성자
    public BookRentStatusChartByDepartmentsPageController() {
        bookService = new BookManagementServiceImpl();
    }


    @FXML private BarChart<String, Number> barChart;
    @FXML private PieChart pieChart;
    private boolean isBarChartVisible = true;
    ObservableList<ReadBookLoanRecordsForChartResponseDto> bookLoanRecordsForChartList;

    @FXML
    public void initialize() throws SQLException {
        bookLoanRecordsForChartList = bookService.readDepartmentsBookLoanRecords();
        // List<responseDto>를 ObservableList로 변환
        ObservableList<XYChart.Data<String, Number>> chartData = FXCollections.observableArrayList();
        for (ReadBookLoanRecordsForChartResponseDto record : bookLoanRecordsForChartList) {
            chartData.add(new XYChart.Data<>(record.getName(), record.getCount()));
        }

        // Series 생성 및 데이터 설정
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("대여한 책 개수");
        series.setData(chartData);

        // 시리즈 추가
        barChart.getData().add(series);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (ReadBookLoanRecordsForChartResponseDto record : bookLoanRecordsForChartList) {
            pieChartData.add(new PieChart.Data(record.getName(), record.getCount()));
        }
        pieChart.setData(pieChartData);

    }

    @FXML
    private void toggleChart(ActionEvent actionEvent) {
        isBarChartVisible = !isBarChartVisible;
        barChart.setVisible(isBarChartVisible);
        pieChart.setVisible(!isBarChartVisible);
    }
}
