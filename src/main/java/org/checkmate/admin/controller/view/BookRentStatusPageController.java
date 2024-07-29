package org.checkmate.admin.controller.view;

import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.checkmate.admin.dto.response.ReadBookLoanRecordsResponseDto;
import org.checkmate.admin.service.BookManagementService;
import org.checkmate.admin.service.BookManagementServiceImpl;
import org.checkmate.common.controller.view.SceneManager;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * 도서 대여 현황
 * HISTORY1: 최초 생성                              [이준희  2024.07.28]
 */
public class BookRentStatusPageController implements Initializable  {

    private final BookManagementService bookService;

    // 기본 생성자
    public BookRentStatusPageController() {
        bookService = new BookManagementServiceImpl();
    }

    @FXML private Label Menu;
    @FXML private Label MenuBack;
    @FXML private AnchorPane slider;
    @FXML private Text countMessage;
    @FXML private TableView<ReadBookLoanRecordsResponseDto> table_book_loan_records;
    @FXML private TableColumn<ReadBookLoanRecordsResponseDto, Long> blrId;
    @FXML private TableColumn<ReadBookLoanRecordsResponseDto, String> loginId;
    @FXML private TableColumn<ReadBookLoanRecordsResponseDto, String> eName;
    @FXML private TableColumn<ReadBookLoanRecordsResponseDto, String> dName;
    @FXML private TableColumn<ReadBookLoanRecordsResponseDto, String> tName;
    @FXML private TableColumn<ReadBookLoanRecordsResponseDto, String> bName;
    @FXML private TableColumn<ReadBookLoanRecordsResponseDto, Date> loanDate;
    @FXML private TableColumn<ReadBookLoanRecordsResponseDto, Date> returnPreDate;
    @FXML private TableColumn<ReadBookLoanRecordsResponseDto, Date> returnDate;
    @FXML private TableColumn<ReadBookLoanRecordsResponseDto, Void> manage;

    ObservableList<ReadBookLoanRecordsResponseDto> bookLoanRecordsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        slider.setTranslateX(-176);

        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-176);

            slide.setOnFinished((ActionEvent e)->{
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

            slide.setOnFinished((ActionEvent e)->{
                Menu.setVisible(true);
                MenuBack.setVisible(false);
            });
        });
    }

    public void Msg(String msg,String function) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("도서 관리");
        alert.setHeaderText(function);
        alert.setContentText(msg);
        alert.show();
    }


    private void loadData() throws SQLException {
        blrId.setCellValueFactory(new PropertyValueFactory<ReadBookLoanRecordsResponseDto,Long>("blrId"));
        loginId.setCellValueFactory(new PropertyValueFactory<ReadBookLoanRecordsResponseDto, String>("loginId"));
        eName.setCellValueFactory(new PropertyValueFactory<ReadBookLoanRecordsResponseDto, String>("eName"));
        dName.setCellValueFactory(new PropertyValueFactory<ReadBookLoanRecordsResponseDto, String>("dName"));
        tName.setCellValueFactory(new PropertyValueFactory<ReadBookLoanRecordsResponseDto, String>("tName"));
        bName.setCellValueFactory(new PropertyValueFactory<ReadBookLoanRecordsResponseDto, String>("bName"));
        loanDate.setCellValueFactory(new PropertyValueFactory<ReadBookLoanRecordsResponseDto, Date>("loanDate"));
        returnPreDate.setCellValueFactory(new PropertyValueFactory<ReadBookLoanRecordsResponseDto, Date>("returnPreDate"));
        returnDate.setCellValueFactory(new PropertyValueFactory<ReadBookLoanRecordsResponseDto, Date>("returnDate"));
        bookLoanRecordsList = bookService.readAllBookLoanRecordsAdmin();
        table_book_loan_records.setItems(bookLoanRecordsList);
        int count = bookLoanRecordsList.size();
        countMessage.setText("총 : "+count+" 건");
        addButtonToTable();
    }

    private void addButtonToTable() {
        Callback<TableColumn<ReadBookLoanRecordsResponseDto, Void>, TableCell<ReadBookLoanRecordsResponseDto, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<ReadBookLoanRecordsResponseDto, Void> call(final TableColumn<ReadBookLoanRecordsResponseDto, Void> param) {
                final TableCell<ReadBookLoanRecordsResponseDto, Void> cell = new TableCell<>() {

                    private final Button modifyBtn = new Button("수정");
                    private final Button deleteBtn = new Button("삭제");

                    {
                        modifyBtn.setOnAction((event) -> {
                        });

                        deleteBtn.setOnAction((event) -> {
                            ReadBookLoanRecordsResponseDto data = getTableView().getItems().get(getIndex());
                            System.out.println("Selected Data: " + data);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            // 버튼들을 HBox에 추가
                            HBox hBox = new HBox(10); // 버튼 사이의 간격 설정
                            hBox.getChildren().addAll(modifyBtn, deleteBtn);
                            setGraphic(hBox);
                        }
                    }
                };
                return cell;
            }
        };

        manage.setCellFactory(cellFactory);
    }

    @FXML
    public void moveToCheckChartByDepartments(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/admin/bookRentChartPageForDepartments.fxml");
    }

    @FXML
    public void moveToCheckChartByTeams(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/admin/bookRentChartPageForTeams.fxml");
    }
}