package org.checkmate.user.controller.view;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.checkmate.common.controller.view.SceneManager;
import org.checkmate.common.util.LoginSession;
import org.checkmate.user.controller.server.BookController;
import org.checkmate.user.dto.response.ReadBookRequestResponseDto;
import org.checkmate.user.dto.response.ReadLoanStatusResponseDto;
import org.checkmate.user.service.BookService;
import org.checkmate.user.service.BookServiceImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import static org.checkmate.user.util.FilePath.*;

/**
 * 도서신청 목록 컨트롤러
 * HISTORY1: 최초 생성                              [권혁규  2024.07.30]
 */
public class ReadBookRequestPageController implements Initializable  {

    private final BookService bookService;

    public ReadBookRequestPageController() {
        bookService = new BookServiceImpl();
    }

    @FXML private Hyperlink userNameLink;
    @FXML private Text tdName;
    @FXML private TableView<ReadBookRequestResponseDto> table_book;
    @FXML private TableColumn<ReadBookRequestResponseDto, Long> rownum;
    @FXML private TableColumn<ReadBookRequestResponseDto, String> loginId;
    @FXML private TableColumn<ReadBookRequestResponseDto, String> eName;
    @FXML private TableColumn<ReadBookRequestResponseDto, String> bName;
    @FXML private TableColumn<ReadBookRequestResponseDto, String> publisher;
    @FXML private TableColumn<ReadBookRequestResponseDto, String> author;
    @FXML private TableColumn<ReadBookRequestResponseDto, String> status;

    ObservableList<ReadBookRequestResponseDto> bookList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadDate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadDate() throws SQLException, SQLException {
        LoginSession session = LoginSession.getInstance();
        var userInfo = session.getUserInfo();
        userNameLink.setText(userInfo.getEName());
        tdName.setText(userInfo.getDName() + "\n" + userInfo.getTName());
        rownum.setCellValueFactory(new PropertyValueFactory<ReadBookRequestResponseDto, Long>("rownum"));
        loginId.setCellValueFactory(new PropertyValueFactory<ReadBookRequestResponseDto, String>("loginId"));
        eName.setCellValueFactory(new PropertyValueFactory<ReadBookRequestResponseDto, String>("eName"));
        bName.setCellValueFactory(new PropertyValueFactory<ReadBookRequestResponseDto, String>("bName"));
        publisher.setCellValueFactory(new PropertyValueFactory<ReadBookRequestResponseDto, String>("publisher"));
        author.setCellValueFactory(new PropertyValueFactory<ReadBookRequestResponseDto, String>("author"));
        status.setCellValueFactory(new PropertyValueFactory<ReadBookRequestResponseDto, String>("status"));
        long deptNo = LoginSession.getInstance().getUserInfo().getDeptNo();
        //BookMapper bm = new BookMapper();
        bookList = bookService.findAllBookRequest(deptNo);
        table_book.setItems(bookList);
    }

    @FXML
    public void bookRequestBtn(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(CREATE_REQUEST_BOOK_FX.getFilePath());
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
