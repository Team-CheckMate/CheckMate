package org.checkmate.admin.controller.view;

import static org.checkmate.admin.util.FilePath.BOOK_LOAN_STATUS_FX;
import static org.checkmate.admin.util.FilePath.BOOK_MANAGEMENT_FX;
import static org.checkmate.admin.util.FilePath.MANAGEMENT_FX;
import static org.checkmate.admin.util.FilePath.RENT_STATUS_CHART_DEPARTMENTS_FX;
import static org.checkmate.admin.util.FilePath.RENT_STATUS_CHART_TEAMS_FX;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.checkmate.admin.controller.server.BookController;
import org.checkmate.admin.dto.response.ReadBookLoanRecordsResponseDto;
import org.checkmate.common.controller.view.SceneManager;

/**
 * 도서 대여 현황
 * HISTORY1: 최초 생성                              [이준희  2024.07.28]
 */
public class BookRentStatusPageController implements Initializable  {

    private final BookController bookController;

    // 기본 생성자
    public BookRentStatusPageController() {
        bookController = new BookController();
    }

    @FXML private Text searchCount;
    @FXML private TextField searchContent;
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
    @FXML private TableColumn<ReadBookLoanRecordsResponseDto, String> status;

    @FXML private void exit(ActionEvent event) {
        Platform.exit();
    } //종료

    ObservableList<ReadBookLoanRecordsResponseDto> bookLoanRecordsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        sm.moveScene("/org/checkmate/view/layouts/admin/userManagementPage.fxml");
    }

    @FXML
    private void goToApplyStatus(ActionEvent event) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/admin/applyStatusViewPage.fxml");
    }

    @FXML
    private void createUserBtn(ActionEvent event) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/admin/userAddPage.fxml");
    }
    public void Msg(String msg,String function) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("도서 관리");
        alert.setHeaderText(function);
        alert.setContentText(msg);
        alert.show();
    }


    private void loadData() throws SQLException {
        loginId.setCellValueFactory(new PropertyValueFactory<ReadBookLoanRecordsResponseDto, String>("loginId"));
        eName.setCellValueFactory(new PropertyValueFactory<ReadBookLoanRecordsResponseDto, String>("eName"));
        dName.setCellValueFactory(new PropertyValueFactory<ReadBookLoanRecordsResponseDto, String>("dName"));
        tName.setCellValueFactory(new PropertyValueFactory<ReadBookLoanRecordsResponseDto, String>("tName"));
        bName.setCellValueFactory(new PropertyValueFactory<ReadBookLoanRecordsResponseDto, String>("bName"));
        loanDate.setCellValueFactory(new PropertyValueFactory<ReadBookLoanRecordsResponseDto, Date>("loanDate"));
        returnPreDate.setCellValueFactory(new PropertyValueFactory<ReadBookLoanRecordsResponseDto, Date>("returnPreDate"));
        returnDate.setCellValueFactory(new PropertyValueFactory<ReadBookLoanRecordsResponseDto, Date>("returnDate"));
        status.setCellValueFactory(new PropertyValueFactory<ReadBookLoanRecordsResponseDto, String>("status"));
        bookLoanRecordsList = bookController.readAllBookLoanRecordsAdmin();
        table_book_loan_records.setItems(bookLoanRecordsList);
        int count = bookLoanRecordsList.size();
        searchCount.setText("총 : "+count+" 건");
        addButtonToTable();
    }

    private void addButtonToTable() {
        Callback<TableColumn<ReadBookLoanRecordsResponseDto, Void>, TableCell<ReadBookLoanRecordsResponseDto, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<ReadBookLoanRecordsResponseDto, Void> call(final TableColumn<ReadBookLoanRecordsResponseDto, Void> param) {
                final TableCell<ReadBookLoanRecordsResponseDto, Void> cell = new TableCell<>() {

                    private final Button modifyBtn = new Button("반납");
                    private final Button deleteBtn = new Button("삭제");

                    {
                        modifyBtn.setStyle("-fx-background-color: transperant; -fx-border-color: #364959 ;");
                        deleteBtn.setStyle("-fx-background-color: transperant; -fx-border-color: #364959 ;");

                        modifyBtn.setOnAction((event) -> {
                            ReadBookLoanRecordsResponseDto data = getTableView().getItems().get(getIndex());
                          try {
                            String msg = bookController.update_return_date(data.getBlrId());
                              Msg(data.getBName()+msg,"반납");
                          } catch (SQLException e) {
                            throw new RuntimeException(e);
                          }
                            SceneManager sm = SceneManager.getInstance();
                            sm.moveScene("/org/checkmate/view/layouts/admin/bookRentStatusPage.fxml");
                        });
                        deleteBtn.setOnAction((event) -> {
                            ReadBookLoanRecordsResponseDto data = getTableView().getItems().get(getIndex());
                          try {
                            String msg = bookController.deleteSelectedBookLoanRecord(data.getBlrId());
                              Msg(data.getBName()+msg,"삭제");
                          } catch (SQLException e) {
                            throw new RuntimeException(e);
                          }
                            SceneManager sm = SceneManager.getInstance();
                            sm.moveScene("/org/checkmate/view/layouts/admin/bookRentStatusPage.fxml");
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
    public void searchBtn(ActionEvent actionEvent) throws SQLException {
        loginId.setCellValueFactory(new PropertyValueFactory<ReadBookLoanRecordsResponseDto, String>("loginId"));
        eName.setCellValueFactory(new PropertyValueFactory<ReadBookLoanRecordsResponseDto, String>("eName"));
        dName.setCellValueFactory(new PropertyValueFactory<ReadBookLoanRecordsResponseDto, String>("dName"));
        tName.setCellValueFactory(new PropertyValueFactory<ReadBookLoanRecordsResponseDto, String>("tName"));
        bName.setCellValueFactory(new PropertyValueFactory<ReadBookLoanRecordsResponseDto, String>("bName"));
        loanDate.setCellValueFactory(new PropertyValueFactory<ReadBookLoanRecordsResponseDto, Date>("loanDate"));
        returnPreDate.setCellValueFactory(new PropertyValueFactory<ReadBookLoanRecordsResponseDto, Date>("returnPreDate"));
        returnDate.setCellValueFactory(new PropertyValueFactory<ReadBookLoanRecordsResponseDto, Date>("returnDate"));
        status.setCellValueFactory(new PropertyValueFactory<ReadBookLoanRecordsResponseDto, String>("status"));
        bookLoanRecordsList = bookController.readBookLoanRecordByNameAdmin(searchContent.getText());
        table_book_loan_records.setItems(bookLoanRecordsList);
        int count = bookLoanRecordsList.size();
        searchCount.setText("총 : "+count+" 건");
        addButtonToTable();
    }

    @FXML
    public void moveToCheckChartByDepartments(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(RENT_STATUS_CHART_DEPARTMENTS_FX.getFilePath());
    }

    @FXML
    public void moveToCheckChartByTeams(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(RENT_STATUS_CHART_TEAMS_FX.getFilePath());
    }

    public void goHome(ActionEvent event) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(MANAGEMENT_FX.getFilePath());
    }
}
