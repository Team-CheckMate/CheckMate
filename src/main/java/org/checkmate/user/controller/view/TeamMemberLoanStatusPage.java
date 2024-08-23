package org.checkmate.user.controller.view;

import static org.checkmate.user.util.FilePath.BOOK_LOAN;
import static org.checkmate.user.util.FilePath.MAIN_FX;
import static org.checkmate.user.util.FilePath.READ_NOT_RENT_LOAN_BOOK_FX;
import static org.checkmate.user.util.FilePath.READ_REQUEST_BOOK_FX;
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
import lombok.extern.log4j.Log4j2;
import org.checkmate.common.controller.view.SceneManager;
import org.checkmate.common.dto.response.CommonResponse;
import org.checkmate.common.util.LoginSession;
import org.checkmate.user.controller.server.BookController;
import org.checkmate.user.dto.response.TeamMemberLoanStatusDegree;
import org.checkmate.user.dto.response.TeamMemberLoanStatusForView;

@Log4j2
@RequiredArgsConstructor
public class TeamMemberLoanStatusPage implements Initializable {

    private final BookController server = new BookController();

    @FXML private Hyperlink userNameLink;
    @FXML private Text tdName;
    @FXML private Text deptNameText;
    @FXML private Text infoText;
    @FXML private Text anaMsg;
    @FXML private TableView<TeamMemberLoanStatusDegree> tableView;  // TableView 사용 데이터 타입
    @FXML private TableColumn<TeamMemberLoanStatusDegree, Integer> column1; // 번호 (행 번호)
    @FXML private TableColumn<TeamMemberLoanStatusDegree, String> column2;  // 사원 번호 (loginId)
    @FXML private TableColumn<TeamMemberLoanStatusDegree, String> column3;  // 이름 (eName)
    @FXML private TableColumn<TeamMemberLoanStatusDegree, Integer> column4; // 현재 대여 도서 수 (bookCount)
    @FXML private TableColumn<TeamMemberLoanStatusDegree, Integer> column5; // 이번 달 읽은 도서 수 (currentMonthCount)
    @FXML private TableColumn<TeamMemberLoanStatusDegree, Integer> column6; // 지난 달 읽은 도서 수 (lastMonthCount)
    @FXML private TableColumn<TeamMemberLoanStatusDegree, Integer> column7; // 작년의 읽은 도서 수 (lastYearCount)

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void goHome(ActionEvent event) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(MAIN_FX.getFilePath());
    }

    @FXML
    private void goToBookLoan(ActionEvent event) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(BOOK_LOAN.getFilePath());
    }

    @FXML
    private void goToLoanManage(ActionEvent event) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(READ_NOT_RENT_LOAN_BOOK_FX.getFilePath());
    }

    @FXML
    private void goToMyLoanBook(ActionEvent event) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(READ_TM_LOAN_STATUS_FX.getFilePath());
    }

    @FXML
    private void goToBookApply(ActionEvent event) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(READ_REQUEST_BOOK_FX.getFilePath());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info(" <<< [ 📢 LoginSession Call ]");
        LoginSession session = LoginSession.getInstance();
        var userInfo = session.getUserInfo();
        log.info(" >>> [ ✅ LoginSession Successfully called! Get \"UserInfo\" ]");
        userNameLink.setText(userInfo.getEName());
        deptNameText.setText(userInfo.getTName() + " 도서 대여 분석");
        tdName.setText(userInfo.getDName() + "\n" + userInfo.getTName());

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
        log.info(" <<< [ 📢 BookController를 호출하여 데이터를 로드: loginId = \"{}\", teamNo = \"{}\" ]", loginId, teamNo);

        CommonResponse<TeamMemberLoanStatusForView> response = server.teamMemberLoanStatus(loginId, teamNo);
        log.info(" >>> [ ✅ 서버로부터 응답 수신 완료. ]");

        TeamMemberLoanStatusForView resData = response.getData();
        log.info(" >>> [ ✅ 응답 데이터 처리 시작. ]");

        int totalLoanBook = resData.getTotalLoanBook(); // 총 빌린 도서 수
        int totalLastMonthLoanBook = resData.getTotalLastMonthLoanBook(); // 지난달 대여 도서 수
        int totalLastYearBook = resData.getTotalLastYearBook(); // 작년 대여 도서 수
        log.info(" >>> [ 📊 대출 도서 데이터: 총 대출 = {}, 지난달 대출 = {}, 작년 대출 = {} ]", totalLoanBook, totalLastMonthLoanBook, totalLastYearBook);

        String[] analysis = readAnalysis(totalLoanBook, totalLastMonthLoanBook, totalLastYearBook);
        log.info(" >>> [ 🔍 분석 결과 생성 ]" );

        List<TeamMemberLoanStatusDegree> dataList = resData.getList();
        log.info(" >>> [ 📋 데이터 리스트 크기: {} ]", dataList.size());

        ObservableList<TeamMemberLoanStatusDegree> data = FXCollections.observableArrayList(
                dataList);
        tableView.setItems(data);
        log.info(" >>> [ ✅ TableView에 데이터 설정 완료. ]");

        infoText.setText(analysis[0]);
        anaMsg.setText(analysis[1]);
        log.info(" >>> [ ✅ UI 업데이트 완료 ]");
    }

    private String[] readAnalysis(int totalLoanBook, int totalLastMonthLoanBook, int totalLastYearBook) {
        double monthChangePercent = calculateMonthChangePercent(totalLoanBook,
                totalLastMonthLoanBook);
        double yearAverageBooks = calculateYearAverageBooks(totalLastYearBook);

        String info = String.format(
                "총 대여 도서 수: %d권\n" +
                "지난달 대여 도서 수: %d권\n" +
                "작년 대여 도서 수: %d권",
                totalLoanBook, totalLastMonthLoanBook, totalLastYearBook
        );

        String analysis = String.format(
                "이번 달에는 지난달에 비해 %.2f%% %s했습니다.\n" +
                "작년과 비교해보니 이대로라면 평균 %.2f권의 책을 읽을 수 있을 것 같습니다.",
                Math.abs(monthChangePercent), monthChangePercent >= 0 ? "증가" : "감소",
                yearAverageBooks
        );

        return new String[]{info, analysis};
    }

    private double calculateMonthChangePercent(int totalLoanBook, int totalLastMonthLoanBook) {
        if (totalLastMonthLoanBook != 0) {
            return ((double) (totalLoanBook - totalLastMonthLoanBook) / totalLastMonthLoanBook) * 100;
        }
        return 0;
    }

    private double calculateYearAverageBooks(int totalLastYearBook) {
        if (totalLastYearBook != 0) {
            return (double) totalLastYearBook / 12;
        }
        return 0;
    }

}
