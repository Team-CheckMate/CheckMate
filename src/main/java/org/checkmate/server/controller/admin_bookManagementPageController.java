package org.checkmate.server.controller;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
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
import org.checkmate.server.dto.response.FindAllBooksAdminResponseDto;
import org.checkmate.server.entity.Book;
import org.checkmate.server.entity.BookLoanStatus;
import org.checkmate.server.mapper.BookMapper;
import org.checkmate.server.service.BookService;
import org.checkmate.server.service.BookServiceImpl;
import org.checkmate.server.service.MemberService;
import org.checkmate.server.service.MemberServiceImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * 도서 관리
 * HISTORY1: 최초 생성                              [이준희  2024.07.26]
 */
public class admin_bookManagementPageController implements Initializable  {

    private final BookService bookService;

    // 기본 생성자
    public admin_bookManagementPageController() {
        bookService = new BookServiceImpl();
    }

    @FXML
    private Label Menu;

    @FXML
    private Label MenuBack;

    @FXML
    private AnchorPane slider;

    @FXML
    private TableView<FindAllBooksAdminResponseDto> table_book;

    @FXML
    private TableColumn<FindAllBooksAdminResponseDto, Long> bookId;

    @FXML
    private TableColumn<FindAllBooksAdminResponseDto, String> bName;

    @FXML
    private TableColumn<FindAllBooksAdminResponseDto, String> ISBN;

    @FXML
    private TableColumn<FindAllBooksAdminResponseDto, String> publisher;

    @FXML
    private TableColumn<FindAllBooksAdminResponseDto, String> author;

    @FXML
    private TableColumn<FindAllBooksAdminResponseDto, Boolean> lStatus;

    @FXML
    private TableColumn<FindAllBooksAdminResponseDto, String> eName;

    @FXML
    private TableColumn<FindAllBooksAdminResponseDto, Date> date;

    @FXML
    private TableColumn<FindAllBooksAdminResponseDto, Void> manage;


    ObservableList<FindAllBooksAdminResponseDto> bookList;

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
        bookId.setCellValueFactory(new PropertyValueFactory<FindAllBooksAdminResponseDto,Long>("bookId"));
        bName.setCellValueFactory(new PropertyValueFactory<FindAllBooksAdminResponseDto, String>("bName"));
        ISBN.setCellValueFactory(new PropertyValueFactory<FindAllBooksAdminResponseDto, String>("ISBN"));
        author.setCellValueFactory(new PropertyValueFactory<FindAllBooksAdminResponseDto, String>("author"));
        publisher.setCellValueFactory(new PropertyValueFactory<FindAllBooksAdminResponseDto, String>("publisher"));
        lStatus.setCellValueFactory(new PropertyValueFactory<FindAllBooksAdminResponseDto, Boolean>("lStatus"));
        date.setCellValueFactory(new PropertyValueFactory<FindAllBooksAdminResponseDto, Date>("addDate"));
        eName.setCellValueFactory(new PropertyValueFactory<FindAllBooksAdminResponseDto,String>("eName"));
        bookList = bookService.findAllBooksAdmin();
        table_book.setItems(bookList);
        addButtonToTable();
    }

    private void addButtonToTable() {
        Callback<TableColumn<FindAllBooksAdminResponseDto, Void>, TableCell<FindAllBooksAdminResponseDto, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<FindAllBooksAdminResponseDto, Void> call(final TableColumn<FindAllBooksAdminResponseDto, Void> param) {
                final TableCell<FindAllBooksAdminResponseDto, Void> cell = new TableCell<>() {

                    private final Button modifyBtn = new Button("수정");
                    private final Button deleteBtn = new Button("삭제");

                    {
                        modifyBtn.setOnAction((event) -> {
                            FindAllBooksAdminResponseDto data = getTableView().getItems().get(getIndex());
                            System.out.println("Selected Data: " + data);
                            admin_bookModifyController controller = new admin_bookModifyController(data.getBookId());
                            // 파라미터 설정
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/checkmate/view/layouts/admin/bookModifyPage.fxml"));
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
                            FindAllBooksAdminResponseDto data = getTableView().getItems().get(getIndex());
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
        sm.moveScene("/org/checkmate/view/layouts/admin/bookAddPage.fxml");
    }
}
