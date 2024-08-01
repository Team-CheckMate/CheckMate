package org.checkmate.user.controller.view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.checkmate.common.controller.view.SceneManager;
import org.checkmate.user.controller.server.BookController;
import org.checkmate.user.dto.response.ReadBookRequestResponseDto;
import org.checkmate.user.dto.response.ReadLoanStatusResponseDto;
import org.checkmate.user.service.BookService;
import org.checkmate.user.service.BookServiceImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import static org.checkmate.user.util.FilePath.CREATE_REQUEST_BOOK_FX;
import static org.checkmate.user.util.FilePath.READ_OVERDUE_LOAN_BOOK_FX;

/**
 * 도서신청 목록 컨트롤러
 * HISTORY1: 최초 생성                              [권혁규  2024.07.30]
 */
public class ReadBookRequestPageController implements Initializable  {

    private final BookService bookService;

    public ReadBookRequestPageController() {
        bookService = new BookServiceImpl();
    }

    @FXML private Label Menu;
    @FXML private Label MenuBack;
    @FXML private AnchorPane slider;
    @FXML private TextField searchName;
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
        rownum.setCellValueFactory(new PropertyValueFactory<ReadBookRequestResponseDto, Long>("rownum"));
        loginId.setCellValueFactory(new PropertyValueFactory<ReadBookRequestResponseDto, String>("loginId"));
        eName.setCellValueFactory(new PropertyValueFactory<ReadBookRequestResponseDto, String>("eName"));
        bName.setCellValueFactory(new PropertyValueFactory<ReadBookRequestResponseDto, String>("bName"));
        publisher.setCellValueFactory(new PropertyValueFactory<ReadBookRequestResponseDto, String>("publisher"));
        author.setCellValueFactory(new PropertyValueFactory<ReadBookRequestResponseDto, String>("author"));
        status.setCellValueFactory(new PropertyValueFactory<ReadBookRequestResponseDto, String>("status"));

        //BookMapper bm = new BookMapper();
        bookList = bookService.findAllBookRequest("1");
        table_book.setItems(bookList);
    }

    @FXML
    public void bookRequestBtn(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(CREATE_REQUEST_BOOK_FX.getFilePath());
    }
}
