package org.checkmate.user.controller.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.checkmate.common.controller.view.SceneManager;
import org.checkmate.common.util.LoginSession;
import org.checkmate.user.dto.response.ReadLoanStatusResponseDto;
import org.checkmate.user.service.BookService;
import org.checkmate.user.service.BookServiceImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.AlertType.WARNING;
import static org.checkmate.user.util.FilePath.*;

/**
 * 대여정보(대여중) 컨트롤러
 * HISTORY1: 최초 생성                              [권혁규  2024.07.29]
 */
public class ReadLoanNotReturnBookPageController implements Initializable {

    private final BookService bookService;

    public ReadLoanNotReturnBookPageController() {
        bookService = new BookServiceImpl();
    }

    @FXML private Hyperlink userNameLink;
    @FXML private Text tdName;
    @FXML private TableView<ReadLoanStatusResponseDto> table_book;
    @FXML private TableColumn<ReadLoanStatusResponseDto, String> bName;
    @FXML private TableColumn<ReadLoanStatusResponseDto, String> publisher;
    @FXML private TableColumn<ReadLoanStatusResponseDto, String> author;
    @FXML private TableColumn<ReadLoanStatusResponseDto, Date> loanDate;
    @FXML private TableColumn<ReadLoanStatusResponseDto, Date> returnPreDate;
    @FXML private TableColumn<ReadLoanStatusResponseDto, CheckBox> select;
    @FXML private TextArea overdueTextArea;
    @FXML private Hyperlink overdueLink;

    ObservableList<ReadLoanStatusResponseDto> bookList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String loginId = LoginSession.getInstance().getUserInfo().getLoginId();
        try {
            loadDate();
            int overdueBookCnt = bookService.getOverdueBookCount(loginId);
            setCheckOverdue(overdueBookCnt);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void returnLoanBookBtn(ActionEvent actionEvent) {
        ObservableList<ReadLoanStatusResponseDto> selectedBooks  = FXCollections.observableArrayList();

        for(ReadLoanStatusResponseDto bean : bookList) {
            if(bean.getSelect().isSelected()) {
                selectedBooks.add(bean);
                System.out.println("선택한 도서 반납 정보 = " + bean);
            }
        }

        if (selectedBooks.isEmpty()) {
            showAlert("반납할 도서가 선택되지 않았습니다.");
            return;
        }
        bookService.updateReturnLoanBook(selectedBooks);
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(READ_NOT_RENT_LOAN_BOOK_FX.getFilePath());
    }

    private void loadDate() throws SQLException, SQLException {
        LoginSession session = LoginSession.getInstance();
        var userInfo = session.getUserInfo();
        var loginId= session.getUserInfo().getLoginId();
        userNameLink.setText(userInfo.getEName());
        tdName.setText(userInfo.getDName() + "\n" + userInfo.getTName());

        select.setCellValueFactory(new PropertyValueFactory<ReadLoanStatusResponseDto, CheckBox>("select"));
        bName.setCellValueFactory(new PropertyValueFactory<ReadLoanStatusResponseDto, String>("bName"));
        publisher.setCellValueFactory(new PropertyValueFactory<ReadLoanStatusResponseDto, String>("publisher"));
        author.setCellValueFactory(new PropertyValueFactory<ReadLoanStatusResponseDto, String>("author"));
        loanDate.setCellValueFactory(new PropertyValueFactory<ReadLoanStatusResponseDto, Date>("loanDate"));
        returnPreDate.setCellValueFactory(new PropertyValueFactory<ReadLoanStatusResponseDto, Date>("returnPreDate"));
        bookList = bookService.findLoanBookNotReturnByLoginId(loginId);
        table_book.setItems(bookList);
        //totalCnt.setText("총 대여 건수 : " + bookList.size());
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(WARNING);
        alert.setTitle("도서");
        alert.setHeaderText("대여정보");
        alert.setContentText(msg);
        alert.show();
    }

    /**
     * 내가 읽은 책 이동 이벤트 처리
     */
    @FXML
    public void readDoneBookBtn(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(READ_DONE_LOAN_BOOK_FX.getFilePath());
    }

    /**
     * 연체 건수
     */
    private int getOverdueBookCount(String loginId) {

        return bookService.getOverdueBookCount(loginId);
    }

    private void setCheckOverdue(int overdueCnt) {
        if(overdueCnt > 0) {
//            overdueTextArea.setVisible(true);
//            overdueTextArea.setText("반납 기한이 지난 도서가 존재합니다.(" + overdueCnt + "권)\n 즉시, 반납 부탁드립니다.");
            overdueLink.setVisible(true);
            overdueLink.setText("반납 기한이 지난 도서가 존재합니다.(" + overdueCnt + "권) 즉시, 반납 부탁드립니다.");
        } else {
            overdueLink.setVisible(false);
//            overdueTextArea.setVisible(false);
        }
    }

    @FXML
    public void moveOverduePage(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(READ_OVERDUE_LOAN_BOOK_FX.getFilePath());
    }
    
    @FXML
    public void goHome(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(MAIN_FX.getFilePath());
    }

    @FXML
    public void goToBookLoan(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(READ_RENT_LOAN_BOOK_FX.getFilePath());
    }

    @FXML
    public void goToLoanManage(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(READ_NOT_RENT_LOAN_BOOK_FX.getFilePath());
    }
    
    @FXML
    public void goToMyLoanBook(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(READ_TM_LOAN_STATUS_FX.getFilePath());
    }

    @FXML
    public void goToBookApply(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(READ_REQUEST_BOOK_FX.getFilePath());
    }

    @FXML
    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }
}
