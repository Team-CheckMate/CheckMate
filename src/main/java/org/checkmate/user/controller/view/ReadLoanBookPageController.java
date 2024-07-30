package org.checkmate.user.controller.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.checkmate.common.controller.view.SceneManager;
import org.checkmate.user.controller.server.BookController;
import org.checkmate.common.service.LoginService;
import org.checkmate.common.service.LoginServiceImpl;
import org.checkmate.common.util.LoginSession;
import org.checkmate.user.dto.request.CreateBookLoanRequestDto;
import org.checkmate.user.dto.request.ReadSearchLoanStatusRequestDto;
import org.checkmate.user.dto.response.CreateBookLoanResponseDto;
import org.checkmate.user.dto.response.ReadLoanStatusResponseDto;
import org.checkmate.user.dto.response.ReadSearchLoanStatusResponseDto;
import org.checkmate.user.service.BookService;
import org.checkmate.user.service.BookServiceImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.AlertType.WARNING;

/**
 * 도서대여 컨트롤러
 * HISTORY1: 최초 생성                              [권혁규  2024.07.29]
 */
public class ReadLoanBookPageController implements Initializable {

    private final BookService bookService;
    private final BookController bookController;

    public ReadLoanBookPageController() {
        bookService = new BookServiceImpl();
        bookController = new BookController();
    }

    @FXML
    private Label Menu;

    @FXML
    private Label MenuBack;

    @FXML
    private AnchorPane slider;

    @FXML
    private TextField searchName;

    @FXML
    private TableView<ReadLoanStatusResponseDto> table_book;

    @FXML
    private TableColumn<ReadLoanStatusResponseDto, String> bName;

    @FXML
    private TableColumn<ReadLoanStatusResponseDto, String> publisher;

    @FXML
    private TableColumn<ReadLoanStatusResponseDto, String> author;

    @FXML
    private TableColumn<ReadLoanStatusResponseDto, Boolean> lStatus;

    @FXML
    private TableColumn<ReadLoanStatusResponseDto, Date> date;

    @FXML
    private TableColumn<ReadLoanStatusResponseDto, CheckBox> select;

    ObservableList<ReadLoanStatusResponseDto> bookList;

    //사이드바 이동
    @FXML private void goToBookLoan(ActionEvent event)
    {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/user/readLoanBookPage.fxml");
    }
    @FXML private void goToLoanManage(ActionEvent event)
    {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/user/readLoanBookPage.fxml"); //변경
    }
    @FXML private void goToMyLoanBook(ActionEvent event)
    {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/user/readLoanBookPage.fxml");
    }
    @FXML private void goToBookApply(ActionEvent event)
    {SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/user/readLoanBookPage.fxml");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadDate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadDate() throws SQLException, SQLException {
        select.setCellValueFactory(new PropertyValueFactory<ReadLoanStatusResponseDto, CheckBox>("select"));
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
        bName.setCellValueFactory(new PropertyValueFactory<ReadLoanStatusResponseDto, String>("bName"));
        publisher.setCellValueFactory(new PropertyValueFactory<ReadLoanStatusResponseDto, String>("publisher"));
        author.setCellValueFactory(new PropertyValueFactory<ReadLoanStatusResponseDto, String>("author"));
        lStatus.setCellValueFactory(new PropertyValueFactory<ReadLoanStatusResponseDto, Boolean>("lStatus"));
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
        date.setCellValueFactory(new PropertyValueFactory<ReadLoanStatusResponseDto, Date>("returnPreDate"));
        //BookMapper bm = new BookMapper();
        bookList = bookService.findAllBooks();
        table_book.setItems(bookList);
    }

    @FXML
    public void searchBtn(ActionEvent actionEvent) throws SQLException {
        ReadSearchLoanStatusResponseDto responseDto = bookService.findByBookName(
                ReadSearchLoanStatusRequestDto.builder().searchName(searchName.getText()).build());
        table_book.setItems(responseDto.getBooklist());
    }

    @FXML
    public void createLoanBookBtn(ActionEvent actionEvent) throws SQLException {
        String loginId = LoginSession.getInstance().getMemberInfo().getLoginId();
        ObservableList<ReadLoanStatusResponseDto> selectedBooks  = FXCollections.observableArrayList();

        for(ReadLoanStatusResponseDto bean : bookList) {
            if(bean.getSelect().isSelected()) {
                selectedBooks .add(bean);
                System.out.println("도서 대여 정보 = " + bean);
            }
        }

        if (selectedBooks.isEmpty()) {
            showAlert("대여할 도서가 선택되지 않았습니다.");
            return;
        }

        CreateBookLoanRequestDto requestDto = CreateBookLoanRequestDto.builder()
                                                .loginId(loginId)
                                                .bookList(selectedBooks )
                                                .build();
        CreateBookLoanResponseDto responseDto = bookController.createLoanBook(requestDto);
        showAlert(responseDto.getMessage());
    }

    public void showAlert(String msg) {
        Alert alert = new Alert(WARNING);
        alert.setTitle("도서");
        alert.setHeaderText("도서 대여");
        alert.setContentText(msg);
        alert.show();
    }

}
