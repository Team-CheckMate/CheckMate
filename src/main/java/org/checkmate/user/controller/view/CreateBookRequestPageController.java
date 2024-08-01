package org.checkmate.user.controller.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.checkmate.common.util.LoginSession;
import org.checkmate.user.service.BookService;
import org.checkmate.user.service.BookServiceImpl;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.AlertType.WARNING;

/**
 * 도서신청 컨트롤러
 * HISTORY1: 최초 생성                              [권혁규  2024.07.31]
 */
public class CreateBookRequestPageController implements Initializable {

    private final BookService bookService;

    public CreateBookRequestPageController() {
        bookService = new BookServiceImpl();
    }

    @FXML private TextField bName;
    @FXML private TextField publisher;
    @FXML private TextField author;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    public void exit(ActionEvent actionEvent) {
    }

    @FXML
    public void createAddBtn(ActionEvent actionEvent) {
        if (bName.getText().isEmpty() ) {
            showAlert("도서명을 입력하세요.");
            return;
        } else if(publisher.getText().isEmpty()) {
            showAlert("출판사를 입력하세요.");
            return;
        } else if(author.getText().isEmpty()) {
            showAlert("저자를 입력하세요.");
            return;
        }
        String loginId = LoginSession.getInstance().getUserInfo().getLoginId();
        boolean isSuccess = bookService.createBookRequest(loginId,bName.getText(), publisher.getText(), author.getText());
        if(isSuccess) {
            showAlert("도서 신청을 완료했습니다.");
        } else {
            showAlert("이미 신청처리된 도서가 존재하거나 등록 완료된 도서입니다.");
        }
    }



    public void showAlert(String msg) {
        Alert alert = new Alert(WARNING);
        alert.setTitle("도서");
        alert.setHeaderText("도서 신청");
        alert.setContentText(msg);
        alert.show();
    }
}
