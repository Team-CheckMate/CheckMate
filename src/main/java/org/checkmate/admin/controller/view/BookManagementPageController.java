package org.checkmate.admin.controller.view;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.checkmate.admin.controller.server.BookController;
import org.checkmate.admin.dto.response.BookReadLoanStatusResponseDto;
import org.checkmate.admin.service.BookManagementService;
import org.checkmate.admin.service.BookManagementServiceImpl;
import org.checkmate.common.controller.view.SceneManager;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * 도서 관리
 * HISTORY1: 최초 생성                              [이준희  2024.07.26]
 */
public class BookManagementPageController implements Initializable  {

    private final BookController bookController;

    public BookManagementPageController() {
        bookController = new BookController();
    }


    @FXML private Label Menu;
    @FXML private Label MenuBack;
    @FXML private AnchorPane slider;
    @FXML private Text countMessage;
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
    @FXML private Button search;
    @FXML private TextField bookSearch;

    //시스템 종료
    @FXML private void exit(ActionEvent event) {
        Platform.exit();
    }

    //사이드바 이동
    @FXML private void goToBookManage(ActionEvent event)
    {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/admin/bookManagementPage.fxml");
    }
    @FXML private void goToLoanStatus(ActionEvent event)
    {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/admin/applyStatusViewPage.fxml"); //변경
    }
    @FXML private void goToUserManage(ActionEvent event)
    {
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/admin/userManagementPage.fxml");
    }
    @FXML private void goToApplyStatus(ActionEvent event)
    {SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/admin/applyStatusViewPage.fxml");
    }

    ObservableList<BookReadLoanStatusResponseDto> bookList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadDate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

//        slider.setTranslateX(-176);
//
//        Menu.setOnMouseClicked(event -> {
//            TranslateTransition slide = new TranslateTransition();
//            slide.setDuration(Duration.seconds(0.4));
//            slide.setNode(slider);
//
//            slide.setToX(0);
//            slide.play();
//
//            slider.setTranslateX(-176);
//
//            slide.setOnFinished((ActionEvent e)->{
//                Menu.setVisible(false);
//                MenuBack.setVisible(true);
//            });
//        });
//
//        MenuBack.setOnMouseClicked(event -> {
//            TranslateTransition slide = new TranslateTransition();
//            slide.setDuration(Duration.seconds(0.4));
//            slide.setNode(slider);
//
//            slide.setToX(-176);
//            slide.play();
//
//            slider.setTranslateX(0);
//
//            slide.setOnFinished((ActionEvent e)->{
//                Menu.setVisible(true);
//                MenuBack.setVisible(false);
//            });
//        });
    }

    public void Msg(String msg,String function) {
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
        bookList = bookController.readAllBooks();
        table_book.setItems(bookList);
        int count = bookList.size();
        countMessage.setText("총 : "+count+" 건");
        addButtonToTable();
    }

    private void addButtonToTable() {
        Callback<TableColumn<BookReadLoanStatusResponseDto, Void>, TableCell<BookReadLoanStatusResponseDto, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<BookReadLoanStatusResponseDto, Void> call(final TableColumn<BookReadLoanStatusResponseDto, Void> param) {
                final TableCell<BookReadLoanStatusResponseDto, Void> cell = new TableCell<>() {

                    private final Button modifyBtn = new Button("수정");
                    private final Button deleteBtn = new Button("삭제");

                    {
                        //TODO : 관리자가 수정 진행시 대출 boolean값 변경 가능하도록 기능 추가
                        modifyBtn.setOnAction((event) -> {
                            BookReadLoanStatusResponseDto data = getTableView().getItems().get(getIndex());
                            System.out.println("Selected Data: " + data);
                            BookUpdatePageController controller = new BookUpdatePageController(data.getBookId());
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
                            BookReadLoanStatusResponseDto data = getTableView().getItems().get(getIndex());
                            System.out.println("Selected Data: " + data);
                            String msg = "";
                            try {
                                msg = bookController.deleteSelectedBook(data.getBookId());
                                Msg(data.getBName()+msg,"삭제");
                            } catch (SQLException e) {
                                Msg("대출중인 회원이 존재하여 삭제할 수 없습니다.","삭제");
                            }

                            SceneManager sm = SceneManager.getInstance();
                            sm.moveScene("/org/checkmate/view/layouts/admin/bookManagementPage.fxml");
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
        sm.moveScene("/org/checkmate/view/layouts/admin/bookCreatePage.fxml");
    }

    @FXML
    public void search(ActionEvent actionEvent) throws SQLException {
        String bookName = this.bookSearch.getText();
        System.out.println(bookName);
        bookId.setCellValueFactory(new PropertyValueFactory<BookReadLoanStatusResponseDto,Long>("bookId"));
        bName.setCellValueFactory(new PropertyValueFactory<BookReadLoanStatusResponseDto, String>("bName"));
        ISBN.setCellValueFactory(new PropertyValueFactory<BookReadLoanStatusResponseDto, String>("ISBN"));
        author.setCellValueFactory(new PropertyValueFactory<BookReadLoanStatusResponseDto, String>("author"));
        publisher.setCellValueFactory(new PropertyValueFactory<BookReadLoanStatusResponseDto, String>("publisher"));
        lStatus.setCellValueFactory(new PropertyValueFactory<BookReadLoanStatusResponseDto, Boolean>("lStatus"));
        date.setCellValueFactory(new PropertyValueFactory<BookReadLoanStatusResponseDto, Date>("addDate"));
        eName.setCellValueFactory(new PropertyValueFactory<BookReadLoanStatusResponseDto,String>("eName"));
        bookList = bookController.ReadBooksByBookName(bookName);
        table_book.setItems(bookList);
        int count = bookList.size();
        countMessage.setText("총 : "+count+" 건");
        addButtonToTable();
    }
}
