package org.checkmate.admin.controller.view;

import java.io.IOException;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.checkmate.admin.dto.request.BookUpdateRequestDto;
import org.checkmate.admin.dto.response.BookReadInformationResponseDto;
import org.checkmate.admin.service.BookManagementService;
import org.checkmate.admin.service.BookManagementServiceImpl;
import org.checkmate.server.util.SceneManager;
import org.checkmate.admin.dto.response.BookUpdateResponseDto;
import org.checkmate.server.util.StringSplit;
import org.checkmate.server.util.TypeFormatter;

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
    private final BookManagementService bookService;

    public BookUpdatePageController(Long bookId) {
        bookService = new BookManagementServiceImpl();
        this.bookId = bookId;
    }

    @FXML
    private Label Menu;

    @FXML
    private Label MenuBack;

    @FXML
    private AnchorPane slider;

    @FXML
    private TextField bookTitle;

    @FXML
    private TextField publisher;

    @FXML
    private TextField isbn;

    @FXML
    private TextField author;

    @FXML
    private TextField translator;

    @FXML
    private ChoiceBox categories;

    private boolean lStatus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(bookId);
        try {
            BookReadInformationResponseDto responseDto = bookService.findBook(bookId);
            bookTitle.setText(responseDto.getBName());
            publisher.setText(responseDto.getPublisher());
            isbn.setText(responseDto.getIsbn());
            author.setText(responseDto.getAuthor());
            translator.setText(responseDto.getTranslator());
            String categoryValue = responseDto.getCategoryId()+". "+responseDto.getCName();
            System.out.println(categoryValue);
            categories.setValue(categoryValue);
            lStatus = responseDto.getLStatus();
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

    public boolean userField(TextField bookTitle,TextField isbn, TextField author, TextField translator, TextField publisher, ChoiceBox categories) {
        if (bookTitle.getText().isEmpty()) {
            Msg("책 제목을 입력해주세요.");
            bookTitle.requestFocus();
            bookTitle.clear();
            return false;
        }
        if (isbn.getText().isEmpty()) {
            Msg("책의 ISBN을 입력해주세요.");
            isbn.requestFocus();
            return false;
        }
        if (author.getText().isEmpty()) {
            Msg("저자를 입력해주세요.");
            author.requestFocus();
            return false;
        }
        if (translator.getText().isEmpty()) {
            Msg("옮긴이를 입력해주세요.");
            author.requestFocus();
            return false;
        }
        if (publisher.getText().isEmpty()) {
            Msg("출판사를 입력해주세요.");
            author.requestFocus();
            return false;
        }
        // TODO : choice BOX 예외처리
        return true;
    }

    public void Msg(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("경고");
        alert.setHeaderText("도서 수정");
        alert.setContentText(msg);
        alert.show();
    }

    public void Msg(String type,String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(type);
        alert.setHeaderText("도서 수정");
        alert.setContentText(msg);
        alert.show();
    }

    @FXML
    public void register(ActionEvent actionEvent) throws NoSuchAlgorithmException, SQLException {
        System.out.println("등록버튼실행됨");
        if(!userField(bookTitle,isbn,author,translator,publisher,categories)){
            return;
        }

        String bookTitle =  this.bookTitle.getText();
        System.out.println(bookTitle);
        String isbn = this.isbn.getText();
        String author = this.author.getText();
        String translator = this.translator.getText();
        String publisher = this.publisher.getText();
        int lStatusInt = TypeFormatter.BooleanToInteger(lStatus);
        System.out.println(publisher);
        String category= (String) this.categories.getValue();
        int category_num = StringSplit.getCategoryNum(category,".");
        System.out.println(category_num);
        BookUpdateResponseDto responseDto = bookService.editBook(
                BookUpdateRequestDto.of(bookId,bookTitle,isbn,author,translator,publisher,category_num,lStatusInt));
        Msg("알림",responseDto.getMessage());
        SceneManager sm = SceneManager.getInstance();
        sm.moveScene("/org/checkmate/view/layouts/admin/bookManagementPage.fxml");
    }

}
