package org.checkmate.user.controller.view;

import static javafx.scene.control.Alert.AlertType.WARNING;
import static org.checkmate.user.util.FilePath.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import lombok.RequiredArgsConstructor;
import org.checkmate.common.controller.view.SceneManager;
import org.checkmate.common.util.LoginSession;
import org.checkmate.user.controller.server.BookController;
import org.checkmate.user.dto.request.CreateBookLoanRequestDto;
import org.checkmate.user.dto.request.ReadSearchLoanStatusRequestDto;
import org.checkmate.user.dto.response.CreateBookLoanResponseDto;
import org.checkmate.user.dto.response.ReadLoanStatusResponseDto;
import org.checkmate.user.dto.response.ReadSearchLoanStatusResponseDto;
import org.checkmate.user.service.BookService;
import org.checkmate.user.service.BookServiceImpl;

/**
 * 도서대여 컨트롤러
 * HISTORY1: 최초 생성                              [권혁규  2024.07.29]
 */
@RequiredArgsConstructor
public class ReadLoanBookPageController implements Initializable {

    private final BookService bookService = new BookServiceImpl();
    private final BookController bookController = new BookController();

    @FXML private Hyperlink userNameLink;
    @FXML private Text tdName;
    @FXML private TextField searchName;
    @FXML private TableView<ReadLoanStatusResponseDto> table_book;
    @FXML private TableColumn<ReadLoanStatusResponseDto, String> bName;
    @FXML private TableColumn<ReadLoanStatusResponseDto, String> publisher;
    @FXML private TableColumn<ReadLoanStatusResponseDto, String> author;
    @FXML private TableColumn<ReadLoanStatusResponseDto, Boolean> lStatus;
    @FXML private TableColumn<ReadLoanStatusResponseDto, Date> date;
    @FXML private TableColumn<ReadLoanStatusResponseDto, CheckBox> select;
    @FXML private Text totalCnt;

    ObservableList<ReadLoanStatusResponseDto> bookList;

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
        var instance = LoginSession.getInstance();
        var userInfo = instance.getUserInfo();
        userNameLink.setText(userInfo.getEName());
        tdName.setText(userInfo.getDName() + '\n' + userInfo.getTName());

        try {
            loadDate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadDate() throws SQLException, SQLException {
        select.setCellValueFactory(
                new PropertyValueFactory<ReadLoanStatusResponseDto, CheckBox>("select"));
//        select.setCellFactory(new Callback<>() {
//            public TableCell<BookLoanStatus, CheckBox> call(TableColumn<BookLoanStatus, CheckBox> param) {
//              return new TableCell<>() {
//                  private final CheckBox checkBox = new CheckBox();
//                  @Override
//                  protected void updateItem(CheckBox checkBox, boolean empty) {
//                      super.updateItem(checkBox, empty);
//                      if (empty) {
//                          setGraphic(null);
//                      } else {
//                          BookLoanStatus currentRecord = getTableView().getItems().get(getIndex());
//                          checkBox.setSelected(currentRecord.getLStatus());
//                          checkBox.setSelected(!currentRecord.getLStatus());
//                          setGraphic(checkBox);
//                      }
//                  }
//              };
//            }
//        });
        bName.setCellValueFactory(
                new PropertyValueFactory<ReadLoanStatusResponseDto, String>("bName"));
        publisher.setCellValueFactory(
                new PropertyValueFactory<ReadLoanStatusResponseDto, String>("publisher"));
        author.setCellValueFactory(
                new PropertyValueFactory<ReadLoanStatusResponseDto, String>("author"));
        lStatus.setCellValueFactory(
                new PropertyValueFactory<ReadLoanStatusResponseDto, Boolean>("lStatus"));
        //대여상태 값이 boolean이어서 텍스트로 변환작업
        lStatus.setCellFactory(column -> new TableCell<ReadLoanStatusResponseDto, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "대여불가능" : "대여가능");
                }
            }
        });
        date.setCellValueFactory(
                new PropertyValueFactory<ReadLoanStatusResponseDto, Date>("returnPreDate"));
        //BookMapper bm = new BookMapper();
        bookList = bookService.findAllBooks();
        table_book.setItems(bookList);
        totalCnt.setText("총 : " + bookList.size() +"건");
    }

    @FXML
    public void searchBtn(ActionEvent actionEvent) throws SQLException {
        ReadSearchLoanStatusResponseDto responseDto = bookService.findByBookName(
                ReadSearchLoanStatusRequestDto.builder().searchName(searchName.getText()).build());
        bookList = responseDto.getBooklist();
        table_book.setItems(bookList);
        totalCnt.setText("총 : " + bookList.size() +"건");
    }

    @FXML
    public void createLoanBookBtn(ActionEvent actionEvent) throws SQLException {
        String loginId = LoginSession.getInstance().getUserInfo().getLoginId();
        ObservableList<ReadLoanStatusResponseDto> selectedBooks = FXCollections.observableArrayList();

        for (ReadLoanStatusResponseDto bean : bookList) {
            if (bean.getSelect().isSelected()) {
                if(bean.getLStatus()) {
                    showAlert(bean.getBName() + "은 대여불가이므로 대여할 수 없습니다.");
                    return;
                }
                selectedBooks.add(bean);
                System.out.println("도서 대여 정보 = " + bean);
            }
        }

        if (selectedBooks.isEmpty()) {
            showAlert("대여할 도서가 선택되지 않았습니다.");
            return;
        } else if (selectedBooks.size() > 3) {
            showAlert("총 3권까지 대여하실 수 있습니다.");
            return;
        }



        CreateBookLoanRequestDto requestDto = CreateBookLoanRequestDto.builder()
                .loginId(loginId)
                .bookList(selectedBooks)
                .build();
        CreateBookLoanResponseDto responseDto = bookController.createLoanBook(requestDto);
        showAlert(responseDto.getMessage());

        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(BOOK_LOAN.getFilePath());
    }

    public void showAlert(String msg) {
        Alert alert = new Alert(WARNING);
        alert.setTitle("도서");
        alert.setHeaderText("도서 대여");
        alert.setContentText(msg);
        alert.show();
    }

}
