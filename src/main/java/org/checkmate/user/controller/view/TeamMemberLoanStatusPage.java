package org.checkmate.user.controller.view;

import static org.checkmate.user.util.FilePath.READ_TM_LOAN_STATUS_FX;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;
import lombok.RequiredArgsConstructor;
import org.checkmate.common.controller.view.SceneManager;
import org.checkmate.common.util.LoginSession;
import org.checkmate.user.controller.server.BookController;
import org.checkmate.user.dto.response.TeamMemberLoanStatusDegree;
import org.checkmate.user.dto.response.TeamMemberLoanStatusForView;

@RequiredArgsConstructor
public class TeamMemberLoanStatusPage implements Initializable {

    private final BookController server = new BookController();
    private String eName;
    private String tName;
    private int totalLoanBook;
    private int totalLastMonthLoanBook;
    private int totalLastYearLoanBook;

    @FXML
    private Hyperlink userNameLink;
    @FXML
    private Text tdName;
    @FXML
    private Text infoText;
    @FXML
    private TableView<TeamMemberLoanStatusDegree> tableView;  // TableView 사용 데이터 타입
    @FXML
    private TableColumn<TeamMemberLoanStatusDegree, Integer> column1; // 번호 (행 번호)
    @FXML
    private TableColumn<TeamMemberLoanStatusDegree, String> column2;  // 사원 번호 (loginId)
    @FXML
    private TableColumn<TeamMemberLoanStatusDegree, String> column3;  // 이름 (eName)
    @FXML
    private TableColumn<TeamMemberLoanStatusDegree, Integer> column4; // 현재 대여 도서 수 (bookCount)
    @FXML
    private TableColumn<TeamMemberLoanStatusDegree, Integer> column5; // 이번 달 읽은 도서 수 (currentMonthCount)
    @FXML
    private TableColumn<TeamMemberLoanStatusDegree, Integer> column6; // 지난 달 읽은 도서 수 (lastMonthCount)
    @FXML
    private TableColumn<TeamMemberLoanStatusDegree, Integer> column7; // 작년의 읽은 도서 수 (lastYearCount)

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void goToBookLoan(ActionEvent event) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/user/readLoanBookPage.fxml");
    }

    @FXML
    private void goToLoanManage(ActionEvent event) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/user/readLoanBookPage.fxml"); //변경
    }

    @FXML
    private void goToMyLoanBook(ActionEvent event) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(READ_TM_LOAN_STATUS_FX.getFilePath());
    }

    @FXML
    private void goToBookApply(ActionEvent event) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/user/readLoanBookPage.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var session = LoginSession.getInstance();
        var userInfo = session.getUserInfo();
        userNameLink.setText(userInfo.getEName());
        tdName.setText(userInfo.getDName() + " \n -> " + userInfo.getTName());

        // 각 컬럼에 데이터 바인딩
        column2.setCellValueFactory(new PropertyValueFactory<>("loginId"));
        column3.setCellValueFactory(new PropertyValueFactory<>("eName"));
        column4.setCellValueFactory(new PropertyValueFactory<>("bookCount"));
        column5.setCellValueFactory(new PropertyValueFactory<>("currentMonthCount"));
        column6.setCellValueFactory(new PropertyValueFactory<>("lastMonthCount"));
        column7.setCellValueFactory(new PropertyValueFactory<>("lastYearCount"));

        // 행 번호 설정
        column1.setCellFactory(new Callback<>() {
            @Override
            public TableCell<TeamMemberLoanStatusDegree, Integer> call(
                    TableColumn<TeamMemberLoanStatusDegree, Integer> param) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (this.getTableRow() != null && !empty) {
                            int index = this.getTableRow().getIndex();
                            setText(index + 1 + "");
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        loadData(userInfo.getLoginId(), userInfo.getTeamNo());
    }

    private void loadData(String loginId, Long teamNo) {
        TeamMemberLoanStatusForView dataResponse = server.teamMemberLoanStatus(loginId, teamNo);
        int totalLoanBook = dataResponse.getTotalLoanBook(); // 총 빌린 도서 수
        System.out.println("totalLoanBook = " + totalLoanBook);
        int totalLastMonthLoanBook = dataResponse.getTotalLastMonthLoanBook();
        System.out.println("totalLastMonthLoanBook = " + totalLastMonthLoanBook);
        int totalLastYearBook = dataResponse.getTotalLastYearBook();
        System.out.println("totalLastYearBook = " + totalLastYearBook);
        List<TeamMemberLoanStatusDegree> dataList = dataResponse.getList();
        ObservableList<TeamMemberLoanStatusDegree> data = FXCollections.observableArrayList(
                dataList);
        tableView.setItems(data);
    }

    private String informationStringBuilder(String eName, String tName, int totalLoanBook,
            int totalLastMonthLoanBook, int totalLastYearLoanBook) {
        return eName
                + "님이 속한 " + '\'' + tName + '\'' + " 팀은 현재 "
                + totalLoanBook + "권을 대여 중이며," + '\n'
                + "저번 달에는 " + totalLastMonthLoanBook + "권의 도서를 읽었습니다."
                + '\n'
                + "작년에는 " + totalLastYearLoanBook + "권의 도서를 읽으셨어요!";
    }
}
