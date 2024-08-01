package org.checkmate.admin.controller.view;

import static org.checkmate.admin.util.FilePath.BOOK_APPLY_FX;
import static org.checkmate.admin.util.FilePath.BOOK_LOAN_STATUS_FX;
import static org.checkmate.admin.util.FilePath.BOOK_MANAGEMENT_FX;
import static org.checkmate.admin.util.FilePath.USER_MANAGEMENT_FX;
import static org.checkmate.user.util.FilePath.MAIN_FX;

import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.checkmate.admin.dto.response.ApplyStatusResponseDto;
import org.checkmate.admin.service.ApplyService;
import org.checkmate.admin.service.ApplyServiceImpl;
import org.checkmate.common.controller.view.SceneManager;


public class ApplyStatusViewPageController implements Initializable {
    ApplyService applyService;
    public ApplyStatusViewPageController() {
        applyService = new ApplyServiceImpl() ;
    }

    @FXML private TableView<ApplyStatusResponseDto> table_admin_user;
    @FXML private TableColumn<ApplyStatusResponseDto, String> loginId;
    @FXML private TableColumn<ApplyStatusResponseDto, String> eName;
    @FXML private TableColumn<ApplyStatusResponseDto, String> bName;
    @FXML private TableColumn<ApplyStatusResponseDto, String> publisher;
    @FXML private TableColumn<ApplyStatusResponseDto, String> author;
    @FXML private TableColumn<ApplyStatusResponseDto, Void> manage;

    //시스템 종료
    @FXML private void exit(ActionEvent event) {
        Platform.exit();
    }

    //사이드바 이동
    //사이드바 이동
    @FXML private void goToBookManage(ActionEvent event)
    {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(BOOK_MANAGEMENT_FX.getFilePath());
    }
    @FXML private void goToLoanStatus(ActionEvent event)
    {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(BOOK_LOAN_STATUS_FX.getFilePath()); //변경
    }
    @FXML private void goToUserManage(ActionEvent event)
    {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(USER_MANAGEMENT_FX.getFilePath());
    }
    @FXML private void goToApplyStatus(ActionEvent event)
    {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(BOOK_APPLY_FX.getFilePath());
    }

    @FXML
    public void goHome(ActionEvent event) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(MAIN_FX.getFilePath());
    }

    ObservableList<ApplyStatusResponseDto> applyList;

    //메세지 출력
    public void Msg(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("경고!");
        alert.setHeaderText("message");
        alert.setContentText(msg);
        alert.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadDate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void loadDate() throws Exception {
        loginId.setCellValueFactory(new PropertyValueFactory<>("loginId"));
        eName.setCellValueFactory(new PropertyValueFactory<>("eName"));
        bName.setCellValueFactory(new PropertyValueFactory<>("bName"));
        publisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        applyList = applyService.readApplyStatus();
        table_admin_user.setItems(applyList);
        addButtonToTable();
    }
        //승인 , 반려 버튼 처리
    private void addButtonToTable() {
        Callback<TableColumn<ApplyStatusResponseDto, Void>, TableCell<ApplyStatusResponseDto, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<ApplyStatusResponseDto, Void> call(final TableColumn<ApplyStatusResponseDto, Void> param) {
                final TableCell<ApplyStatusResponseDto, Void> cell = new TableCell<>() {
                    private final Button createBtn = new Button("승인");
                    private final Button deleteBtn = new Button("반려");
                    {
                        createBtn.setStyle("-fx-background-color: transperant; -fx-border-color: #364959 ;");
                        deleteBtn.setStyle("-fx-background-color: transperant; -fx-border-color: #364959 ;");


                        createBtn.setOnAction((event) -> {
                            ApplyStatusResponseDto data = getTableView().getItems().get(getIndex());

                            int result = 0;
                            try {
                                result = applyService.updateRequestDate(data.getBrId());

                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            String msg = result != 0 ? "승인되었습니다" : "승인 실패하였습니다";
                            Msg(msg);
                            SceneManager sm = SceneManager.getInstance();
                            sm.moveScene("/org/checkmate/view/layouts/admin/applyStatusViewPage.fxml");
//                            System.out.println(msg);
                        });
                    }

                    {
                        deleteBtn.setOnAction((event) -> {
                            ApplyStatusResponseDto data = getTableView().getItems().get(getIndex());

                            int result = 0;
                            try {
                                result = applyService.updateReturnDate(data.getBrId());

                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            String msg = result != 0 ? "반려되었습니다" : "반려 실패하였습니다";
                            Msg(msg);
                            SceneManager sm = SceneManager.getInstance();
                            sm.moveScene("/org/checkmate/view/layouts/admin/applyStatusViewPage.fxml");

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox hBox = new HBox(10); // 버튼 사이의 간격 설정
                            hBox.getChildren().addAll(createBtn, deleteBtn);
                            setGraphic(hBox);

                        }
                    }
                };
                return cell;
            }
        };
        manage.setCellFactory(cellFactory);
    }

}

