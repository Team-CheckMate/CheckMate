package org.checkmate.admin.controller.view;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.checkmate.admin.controller.server.BookController;
import org.checkmate.admin.dto.request.BookUpdateRequestDto;
import org.checkmate.admin.dto.response.BookReadInformationResponseDto;
import org.checkmate.admin.service.BookManagementService;
import org.checkmate.admin.service.BookManagementServiceImpl;
//import org.checkmate.common.controller.view.SceneManager;
import org.checkmate.admin.dto.response.BookUpdateResponseDto;
import org.checkmate.common.controller.view.SceneManager;
import org.checkmate.common.exception.ValidationException;
import org.checkmate.common.util.StringSplit;
import org.checkmate.common.util.TypeFormatter;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * 관리자 도서 수정
 * HISTORY1: 최초 생성                              [이준희  2024.07.27]
 */
public class BookUpdatePageController implements Initializable {

    private Long bookId;
    private final BookController bookController;

    public BookUpdatePageController(Long bookId) {
        bookController = new BookController();
        this.bookId = bookId;
    }

    @FXML private Label Menu;
    @FXML private Label MenuBack;
    @FXML private AnchorPane slider;
    @FXML private TextField bookTitle;
    @FXML private TextField publisher;
    @FXML private TextField isbn;
    @FXML private TextField author;
    @FXML private TextField translator;
    @FXML private ChoiceBox categories;

    private boolean lStatus;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(bookId);
        try {
            BookReadInformationResponseDto responseDto = bookController.readBook(bookId);
            setData(responseDto);
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

    private void setData(BookReadInformationResponseDto responseDto) throws SQLException{
        bookTitle.setText(responseDto.getBName());
        publisher.setText(responseDto.getPublisher());
        isbn.setText(responseDto.getIsbn());
        author.setText(responseDto.getAuthor());
        translator.setText(responseDto.getTranslator());
        String categoryValue = responseDto.getCategoryId()+". "+responseDto.getCName();
        categories.setValue(categoryValue);
        lStatus = responseDto.getLStatus();
    }

    public void validateUserFields() {
        isNotEmpty(bookTitle, "책 제목을 입력해주세요.");
        isNotEmpty(isbn, "책의 ISBN을 입력해주세요.");
        isNotEmpty(author, "저자를 입력해주세요.");
        isNotEmpty(translator, "옮긴이를 입력해주세요.");
        isNotEmpty(publisher, "출판사를 입력해주세요.");
    }

    private void isNotEmpty(TextField field, String message) {
        if (field.getText().trim().isEmpty()) {
            throw new ValidationException(message);
        }
    }

    public void showAlert(String msg) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("경고");
        alert.setHeaderText("도서 수정");
        alert.setContentText(msg);
        alert.show();
    }

    public void showAlert(String type,String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(type);
        alert.setHeaderText("도서 수정");
        alert.setContentText(msg);
        alert.show();
    }

    private BookUpdateRequestDto createRequestDto(){
        Long bookId = this.bookId;
        String bookTitle = this.bookTitle.getText();
        String isbn = this.isbn.getText();
        String author = this.author.getText();
        String translator = this.translator.getText();
        String publisher = this.publisher.getText();
        int lStatusInt = TypeFormatter.BooleanToInteger(lStatus);
        String category= (String) this.categories.getValue();
        int category_num = StringSplit.getCategoryNum(category,".");

        validateUserFields();

        return BookUpdateRequestDto.builder()
            .bookId(bookId)
            .bookTitle(bookTitle)
            .isbn(isbn)
            .author(author)
            .translator(translator)
            .publisher(publisher)
            .l_status(lStatusInt)
            .category_num(category_num)
            .build();
    }

    @FXML
    public void register(ActionEvent actionEvent) throws NoSuchAlgorithmException, SQLException {
        System.out.println("등록버튼실행됨");
        try {
            BookUpdateRequestDto requestDto = createRequestDto();
            bookController.updateBook(createRequestDto());
        }catch (ValidationException | SQLException e){
            showAlert(e.getMessage());
        }
    }

}
