package org.checkmate.user.controller.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.checkmate.common.util.LoginSession;
import org.checkmate.user.controller.server.BookController;
import org.checkmate.user.dto.response.ReadLoanStatusResponseDto;
import org.checkmate.user.service.BookService;
import org.checkmate.user.service.BookServiceImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * 내가 읽은 책 컨트롤러
 * HISTORY1: 최초 생성                              [권혁규  2024.07.29]
 */
public class ReadLoanDoneBookPageController implements Initializable {
    private final BookService bookService;
    private final BookController bookController;

    public ReadLoanDoneBookPageController() {
        bookService = new BookServiceImpl();
        bookController = new BookController();
    }

    @FXML
    private Label Menu;
    @FXML private Label MenuBack;
    @FXML private AnchorPane slider;
    @FXML private TextField searchName;
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
        String loginId = LoginSession.getInstance().getUserInfo().getLoginId();
        bName.setCellValueFactory(new PropertyValueFactory<ReadLoanStatusResponseDto, String>("bName"));
        publisher.setCellValueFactory(new PropertyValueFactory<ReadLoanStatusResponseDto, String>("publisher"));
        author.setCellValueFactory(new PropertyValueFactory<ReadLoanStatusResponseDto, String>("author"));
        loanDate.setCellValueFactory(new PropertyValueFactory<ReadLoanStatusResponseDto, Date>("loanDate"));
        returnDate.setCellValueFactory(new PropertyValueFactory<ReadLoanStatusResponseDto, Date>("returnDate"));
        bookList = bookService.findAllReadMyBooks(loginId);
        table_book.setItems(bookList);
    }
}
