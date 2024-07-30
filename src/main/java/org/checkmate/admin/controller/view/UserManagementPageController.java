package org.checkmate.admin.controller.view;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.Duration;
import org.checkmate.admin.dto.response.AdminMemberResponseDto;
import org.checkmate.admin.service.AdminMemberService;
import org.checkmate.admin.service.AdminMemberServiceImpl;
import org.checkmate.common.controller.view.SceneManager;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class UserManagementPageController implements Initializable {
  
    @FXML
    private Label Menu;

    @FXML
    private Label MenuBack;

    @FXML
    private AnchorPane slider;
    AdminMemberService adminMemberService;
    public UserManagementPageController(){
        adminMemberService = new AdminMemberServiceImpl();
    }

    @FXML
    private TableView<AdminMemberResponseDto> table_admin_user;

    @FXML
    private TableColumn<AdminMemberResponseDto, String> loginId;

    @FXML
    private TableColumn<AdminMemberResponseDto, String> eName;

    @FXML
    private TableColumn<AdminMemberResponseDto, String> tName;

    @FXML
    private TableColumn<AdminMemberResponseDto, String> dName;


    @FXML
    private TableColumn<AdminMemberResponseDto, Void> delete;

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }


    ObservableList<AdminMemberResponseDto> memberList;

    @FXML
    private void createUserBtn(ActionEvent event) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/admin/userAddPage.fxml");
    }

    public void Msg(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("경고!");
        alert.setHeaderText("삭제");
        alert.setContentText(msg);
        alert.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            loadDate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadDate() throws SQLException {
        loginId.setCellValueFactory(new PropertyValueFactory<>("loginId"));
        eName.setCellValueFactory(new PropertyValueFactory<>("eName"));
        tName.setCellValueFactory(new PropertyValueFactory<>("tName"));
        dName.setCellValueFactory(new PropertyValueFactory<>("dName"));

        memberList = adminMemberService.findByMember();
        table_admin_user.setItems(memberList);
       delete();
    }

    private void delete() {
        Callback<TableColumn<AdminMemberResponseDto, Void>, TableCell<AdminMemberResponseDto, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<AdminMemberResponseDto, Void> call(final TableColumn<AdminMemberResponseDto, Void> param) {
                final TableCell<AdminMemberResponseDto, Void> cell = new TableCell<>() {

                    private final Button deleteBtn = new Button("삭제");

                    {
                        deleteBtn.setOnAction((event) -> {
                            AdminMemberResponseDto data = getTableView().getItems().get(getIndex());

                            int result = 0;
                            try {
                                result = adminMemberService.deleteUser(data.getLoginId());

                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            String msg = result != 0 ? "삭제되었습니다" : "삭제 실패하였습니다";
                            Msg("사원번호 " + data.getLoginId() + msg);
                            System.out.println(msg);
                            SceneManager sm = SceneManager.getInstance();
                            sm.moveScene("/org/checkmate/view/layouts/admin/userManagementPage.fxml");
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(deleteBtn);
                        }
                    }
                };
                return cell;
            }
        };

        delete.setCellFactory(cellFactory);
    }

}

