package org.checkmate.admin.controller.view;

import javafx.animation.TranslateTransition;
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
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.checkmate.admin.dto.response.BookReadLoanStatusResponseDto;
import org.checkmate.admin.service.BookManagementService;
import org.checkmate.admin.service.BookManagementServiceImpl;
import org.checkmate.common.util.SceneManager;

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

    private final BookManagementService bookService;

    // 기본 생성자
    public BookManagementPageController() throws IOException {
        bookService = new BookManagementServiceImpl();
    }

    @FXML
    private Label Menu;

    @FXML
    private Label MenuBack;

    @FXML
    private AnchorPane slider;

    @FXML
    private TableView<BookReadLoanStatusResponseDto> table_book;

    @FXML
    private TableColumn<BookReadLoanStatusResponseDto, Long> bookId;

    @FXML
    private TableColumn<BookReadLoanStatusResponseDto, String> bName;

    @FXML
    private TableColumn<BookReadLoanStatusResponseDto, String> ISBN;

    @FXML
    private TableColumn<BookReadLoanStatusResponseDto, String> publisher;

    @FXML
    private TableColumn<BookReadLoanStatusResponseDto, String> author;

    @FXML
    private TableColumn<BookReadLoanStatusResponseDto, Boolean> lStatus;

    @FXML
    private TableColumn<BookReadLoanStatusResponseDto, String> eName;

    @FXML
    private TableColumn<BookReadLoanStatusResponseDto, Date> date;

    @FXML
    private TableColumn<BookReadLoanStatusResponseDto, Void> manage;


    ObservableList<BookReadLoanStatusResponseDto> bookList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadDate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        slider.setTranslateX(-176);

        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-176);

            slide.setOnFinished((ActionEvent e)->{
                Menu.setVisible(false);
                MenuBack.setVisible(true);
            });
        });

        MenuBack.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-176);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)->{
                Menu.setVisible(true);
                MenuBack.setVisible(false);
            });
        });
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
        bookList = bookService.findAllBooksAdmin();
        table_book.setItems(bookList);
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
                            boolean result = false;
                            try {
                                result = bookService.deleteSelectedBook(data.getBookId());
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            String msg = result ? "삭제되었습니다" : "삭제 실패하였습니다";
                            Msg(data.getBName()+msg,"삭제");
                            System.out.println(result);
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
}
