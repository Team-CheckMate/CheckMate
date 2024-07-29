package org.checkmate.admin.controller.view;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.Duration;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import org.checkmate.admin.dto.response.AdminMember;
import org.checkmate.admin.mapper.UserManagementMapper;


public class UserManagementPageController implements Initializable {

    @FXML private Label Menu;
    @FXML private Label MenuBack;
    @FXML private AnchorPane slider;
    @FXML private TableView<AdminMember> table_admin_user;
    @FXML private TableColumn<AdminMember, String> login_id;
    @FXML private TableColumn<AdminMember, String> e_name;
    @FXML private TableColumn<AdminMember, String> t_name;
    @FXML private TableColumn<AdminMember, String> d_name;
    @FXML private TableColumn<AdminMember, CheckBox> select;
    @FXML private TableColumn<AdminMember, Void> deletebtn;

    @FXML private void exit(ActionEvent event) {
        Platform.exit();
    }

    ObservableList<AdminMember> memberList;

    @FXML
    private void userRegisterbtn(ActionEvent event) {
        System.out.println("사용자 등록"); //등록 부분 처리 예정
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

        slider.setTranslateX(-176);

        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-176);

            slide.setOnFinished((ActionEvent e) -> {
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

            slide.setOnFinished((ActionEvent e) -> {
                Menu.setVisible(true);
                MenuBack.setVisible(false);
            });
        });
    }

    private void loadDate() throws SQLException {
        select.setCellValueFactory(new PropertyValueFactory<>("select"));
        login_id.setCellValueFactory(new PropertyValueFactory<>("login_id"));
        e_name.setCellValueFactory(new PropertyValueFactory<>("e_name"));
        t_name.setCellValueFactory(new PropertyValueFactory<>("t_name"));
        d_name.setCellValueFactory(new PropertyValueFactory<>("d_name"));
        UserManagementMapper bm = new UserManagementMapper();
        memberList = bm.findByMember();
        table_admin_user.setItems(memberList);
        addButtonToTable();
    }

    private void addButtonToTable() {
        Callback<TableColumn<AdminMember, Void>, TableCell<AdminMember, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<AdminMember, Void> call(final TableColumn<AdminMember, Void> param) {
                final TableCell<AdminMember, Void> cell = new TableCell<>() {

                    private final Button deleteBtn = new Button("삭제");

                    {
                        UserManagementMapper bm = new UserManagementMapper();
                        deleteBtn.setOnAction((event) -> {
                            AdminMember data = getTableView().getItems().get(getIndex());

                            int result = 0;
                            try {
                                result = bm.userDelete(data.getLogin_id());

                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            String msg = result != 0 ? "삭제되었습니다" : "삭제 실패하였습니다";
                            Msg("사원번호 " + data.getLogin_id() + msg);
                            System.out.println(msg);
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

        deletebtn.setCellFactory(cellFactory);
    }
}

