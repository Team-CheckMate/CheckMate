package org.checkmate.server.controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.checkmate.server.dto.request.AddBookRequestDto;
import org.checkmate.server.dto.request.ChangePwRequestDto;
import org.checkmate.server.dto.response.AddBookResponseDto;
import org.checkmate.server.dto.response.ChangePwResponseDto;
import org.checkmate.server.service.BookService;
import org.checkmate.server.service.BookServiceImpl;
import org.checkmate.server.service.MemberService;
import org.checkmate.server.service.MemberServiceImpl;
import org.checkmate.server.util.StringSplit;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * 관리자 도서 등록
 * HISTORY1: 최초 생성                              [이준희  2024.07.26]
 */
public class admin_bookAddController implements Initializable {

    private final BookService bookService;

    public admin_bookAddController() {
        bookService = new BookServiceImpl();
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
        alert.setTitle("경고!");
        alert.setHeaderText("도서 등록");
        alert.setContentText(msg);
        alert.show();
    }

    @FXML
    public void register(javafx.event.ActionEvent actionEvent) throws NoSuchAlgorithmException, SQLException {
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
        System.out.println(publisher);
        String category= (String) this.categories.getValue();
        int category_num = StringSplit.getCategoryNum(category,".");
        System.out.println(category_num);
        AddBookResponseDto changeResult = bookService.addBook(AddBookRequestDto.of(bookTitle,isbn,author,translator,publisher,category_num));
    }

}
