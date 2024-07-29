package org.checkmate.admin.controller.view;

import static javafx.scene.control.Alert.AlertType.*;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.checkmate.admin.controller.server.BookController;
import org.checkmate.admin.dto.request.BookCreateRequestDto;
import org.checkmate.common.exception.ValidationException;
import org.checkmate.common.util.StringSplit;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * 관리자 도서 등록
 * HISTORY1: 최초 생성                              [이준희  2024.07.26]
 */
public class BookCreatePageController implements Initializable {

    private final BookController bookController;

    public BookCreatePageController() {
        bookController = new BookController();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

    @FXML
    public void register(ActionEvent actionEvent) {
        System.out.println("등록버튼실행됨");
        try {
            BookCreateRequestDto requestDto = createRequestDto();
            bookController.createBook(requestDto);
        } catch (ValidationException | SQLException e) {
            showAlert(e.getMessage());
        }

    }

    public void showAlert(String msg) {
        Alert alert = new Alert(WARNING);
        alert.setTitle("경고!");
        alert.setHeaderText("도서 등록");
        alert.setContentText(msg);
        alert.show();
    }

    private BookCreateRequestDto createRequestDto() {
        String title = bookTitle.getText();
        String isbnText = isbn.getText();
        String authorText = author.getText();
        String translatorText = translator.getText();
        String publisherText = publisher.getText();
        String category = String.valueOf(categories.getValue());
        int categoryNum = StringSplit.getCategoryNum(category, ".");

        validateUserFields();

        return BookCreateRequestDto.builder()
                .bookTitle(title)
                .isbn(isbnText)
                .author(authorText)
                .translator(translatorText)
                .publisher(publisherText)
                .category(category)
                .category_num(categoryNum)
                .build();
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

}
