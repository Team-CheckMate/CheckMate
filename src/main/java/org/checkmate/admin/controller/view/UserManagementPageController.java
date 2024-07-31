package org.checkmate.admin.controller.view;

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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.checkmate.admin.dto.response.AdminMemberResponseDto;
import org.checkmate.admin.service.MemberService;
import org.checkmate.admin.service.MemberServiceImpl;
import org.checkmate.common.controller.view.SceneManager;


public class UserManagementPageController implements Initializable {

  MemberService adminMemberService;

  public UserManagementPageController() {
    adminMemberService = new MemberServiceImpl();
  }

  @FXML private Hyperlink userNameLink;
  @FXML private TextField searchContent; //검색 내용
  @FXML private Text searchCount; //검색된 행 개수
  @FXML private TableView<AdminMemberResponseDto> table_admin_user; //테이블
  @FXML private TableColumn<AdminMemberResponseDto, String> loginId; //테이블 열 1 - 로그인 아이디
  @FXML private TableColumn<AdminMemberResponseDto, String> eName;//테이블 열 2 - 사원이름
  @FXML private TableColumn<AdminMemberResponseDto, String> tName;//테이블 열 3 - 팀이름
  @FXML private TableColumn<AdminMemberResponseDto, String> dName;//테이블 열 4 - 부서 이름
  @FXML private TableColumn<AdminMemberResponseDto, Void> manage;//테이블 열 5 - 관리버튼

  @FXML private void exit(ActionEvent event) {
    Platform.exit();
  } //종료

  ObservableList<AdminMemberResponseDto> memberList;

  //사이드바 이동
  @FXML
  private void goToBookManage(ActionEvent event) {
    SceneManager sm = SceneManager.getInstance();
    sm.moveScene("/org/checkmate/view/layouts/admin/bookManagementPage.fxml");
  }

  @FXML
  private void goToLoanStatus(ActionEvent event) {
    SceneManager sm = SceneManager.getInstance();
    sm.moveScene("/org/checkmate/view/layouts/admin/applyStatusViewPage.fxml"); //변경
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

  //메세지 창
  public void Msg(String msg) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("경고!");
    alert.setHeaderText("메세지");
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
    int count = memberList.size();
    searchCount.setText("총 : " + count + " 건");
    addButtonToTable();
  }

  //관리버튼 추가 -> 삽입 초기화 버튼
  private void addButtonToTable() {
    Callback<TableColumn<AdminMemberResponseDto, Void>, TableCell<AdminMemberResponseDto, Void>> cellFactory = new Callback<>() {
      @Override
      public TableCell<AdminMemberResponseDto, Void> call(
          final TableColumn<AdminMemberResponseDto, Void> param) {
        final TableCell<AdminMemberResponseDto, Void> cell = new TableCell<>() {
          private final Button updatePwBtn = new Button("초기화");
          private final Button deleteBtn = new Button("삭제");


          {

            updatePwBtn.setStyle("-fx-background-color: transperant; -fx-border-color: #364959 ;");
            deleteBtn.setStyle("-fx-background-color: transperant; -fx-border-color: #364959 ;");


                updatePwBtn.getStyleClass().add("btn");
            deleteBtn.getStyleClass().add("btn");

            updatePwBtn.setOnAction((event) -> {
              AdminMemberResponseDto data = getTableView().getItems().get(getIndex());

              int result = 0;
              try {
                result = adminMemberService.updatePw(data.getLoginId());

              } catch (SQLException e) {
                throw new RuntimeException(e);
              }
              String msg = result != 0 ? "승인되었습니다" : "승인 실패하였습니다";
              Msg(msg);
              SceneManager sm = SceneManager.getInstance();
              sm.moveScene("/org/checkmate/view/layouts/admin/userManagementPage.fxml");
            });
          }

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
              HBox hBox = new HBox(10); // 버튼 사이의 간격 설정
              hBox.getChildren().addAll(updatePwBtn, deleteBtn);
              setGraphic(hBox);

            }
          }
        };
        return cell;
      }
    };

    manage.setCellFactory(cellFactory);
  }

  //검색 버튼
  @FXML
  public void searchBtn(ActionEvent actionEvent) throws SQLException {
    String searchContent = this.searchContent.getText();
    System.out.println(searchContent);
    loginId.setCellValueFactory(new PropertyValueFactory<>("loginId"));
    eName.setCellValueFactory(new PropertyValueFactory<>("eName"));
    tName.setCellValueFactory(new PropertyValueFactory<>("tName"));
    dName.setCellValueFactory(new PropertyValueFactory<>("dName"));
    memberList = adminMemberService.searchMember(searchContent);
    table_admin_user.setItems(memberList);
    int count = memberList.size();
    searchCount.setText("총 : " + count + " 건");
    addButtonToTable();

  }

  @FXML
  public void goHome(ActionEvent event) {
    SceneManager sm = SceneManager.getInstance();
    sm.moveScene(MAIN_FX.getFilePath());
  }

}

