package org.checkmate.admin.controller.view;

import static org.checkmate.admin.util.FilePath.BOOK_APPLY_FX;
import static org.checkmate.admin.util.FilePath.BOOK_LOAN_STATUS_FX;
import static org.checkmate.admin.util.FilePath.BOOK_MANAGEMENT_FX;
import static org.checkmate.admin.util.FilePath.USER_MANAGEMENT_FX;
import static org.checkmate.user.util.FilePath.MAIN_ADMIN;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.checkmate.admin.controller.server.BookController;
import org.checkmate.admin.dto.response.ReadBookLoanRecordsForChartResponseDto;
import org.checkmate.admin.dto.response.ReadBookLoanRecordsResponseDto;
import org.checkmate.admin.service.BookManagementService;
import org.checkmate.admin.service.BookManagementServiceImpl;
import org.checkmate.common.controller.view.SceneManager;

import java.sql.SQLException;

public class BookRentStatusChartByTeamsPageController {

  private final BookController bookController;

  // 기본 생성자
  public BookRentStatusChartByTeamsPageController() {
    bookController = new BookController();
  }

  @FXML private BarChart<String, Number> barChart;
  @FXML private PieChart pieChart;
  private boolean isBarChartVisible = true;
  ObservableList<ReadBookLoanRecordsForChartResponseDto> bookLoanRecordsForChartList;

  @FXML
  public void initialize() throws SQLException {
    ObservableList<ReadBookLoanRecordsForChartResponseDto> bookLoanRecordsForChartList = FXCollections.observableArrayList(bookController.readTeamsBookLoanRecords());
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
    for (final PieChart.Data data : pieChart.getData()) {
      data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
          try {
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

  //사이드바 이동
  @FXML
  public void goHome(ActionEvent event) {
    SceneManager sm = SceneManager.getInstance();
    sm.moveScene(MAIN_ADMIN.getFilePath());
  }

  @FXML
  private void goToBookManage(ActionEvent event) {
    SceneManager sm = SceneManager.getInstance();
    sm.moveScene(BOOK_MANAGEMENT_FX.getFilePath());
  }

  @FXML
  private void goToLoanStatus(ActionEvent event) {
    SceneManager sm = SceneManager.getInstance();
    sm.moveScene(BOOK_LOAN_STATUS_FX.getFilePath()); //변경
  }

  @FXML
  private void goToUserManage(ActionEvent event) {
    SceneManager sm = SceneManager.getInstance();
    sm.moveScene(USER_MANAGEMENT_FX.getFilePath());
  }

  @FXML
  private void goToApplyStatus(ActionEvent event) {
    SceneManager sm = SceneManager.getInstance();
    sm.moveScene(BOOK_APPLY_FX.getFilePath());
  }

  @FXML
  private void toggleChart(ActionEvent actionEvent) {
    isBarChartVisible = !isBarChartVisible;
    barChart.setVisible(isBarChartVisible);
    pieChart.setVisible(!isBarChartVisible);
  }
}
