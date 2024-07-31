package org.checkmate.admin.controller.view;

import static org.checkmate.admin.util.FilePath.BOOK_APPLY_FX;
import static org.checkmate.admin.util.FilePath.BOOK_CREATE_FX;
import static org.checkmate.admin.util.FilePath.BOOK_MANAGEMENT_FX;
import static org.checkmate.admin.util.FilePath.MANAGEMENT_FX;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.checkmate.admin.controller.server.BookController;
import org.checkmate.admin.dto.response.BookReadLoanStatusResponseDto;
import org.checkmate.common.controller.view.SceneManager;

/**
 * 도서 관리 HISTORY1: 최초 생성                              [이준희  2024.07.26]
 */
public class BookManagementPageController implements Initializable {

    private final BookController bookController;

    public BookManagementPageController() {
        bookController = new BookController();
    }

    @FXML private Text searchCount;
    @FXML private TableView<BookReadLoanStatusResponseDto> table_book;
    @FXML private TableColumn<BookReadLoanStatusResponseDto, Long> bookId;
    @FXML private TableColumn<BookReadLoanStatusResponseDto, String> bName;
    @FXML private TableColumn<BookReadLoanStatusResponseDto, String> ISBN;
    @FXML private TableColumn<BookReadLoanStatusResponseDto, String> publisher;
    @FXML private TableColumn<BookReadLoanStatusResponseDto, String> author;
    @FXML private TableColumn<BookReadLoanStatusResponseDto, Boolean> lStatus;
    @FXML private TableColumn<BookReadLoanStatusResponseDto, String> eName;
    @FXML private TableColumn<BookReadLoanStatusResponseDto, Date> date;
    @FXML private TableColumn<BookReadLoanStatusResponseDto, Void> manage;
    @FXML private TextField searchContent;

    //시스템 종료
    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    //사이드바 이동
    @FXML
    private void goToBookManage(ActionEvent event) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(BOOK_MANAGEMENT_FX.getFilePath());
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
        sm.moveScene(BOOK_APPLY_FX.getFilePath());
    }

    ObservableList<BookReadLoanStatusResponseDto> bookList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadDate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void Msg(String msg, String function) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("도서 관리");
        alert.setHeaderText(function);
        alert.setContentText(msg);
        alert.show();
    }


    private void loadDate() throws SQLException {
        bookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        bName.setCellValueFactory(new PropertyValueFactory<>("bName"));
        ISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        lStatus.setCellValueFactory(new PropertyValueFactory<>("lStatus"));
        date.setCellValueFactory(new PropertyValueFactory<>("addDate"));
        eName.setCellValueFactory(new PropertyValueFactory<>("eName"));
        ObservableList<BookReadLoanStatusResponseDto> bookList = FXCollections.observableArrayList(
                bookController.readAllBooks());
        table_book.setItems(bookList);
        int count = bookList.size();
        searchCount.setText("총 : " + count + " 건");
        addButtonToTable();
    }

    private void addButtonToTable() {
        Callback<TableColumn<BookReadLoanStatusResponseDto, Void>, TableCell<BookReadLoanStatusResponseDto, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<BookReadLoanStatusResponseDto, Void> call(
                    final TableColumn<BookReadLoanStatusResponseDto, Void> param) {
                final TableCell<BookReadLoanStatusResponseDto, Void> cell = new TableCell<>() {

                    private final Button modifyBtn = new Button("수정");
                    private final Button deleteBtn = new Button("삭제");

                    {
                        modifyBtn.setStyle(
                                "-fx-background-color: transperant; -fx-border-color: #364959 ;");
                        deleteBtn.setStyle(
                                "-fx-background-color: transperant; -fx-border-color: #364959 ;");

                        modifyBtn.setOnAction((event) -> {
                            BookReadLoanStatusResponseDto data = getTableView().getItems()
                                    .get(getIndex());
                            System.out.println("Selected Data: " + data);
                            BookUpdatePageController controller = new BookUpdatePageController(
                                    data.getBookId());
                            // 파라미터 설정
                            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                                    "/org/checkmate/view/layouts/admin/bookUpdatePage.fxml"));
                            loader.setController(controller);
                            Parent root = null;
                            try {
                                root = loader.load();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            Scene scene = new Scene(root);
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.setScene(scene);
                            stage.show();
                        });

                        deleteBtn.setOnAction((event) -> {
                            BookReadLoanStatusResponseDto data = getTableView().getItems()
                                    .get(getIndex());
                            System.out.println("Selected Data: " + data);
                            String msg = "";
                            try {
                                msg = bookController.deleteSelectedBook(data.getBookId());
                                Msg(data.getBName() + msg, "삭제");
                            } catch (SQLException e) {
                                Msg("대출중인 회원이 존재하여 삭제할 수 없습니다.", "삭제");
                            }

                            SceneManager sm = SceneManager.getInstance();
                            sm.moveScene(BOOK_MANAGEMENT_FX.getFilePath());
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            // 버튼들을 HBox에 추가
                            HBox hBox = new HBox(10); // 버튼 사이의 간격 설정
                            hBox.getChildren().addAll(modifyBtn, deleteBtn);
                            setGraphic(hBox);
                        }
                    }
                };
                return cell;
            }
        };

        manage.setCellFactory(cellFactory);
    }

    @FXML
    public void moveToAddBook(ActionEvent actionEvent) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(BOOK_CREATE_FX.getFilePath());
    }

    @FXML
    public void searchBtn(ActionEvent actionEvent) throws SQLException {
        String bookName = this.searchContent.getText();
        System.out.println(bookName);
        bookId.setCellValueFactory(
                new PropertyValueFactory<BookReadLoanStatusResponseDto, Long>("bookId"));
        bName.setCellValueFactory(
                new PropertyValueFactory<BookReadLoanStatusResponseDto, String>("bName"));
        ISBN.setCellValueFactory(
                new PropertyValueFactory<BookReadLoanStatusResponseDto, String>("ISBN"));
        author.setCellValueFactory(
                new PropertyValueFactory<BookReadLoanStatusResponseDto, String>("author"));
        publisher.setCellValueFactory(
                new PropertyValueFactory<BookReadLoanStatusResponseDto, String>("publisher"));
        lStatus.setCellValueFactory(
                new PropertyValueFactory<BookReadLoanStatusResponseDto, Boolean>("lStatus"));
        date.setCellValueFactory(
                new PropertyValueFactory<BookReadLoanStatusResponseDto, Date>("addDate"));
        eName.setCellValueFactory(
                new PropertyValueFactory<BookReadLoanStatusResponseDto, String>("eName"));
        bookList = bookController.ReadBooksByBookName(bookName);
        table_book.setItems(bookList);
        int count = bookList.size();
        searchCount.setText("총 : " + count + " 건");
        addButtonToTable();
    }

    public void goHome(ActionEvent event) {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene(MANAGEMENT_FX.getFilePath());
    }
}
