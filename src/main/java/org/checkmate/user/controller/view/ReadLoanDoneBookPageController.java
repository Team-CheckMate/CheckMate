package org.checkmate.user.controller.view;

import javafx.application.Platform;
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

import static org.checkmate.user.util.FilePath.*;

/**
 * 내가 읽은 책 컨트롤러
 * HISTORY1: 최초 생성                              [권혁규  2024.07.29]
 */
public class ReadLoanDoneBookPageController implements Initializable {
    private final BookService bookService;

    public ReadLoanDoneBookPageController() {
        bookService = new BookServiceImpl();
    }

    @FXML private Hyperlink userNameLink;
    @FXML private Text tdName;
    @FXML private TableView<ReadLoanStatusResponseDto> table_book;
    @FXML private TableColumn<ReadLoanStatusResponseDto, String> bName;
    @FXML private TableColumn<ReadLoanStatusResponseDto, String> publisher;
    @FXML private TableColumn<ReadLoanStatusResponseDto, String> author;
    @FXML private TableColumn<ReadLoanStatusResponseDto, Date> loanDate;
    @FXML private TableColumn<ReadLoanStatusResponseDto, Date> returnDate;

    ObservableList<ReadLoanStatusResponseDto> bookList;

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
        var loginId= session.getUserInfo().getLoginId();
        userNameLink.setText(userInfo.getEName());
        tdName.setText(userInfo.getDName() + "\n" + userInfo.getTName());

        bName.setCellValueFactory(new PropertyValueFactory<ReadLoanStatusResponseDto, String>("bName"));
        publisher.setCellValueFactory(new PropertyValueFactory<ReadLoanStatusResponseDto, String>("publisher"));
        author.setCellValueFactory(new PropertyValueFactory<ReadLoanStatusResponseDto, String>("author"));
        loanDate.setCellValueFactory(new PropertyValueFactory<ReadLoanStatusResponseDto, Date>("loanDate"));
        returnDate.setCellValueFactory(new PropertyValueFactory<ReadLoanStatusResponseDto, Date>("returnDate"));
        bookList = bookService.findAllReadMyBooks(loginId);
        table_book.setItems(bookList);
    }

    @FXML
    public void goHome(ActionEvent actionEvent) {
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

    @FXML
    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }
}
